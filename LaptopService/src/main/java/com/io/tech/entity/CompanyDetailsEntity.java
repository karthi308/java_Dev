package com.io.tech.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "company_details")
public class CompanyDetailsEntity {
    @Id
    @Column(name = "company_id")
    @JsonProperty("company_id")
    private String companyId;

    @Column(name = "company_name")
    @JsonProperty("company_name")
    private String companyName;

    @Column(name = "company_short_name")
    @JsonProperty("company_short_name")
    private String companyShortName;

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

    @Column(name = "company_logo")
    @JsonProperty("company_logo")
    private byte[] companyLogo;

    @Column(name = "mobile_number")
    @JsonProperty("mobile_number")
    private long mobileNumber;

    @Column(name = "telephone_number")
    @JsonProperty("telephone_number")
    private long telephoneNumber;

    @Column(name = "status")
    @JsonProperty("status")
    private String status;
}
