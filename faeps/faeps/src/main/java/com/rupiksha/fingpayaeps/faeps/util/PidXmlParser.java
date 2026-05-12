package com.rupiksha.fingpayaeps.faeps.util;

import com.rupiksha.fingpayaeps.faeps.dto.CaptureResponse;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.xml.sax.InputSource;

import java.io.StringReader;

public class PidXmlParser {

    public static CaptureResponse parse(String xml) {
        try {

            if (xml == null || xml.trim().isEmpty()) {
                throw new RuntimeException("Empty PID XML");
            }

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

            factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
            factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
            factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(xml)));

            doc.getDocumentElement().normalize();

            Element resp = getElement(doc, "Resp");
            Element device = getElement(doc, "DeviceInfo");
            Element skey = getElement(doc, "Skey");
            Element hmac = getElement(doc, "Hmac");
            Element data = getElement(doc, "Data");

            CaptureResponse cr = new CaptureResponse();

            // ================= RESP =================
            cr.setErrCode(getAttr(resp, "errCode"));
            cr.setErrInfo(getAttr(resp, "errInfo"));

            if (!"0".equals(cr.getErrCode())) {
                throw new RuntimeException("Biometric failed: " + cr.getErrInfo());
            }

            // 🔥 ADD THESE (IMPORTANT)
            cr.setFCount(getAttr(resp, "fCount"));
            cr.setFType(getAttr(resp, "fType"));
            cr.setICount(getAttr(resp, "iCount"));
            cr.setIType(getAttr(resp, "iType"));
            cr.setPCount(getAttr(resp, "pCount"));
            cr.setPType(getAttr(resp, "pType"));
            cr.setNmPoints(getAttr(resp, "nmPoints"));
            cr.setQScore(getAttr(resp, "qScore"));

            // ================= DEVICE =================
            cr.setDpID(getAttrCaseInsensitive(device, "dpId", "dpID"));
            cr.setRdsID(getAttrCaseInsensitive(device, "rdsId", "rdsID"));
            cr.setRdsVer(getAttr(device, "rdsVer"));

            // 🔥 ADD THESE
            cr.setDc(getAttr(device, "dc"));
            cr.setMi(getAttr(device, "mi"));
            cr.setMc(getAttr(device, "mc"));

            // ================= SKEY =================
            cr.setCi(getAttr(skey, "ci"));
            cr.setSessionKey(cleanBase64(getText(skey)));

            // ================= HMAC =================
            cr.setHmac(cleanBase64(getText(hmac)));

            // ================= DATA =================
            cr.setPidDatatype(getAttr(data, "type"));
            cr.setPiddata(cleanBase64(getText(data)));

            // ================= FINAL VALIDATION =================

            if (cr.getPiddata() == null || cr.getPiddata().isEmpty())
                throw new RuntimeException("PID data missing");

            if (cr.getHmac() == null || cr.getHmac().isEmpty())
                throw new RuntimeException("HMAC missing");

            if (cr.getSessionKey() == null || cr.getSessionKey().isEmpty())
                throw new RuntimeException("SessionKey missing");

            return cr;

        } catch (Exception e) {
            throw new RuntimeException("PID XML parse failed", e);
        }
    }
    //==================================================================
    // ================= HELPERS =================

    // Required element (throws error if missing)
    private static Element getElement(Document doc, String tag) {
        NodeList list = doc.getElementsByTagName(tag);
        if (list.getLength() == 0) {
            throw new RuntimeException(tag + " tag missing in PID XML");
        }
        return (Element) list.item(0);
    }

    // Optional element (safe)
    private static Element getOptionalElement(Document doc, String tag) {
        NodeList list = doc.getElementsByTagName(tag);
        return list.getLength() > 0 ? (Element) list.item(0) : null;
    }

    // Attribute getter
    private static String getAttr(Element el, String attr) {
        return (el != null && el.hasAttribute(attr)) ? el.getAttribute(attr) : null;
    }

    // Case-insensitive attribute (VERY IMPORTANT for RD devices)
    private static String getAttrCaseInsensitive(Element el, String... attrs) {
        if (el == null) return null;

        for (String attr : attrs) {
            if (el.hasAttribute(attr)) {
                return el.getAttribute(attr);
            }
        }
        return null;
    }

    // Element text
    private static String getText(Element el) {
        return el != null ? el.getTextContent() : null;
    }

    // Clean Base64 (fast version)
    private static String cleanBase64(String val) {
        return val == null ? null : val.replace("\n", "").replace("\r", "").trim();
    }
}