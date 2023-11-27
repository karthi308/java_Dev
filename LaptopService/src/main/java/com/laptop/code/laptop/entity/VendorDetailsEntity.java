package com.laptop.code.laptop.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@Table(name = "vendor_stock_details")
public class VendorDetailsEntity implements Serializable {
    @Id
    @Column(name = "stock_id")
    String stockId;

    @Column(name = "stock_name")
    String stockName;

    @Column(name = "price")
    int price;

    @Column(name = "vendor_name")
    String vendorName;

    @Column(name = "warranty")
    String warranty;

    @Column(name = "vendor_location")
    String vendorLocation;

    @Column(name = "status")
    String status;

    @Column(name = "transferred_to")
    String transferredTo;

    @Column(name = "year")
    int year;

}
