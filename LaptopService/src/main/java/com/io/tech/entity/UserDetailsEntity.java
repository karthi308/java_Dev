package com.io.tech.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.io.tech.repository.UserDetailsId;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "user_details")
public class UserDetailsEntity {


    @EmbeddedId
    private UserDetailsId id;

    @Column(name = "user_name")
    @JsonProperty("user_name")
    private String userName;

    @Column(name = "password")
    @JsonProperty("password")
    private String password;

    @Column(name = "mail_id")
    @JsonProperty("mail_id")
    private String mailId;

    @Column(name = "branch_name")
    @JsonProperty("branch_name")
    private String branchName;

    @Column(name = "branch_short_name")
    @JsonProperty("branch_short_name")
    private String branchShortName;

    @Column(name = "company_details_access")
    @JsonProperty("company_details_access")
    private boolean companyDetailsAccess;

    @Column(name = "user_details_access")
    @JsonProperty("user_details_access")
    private boolean userDetailsAccess;

    @Column(name = "switch_branch_access")
    @JsonProperty("switch_branch_access")
    private boolean switchBranchAccess;

    @Column(name = "vendor_stock_access")
    @JsonProperty("vendor_stock_access")
    private boolean vendorStockAccess;

    @Column(name = "user_key")
    @JsonProperty("user_key")
    private String userKey;

    @Column(name = "status")
    @JsonProperty("status")
    private String status;

    @Column(name = "profile_image")
    @JsonProperty("profile_image")
    private byte[] profileImage;
}
