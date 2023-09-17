package com.laptop.code.laptop.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class StockDetailsPojo {


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
    private String warranty;

    @JsonProperty
    private String transferTo;


    @JsonProperty
    private String location;


}
