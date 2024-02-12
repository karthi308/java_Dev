package com.io.tech.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.io.tech.repository.StockDetailsId;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

import java.math.BigInteger;

@Data
@Entity
@Table(name = "stock_details")
public class StockDetailsEntity {

    @EmbeddedId
    private StockDetailsId id;

    @Column(name = "stock_name")
    @JsonProperty("stock_name")
    private String stockName;

    @Column(name = "price")
    @JsonProperty("price")
    private int price;

    @Column(name = "warranty")
    @JsonProperty("warranty")
    private String warranty;

    @Column(name = "vendor_name")
    @JsonProperty("vendor_name")
    private String vendorName;

    @Column(name = "vendor_location")
    @JsonProperty("vendor_location")
    private String vendorLocation;

    @Column(name = "status")
    @JsonProperty("status")
    private String status;

    @Column(name = "transfer_to")
    @JsonProperty("transferred_to")
    private String transferTo;

    @Column(name = "year")
    @JsonProperty("year")
    private String year;

}

