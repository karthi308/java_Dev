package com.laptop.code.laptop.entity;



import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;


import java.io.Serializable;
import java.util.Date;

@Data
@Table(name="user_details_tbl")
@Entity
public class UserDetailsEntity implements Serializable {

    @Id
    @Column(name="intake_no")
    private String intakeNo;
    @Column(name="name")
    private String name;

    @Column(name="address")
    private String address;

    @Column(name="city/town/village")
    private String city;

    @Column(name="state")
    private String state;

    @Column(name="pincode")
    private String pincode;

    @Column(name="mobile_number")
    private long mobileNumber;

    @Column(name="mail_id")
    private String mailId;

    @Column(name="make")
    private String make;
    @Column(name="model")
    private String model;
    @Column(name="sl_no")
    private String slNo;
    @Column(name="bt_sl_no")
    private String btSlNo;
    @Column(name="with_adapter")
    private boolean withAdapter;
    @Column(name="adapter_sl_no")
    private String adapterSlNo;
    @Column(name="problem")
    private String problem;
    @Column(name="damages")
    private String damages;

    @Column(name="status")
    private String status;

    @Column(name="rejected_reason")
    private String rejectedReason;

    @Column(name="yd")
    private String YD;

    @Column(name="problem_Identified")
    private  String problemIdentified;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(name="price")
    private long price;

    @Column(name="branch")
    private String branch;

    @Column(name="created_time")
    private Date createdTime;

    @Column(name="updated_time")
    private Date updatedTime;




}
