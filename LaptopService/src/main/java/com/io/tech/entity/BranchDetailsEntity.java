package com.io.tech.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.io.tech.repository.BranchDetailsId;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "branch_details")
public class BranchDetailsEntity {

    @EmbeddedId
    private BranchDetailsId id;

    @Column(name = "branch_short_name")
    @JsonProperty("branch_short_name")
    private String branchShortName;

    @Column(name = "address1")
    @JsonProperty("address1")
    private String address1;

    @Column(name = "address2")
    @JsonProperty("address2")
    private String address2;

    @Column(name = "state")
    @JsonProperty("state")
    private String state;

    @Column(name = "pincode")
    @JsonProperty("pincode")
    private int pincode;

    @Column(name = "mobile_number")
    @JsonProperty("mobile_number")
    private long mobileNumber;

    @Column(name = "telephone_number")
    @JsonProperty("telephone_number")
    private long telephoneNumber;

    @Column(name = "status")
    @JsonProperty("status")
    private String status;

    @Column(name = "day_target")
    private String dayTarget;

    @Column(name = "month_target")
    private String monthTarget;

    @Column(name = "year_target")
    private String yearTarget;
}


