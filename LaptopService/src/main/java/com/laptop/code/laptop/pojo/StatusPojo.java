package com.laptop.code.laptop.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StatusPojo {

    private String intakeNo;

    private String searchNo;

    private long mobileNo;

    private String branch;

    private String rejectedReason;

    private String status;

    private String stockId;

    private String stockName;

    private String vendorName;

    private String branchName;

    private long price;

    private String warranty;

    private String transferTo;

    private String location;

    private String finalPrice;
}
