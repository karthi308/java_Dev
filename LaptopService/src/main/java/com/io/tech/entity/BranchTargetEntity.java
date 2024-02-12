package com.io.tech.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "branch_target")
public class BranchTargetEntity {

    @Id
    @Column(name = "company_id")
    private String companyId;

    @Column(name = "branch_name")
    @JsonProperty("branch_name")
    private String branchName;

    @Column(name = "total_amount")
    private String totalAmount;

    @Column(name = "day_target")
    private String dayTarget;

    @Column(name = "day")
    private String day;
}

