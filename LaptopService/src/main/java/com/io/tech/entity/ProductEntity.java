package com.io.tech.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.io.tech.repository.ProductId;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "product_details")
public class ProductEntity {


    @EmbeddedId
    private ProductId id;

    @Column(name = "year")
    @JsonProperty("year")
    private String year;

    @Column(name = "branch_name")
    @JsonProperty("branch_name")
    private String branchName;

    @Column(name = "mobile_number")
    @JsonProperty("mobile_number")
    private long mobileNumber;

    @Column(name = "alternative_mobile_number")
    @JsonProperty("alternative_mobile_number")
    private long alternativeMobileNumber;

    @Column(name = "name")
    @JsonProperty("name")
    private String name;

    @Column(name = "address1")
    @JsonProperty("address1")
    private String address1;

    @Column(name = "address2")
    @JsonProperty("address2")
    private String address2;

    @Column(name = "pincode")
    @JsonProperty("pincode")
    private long pincode;

    @Column(name = "state")
    @JsonProperty("state")
    private String state;

    @Column(name = "mail_id")
    @JsonProperty("mail_id")
    private String mailId;

    @Column(name = "make")
    @JsonProperty("make")
    private String make;

    @Column(name = "model")
    @JsonProperty("model")
    private String model;

    @Column(name = "laptop_serial_number")
    @JsonProperty("laptop_serial_number")
    private String laptopSerialNumber;

    @Column(name = "battery_serial_number")
    @JsonProperty("battery_serial_number")
    private String batterySerialNumber;

    @Column(name = "adapter_serial_number")
    @JsonProperty("adapter_serial_number")
    private String adapterSerialNumber;

    @Column(name = "problem_reported")
    @JsonProperty("problem_reported")
    private String problemReported;

    @Column(name = "damages")
    @JsonProperty("damages")
    private String damages;

    @Column(name = "others")
    @JsonProperty("others")
    private String others;

    @Column(name = "price")
    @JsonProperty("price")
    private long price;

    @Column(name = "problem_identified")
    @JsonProperty("problem_identified")
    private String problemIdentified;

    @Column(name = "rejected_reason")
    @JsonProperty("rejected_reason")
    private String rejectedReason;

    @Column(name = "status")
    @JsonProperty("status")
    private String status;

    @Column(name = "user_id")
    @JsonProperty("user_id")
    private String userId;

    @Column(name = "pdf")
    @JsonProperty("pdf")
    private byte[] pdf;

}
