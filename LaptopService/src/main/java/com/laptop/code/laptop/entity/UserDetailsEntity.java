package com.laptop.code.laptop.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;


import java.io.Serializable;

@Data
@Table(name="user_details")
@Entity
public class UserDetailsEntity implements Serializable {

    @Id
    @Column(name="user_id")
    private String userId;

    @Column(name="user_name")
    private String userName;

    @Column(name="pwd")
    private String pwd;

    @Column(name="mail_id")
    private String mailId;

    @Column(name="branch_name")
    private String branchName;

    @Column(name="user_modification_access")
    private boolean userModificationAccess;

    @Column(name="switch_branch_access")
    private boolean switchBranchAccess;

    @Column(name="vendor_stock_access")
    private boolean vendorStockAccess;

    @Column(name="user_key")
    private String userKey;

    @Column(name="status")
    private String status;

}
