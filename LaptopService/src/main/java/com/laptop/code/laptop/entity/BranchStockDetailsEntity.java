package com.laptop.code.laptop.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serializable;

@Entity
@Data
@Table(name = "branch_stock_details")
public class BranchStockDetailsEntity implements Serializable {
    @Id
    @Column(name = "stock_id")
    String stockId;

    @Column(name = "intake_no")
    String intakeNo;

    @Column(name = "year")
    String year;

    @Column(name = "stock_name")
    String stockName;

    @Column(name = "price")
    int price;

    @Column(name = "branch_name")
    String branchName;

    @Column(name = "transferred_from")
    String transferredFrom;

    @Column(name = "warranty")
    String warranty;

    @Column(name = "status")
    String status;

}
