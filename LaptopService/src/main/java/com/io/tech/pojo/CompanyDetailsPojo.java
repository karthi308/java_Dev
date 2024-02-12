package com.io.tech.pojo;

import lombok.Data;

@Data
public class CompanyDetailsPojo {
    private String companyId;
    private String user_name;
    private String company_name;
    private String branch_name;
    private String branch_short_name;
    private String mail_id;
    private String address1;
    private String address2;
    private String state;
    private int pincode;
    private byte[] company_logo;
    private long mobile_number;
    private long telephone_number;
    private String status;
}
