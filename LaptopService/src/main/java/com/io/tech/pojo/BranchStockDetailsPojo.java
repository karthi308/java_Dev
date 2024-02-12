package com.io.tech.pojo;

import lombok.Data;

@Data
public class BranchStockDetailsPojo {
    private String stock_id;
    private String intake_no;
    private String branch_name;
    private String transfer_to;
    private String stock_name;
    private int price;
    private String warranty;
    private String status;
}
