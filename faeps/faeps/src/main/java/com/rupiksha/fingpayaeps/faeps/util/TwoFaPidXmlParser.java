package com.rupiksha.fingpayaeps.faeps.util;

import com.rupiksha.fingpayaeps.faeps.dto.TwoFaCaptureResponse;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.function.Consumer;

public class TwoFaPidXmlParser {

    public static TwoFaCaptureResponse parse(String xmlStr) {

        try {
            if (xmlStr == null || xmlStr.trim().isEmpty()) {
                throw new RuntimeException("PID XML is empty");
            }

            // 🔐 SECURE XML PARSER
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

            factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
            factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
            factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

            factory.setXIncludeAware(false);
            factory.setExpandEntityReferences(false);
            factory.setNamespaceAware(false);
            factory.setValidating(false);

            DocumentBuilder builder = factory.newDocumentBuilder();

            Document doc = builder.parse(
                    new ByteArrayInputStream(xmlStr.getBytes(StandardCharsets.UTF_8))
            );

            doc.getDocumentElement().normalize();

            Element resp = get(doc, "Resp");
            Element device = getOptional(doc, "DeviceInfo");
            Element skey = get(doc, "Skey");
            Element hmac = get(doc, "Hmac");
            Element data = get(doc, "Data");

            TwoFaCaptureResponse res = new TwoFaCaptureResponse();

            // ================= RESP =================
            res.setErrCode(getAttr(resp, "errCode"));
            res.setErrInfo(getAttr(resp, "errInfo"));

            setIfNotNull(res::setFCount, getAttrCI(resp, "fCount", "fcount"));
            setIfNotNull(res::setFType, getAttrCI(resp, "fType", "ftype"));
            setIfNotNull(res::setQScore, getAttrCI(resp, "qScore", "qscore"));


            // ================= DEVICE =================
            if (device != null) {
                setIfNotNull(res::setDpID, getAttrCI(device, "dpId", "dpID"));
                setIfNotNull(res::setRdsID, getAttrCI(device, "rdsId", "rdsID"));
                setIfNotNull(res::setRdsVer, getAttr(device, "rdsVer"));

                setIfNotNull(res::setDc, getAttr(device, "dc"));
                setIfNotNull(res::setMi, getAttr(device, "mi"));
                setIfNotNull(res::setMc, getAttr(device, "mc"));
            }

            // ================= SKEY =================
            res.setCi(getAttr(skey, "ci"));
            setIfNotNull(res::setSessionKey, clean(skey.getTextContent()));

            // ================= HMAC =================
            setIfNotNull(res::setHmac, clean(hmac.getTextContent()));

            // ================= DATA =================
            setIfNotNull(res::setPidDatatype, getAttr(data, "type"));
            setIfNotNull(res::setPiddata, clean(data.getTextContent()));

            // ================= VALIDATION =================

            if (!"0".equals(res.getErrCode())) {
                throw new RuntimeException("Biometric capture failed: " + res.getErrInfo());
            }

            if (res.getPiddata() == null || res.getPiddata().isEmpty()) {
                throw new RuntimeException("PID data missing");
            }

            if (res.getHmac() == null || res.getHmac().isEmpty()) {
                throw new RuntimeException("HMAC missing");
            }

            if (res.getSessionKey() == null || res.getSessionKey().isEmpty()) {
                throw new RuntimeException("SessionKey missing");
            }

            // 🔥 DEBUG SAFE
            System.out.println("PID Length: " + res.getPiddata().length());
            System.out.println("HMAC Length: " + res.getHmac().length());
            System.out.println("SessionKey Length: " + res.getSessionKey().length());

            return res;

        } catch (Exception e) {
            throw new RuntimeException("PID XML parsing failed", e);
        }
    }

    // ================= HELPERS =================

    private static void setIfNotNull(Consumer<String> setter, String value) {
        if (value != null && !value.isEmpty()) {
            setter.accept(value);
        }
    }

    private static Element get(Document doc, String tag) {
        NodeList list = doc.getElementsByTagName(tag);
        if (list.getLength() == 0) {
            throw new RuntimeException(tag + " missing in PID XML");
        }
        return (Element) list.item(0);
    }

    private static Element getOptional(Document doc, String tag) {
        NodeList list = doc.getElementsByTagName(tag);
        return list.getLength() > 0 ? (Element) list.item(0) : null;
    }

    private static String getAttr(Element el, String attr) {
        return (el != null && el.hasAttribute(attr)) ? el.getAttribute(attr) : null;
    }

    private static String getAttrCI(Element el, String primary, String secondary) {
        if (el == null) return null;

        if (el.hasAttribute(primary)) return el.getAttribute(primary);
        if (el.hasAttribute(secondary)) return el.getAttribute(secondary);

        return null;
    }

    // 🔥 CLEAN WITHOUT TRIM (IMPORTANT FOR BASE64)
    private static String clean(String val) {
        return val == null ? null : val.replace("\n", "").replace("\r", "");
    }
}