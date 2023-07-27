package com.laptop.code.laptop.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class StockListPojo {


    private int stockId;

    @JsonProperty
    private String stockName;

    @JsonProperty
    private String vendorName;

    @JsonProperty
    private String branchName;

    @JsonProperty
    private String price;

    @JsonProperty
    private String warrntyFromDate;

    @JsonProperty
    private String warrntyToDate;

    @JsonProperty
    private String location;



}
