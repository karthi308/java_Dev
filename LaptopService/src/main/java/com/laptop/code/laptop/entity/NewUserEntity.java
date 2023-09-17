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
@Table(name="new_user_tbl")
@Entity
public class NewUserEntity implements Serializable {

    @Id
    @Column(name="user_id")
    private String userId;

    @Column(name="user_name")
    private String userName;

    @Column(name="pwd")
    private String pwd;

    @Column(name="mail_id")
    private String mailId;

    @Column(name="branch")
    private String branch;

    @Column(name="admin_access")
    private String adminAccess;

    @Column(name="switch_branch_name")
    private String switchBranchName;

    @Column(name="user_key")
    private String userKey;

}
