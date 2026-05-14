package com.rupiksha.recharge.recharge.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "Response")
public class FetchBillResponse {

    @JacksonXmlProperty(localName = "ResponseStatus")
    private String responseStatus;

    @JacksonXmlProperty(localName = "Description")
    private String description;

    @JacksonXmlProperty(localName = "MerTxnID")
    private String merTxnID;

    @JacksonXmlProperty(localName = "ConsumerID")
    private String consumerID;

    @JacksonXmlProperty(localName = "OrderId")
    private String orderId;

    @JacksonXmlProperty(localName = "ConsumerName")
    private String consumerName;

    @JacksonXmlProperty(localName = "DueAmount")
    private String dueAmount;

    @JacksonXmlProperty(localName = "DueDate")
    private String dueDate;

    @JacksonXmlProperty(localName = "BillDate")
    private String billDate;
}