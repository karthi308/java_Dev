package com.laptop.code.laptop.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
public class CustomerDetailsPojo {

    private String name;
    private String street;
    private String city_village_town;
    private String state;
    private String pincode;
    private Long mobileNo;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long alternativeMobileNo;

    private String mailId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String make;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String model;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String laptopSerialNo;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String batterySerialNo;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String adapterSerialNo;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String problemReported;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String damages;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String intakeNo;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String problemIdentified;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private long price;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String others;

}
