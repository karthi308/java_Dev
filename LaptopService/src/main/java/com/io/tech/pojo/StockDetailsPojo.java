package com.io.tech.pojo;

import lombok.Data;

@Data
public class StockDetailsPojo {
    private String stock_id;
    private String stock_name;
    private int price;
    private String vendor_name;
    private String vendor_location;
    private String warranty;
    private String transfer_to;
    private String status;
    private String manufacturer;
}
