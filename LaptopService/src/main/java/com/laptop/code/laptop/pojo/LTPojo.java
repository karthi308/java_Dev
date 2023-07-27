package com.laptop.code.laptop.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
public class LTPojo {

    private String name;
    private String address;
    private String city;
    private String state;
    private String pincode;
    private String mobileNumber;
    private String mailId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String make;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String model;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String SlNo;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String batterySlNo;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private boolean withAdapter;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String adapterSlNo;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String problem;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String damages;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String intakeNo;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String problemIdentified;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private long price;

}
