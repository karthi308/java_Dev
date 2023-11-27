package com.laptop.code.laptop.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class StockDetailsPojo {


    private String intakeNo;
    
    private String stockId;

    
    private String stockName;

    
    private String vendorName;

    
    private String branchName;

    private String stockStatus;

    
    private int price;

    
    private String warranty;

    
    private String transferTo;


    
    private String vendorLocation;


}
