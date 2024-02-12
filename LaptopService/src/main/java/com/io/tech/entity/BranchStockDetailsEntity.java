package com.io.tech.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.io.tech.repository.StockDetailsId;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "branch_stock_details")
public class BranchStockDetailsEntity {

    @EmbeddedId
    private StockDetailsId id;

    @JsonProperty("intake_no")
    @Column(name = "intake_no")
    private String intakeNo;

    @JsonProperty("branch_name")
    @Column(name = "branch_name")
    private String branchName;

    @JsonProperty("stock_name")
    @Column(name = "stock_name")
    private String stockName;

    @JsonProperty("price")
    @Column(name = "price")
    private int price;

    @JsonProperty("warranty")
    @Column(name = "warranty")
    private String warranty;

    @JsonProperty("transferred_from")
    @Column(name = "transferred_from")
    private String transferredFrom;

    @JsonProperty("status")
    @Column(name = "status")
    private String status;

    @JsonProperty("year")
    @Column(name = "year")
    private String year;

}

