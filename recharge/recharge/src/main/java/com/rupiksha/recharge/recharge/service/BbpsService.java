package com.rupiksha.recharge.recharge.service;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import com.rupiksha.recharge.recharge.dto.FetchBillRequest;
import com.rupiksha.recharge.recharge.dto.FetchBillResponse;

import com.rupiksha.recharge.recharge.dto.PayBillRequest;
import com.rupiksha.recharge.recharge.dto.PayBillResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
@Slf4j
public class BbpsService {

    private final RestTemplate restTemplate;

    @Value("${venus.auth.key}")
    private String authKey;

    @Value("${venus.auth.pass}")
    private String authPass;

    // ==============================
    // FETCH BILL
    // ==============================

    public FetchBillResponse fetchBill(
            FetchBillRequest request
    ) {

        try {

            String merchantRefNo =
                    generateTxnId();

            String subDivValue =

                    "INS".equals(
                            request.getServiceType()
                    )

                            ? request.getSubDiv()

                            : encode(
                            request.getSubDiv()
                    );

            String url =
                    "https://venusrecharge.co.in/FetchBill.aspx"
                            + "?authkey=" + authKey
                            + "&authpass=" + authPass
                            + "&opcode="
                            + encode(request.getOpcode())
                            + "&Merchantrefno="
                            + merchantRefNo
                            + "&ConsumerID="
                            + encode(request.getConsumerId())
                            + "&SubDiv="
                            + subDivValue
                            + "&ConsumerMobileNo="
                            + encode(request.getConsumerMobileNo())
                            + "&Field1="
                            + encode(request.getField1())
                            + "&Field2="
                            + encode(request.getField2())
                            + "&ServiceType="
                            + encode(request.getServiceType());

//            log.info(
//                    "Fetch Bill URL : {}",
//                    url
//            );

            String xmlResponse =
                    restTemplate.getForObject(
                            url,
                            String.class
                    );
//
//            log.info(
//                    "BBPS Fetch Response : {}",
//                    xmlResponse
//            );

            XmlMapper xmlMapper =
                    new XmlMapper();

            return xmlMapper.readValue(
                    xmlResponse,
                    FetchBillResponse.class
            );

        } catch (Exception e) {

//            log.error(
//                    "BBPS Fetch Error : {}",
//                    e.getMessage()
//            );

            throw new RuntimeException(
                    "Unable To Fetch Bill"
            );
        }
    }

    // ==============================
    // PAY BILL
    // ==============================

    public PayBillResponse payBill(
            PayBillRequest request
    ) {

        try {

            String merchantRefNo =
                    generateTxnId();

            String subDivValue =

                    "INS".equals(
                            request.getServiceType()
                    )

                            ? request.getSubDiv()

                            : encode(
                            request.getSubDiv()
                    );

            String url =
                    "https://venusrecharge.co.in/PaymentBill.aspx"
                            + "?authkey=" + authKey
                            + "&authpass=" + authPass
                            + "&opcode="
                            + encode(request.getOpcode())
                            + "&Merchantrefno="
                            + merchantRefNo
                            + "&ConsumerID="
                            + encode(request.getConsumerId())
                            + "&ConsumerMobileNo="
                            + encode(request.getConsumerMobileNo())
                            + "&ServiceType="
                            + encode(request.getServiceType())
                            + "&Amount="
                            + request.getAmount()
                            + "&Orderid="
                            + encode(request.getOrderId())
                            + "&SubDiv="
                            + subDivValue
                            + "&Field1="
                            + encode(request.getField1())
                            + "&Field2="
                            + encode(request.getField2());

            log.info(
                    "Pay Bill URL : {}",
                    url
            );

            String xmlResponse =
                    restTemplate.getForObject(
                            url,
                            String.class
                    );

            log.info(
                    "BBPS Pay Response : {}",
                    xmlResponse
            );

            XmlMapper xmlMapper =
                    new XmlMapper();

            return xmlMapper.readValue(
                    xmlResponse,
                    PayBillResponse.class
            );

        } catch (Exception e) {

            log.error(
                    "BBPS Pay Error : {}",
                    e.getMessage()
            );

            throw new RuntimeException(
                    "Unable To Pay Bill"
            );
        }
    }

    // ==============================
    // URL ENCODE
    // ==============================

    private String encode(
            String value
    ){

        return URLEncoder.encode(

                value == null
                        ? ""
                        : value,

                StandardCharsets.UTF_8
        );
    }

    // ==============================
    // GENERATE TXN ID
    // ==============================

    private String generateTxnId(){

        return (
                System.currentTimeMillis()
                        + ""
                        + (int)(Math.random() * 10)
        ).substring(0, 14);
    }
}