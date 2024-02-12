package com.io.tech.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.io.tech.repository.ProductId;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductPojo {

    private String intake_no;

    private long mobile_number;

    private long alternative_mobile_number;

    private String year;

    private String branch_name;

    private String branch_short_name;

    private String name;

    private String address1;

    private String address2;

    private long pincode;

    private String state;

    private String mail_id;

    private String make;

    private String model;

    private String laptop_serial_number;

    private String battery_serial_number;

    private String adapter_serial_number;

    private String problem_reported;

    private String damages;

    private String others;

    private int price;

    private String problem_identified;

    private String rejected_reason;

    private String status;

    private String stock_id;
    private byte[] pdf;
}

