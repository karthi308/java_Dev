package com.io.tech.pojo;

import lombok.Data;

@Data
public class BranchDetailsPojo {
    private String branch_name;
    private String branch_short_name;
    private String address1;
    private String address2;
    private String state;
    private int pincode;
    private long mobile_number;
    private long telephone_number;
    private String status;
}
