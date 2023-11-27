package com.laptop.code.laptop.entity;


import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;


import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "customer_details")
@Entity
public class CustomerDetailsEntity implements Serializable {

    @Id
    @Column(name = "intake_no")
    private String intakeNo;

    @Column(name = "year")
    private int year;

    @Column(name = "name")
    private String name;

    @Column(name = "mobile_number")
    private long mobileNumber;

    @Column(name = "alternative_mobile_number")
    private long alternativeMobileNumber;

    @Column(name = "address")
    private String address;

    @Column(name = "city/town/village")
    private String city;

    @Column(name = "pincode")
    private String pincode;

    @Column(name = "state")
    private String state;


    @Column(name = "mail_id")
    private String mailId;

    @Column(name = "make")
    private String make;

    @Column(name = "model")
    private String model;

    @Column(name = "laptop_serial_no")
    private String laptopSlNo;

    @Column(name = "battery_serial_no")
    private String batterySlNo;

    @Column(name = "adapter_serial_no")
    private String adapterSlNo;

    @Column(name = "problem_reported")
    private String problemReported;

    @Column(name = "damages")
    private String damages;

    @Column(name = "others")
    private String others;

    @Column(name = "status")
    private String status;

    @Column(name = "rejected_reason")
    private String rejectedReason;

    @Column(name = "problem_Identified")
    private String problemIdentified;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(name = "price")
    private long price;

    @Column(name = "branch")
    private String branch;

    @Column(name = "stock_id")
    private String stockId;

    @Column(name = "final_price")
    private String finalPrice;

}
