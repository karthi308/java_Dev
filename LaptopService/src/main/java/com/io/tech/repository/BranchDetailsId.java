package com.io.tech.repository;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
@Data
public class BranchDetailsId implements Serializable {

    @JsonProperty("company_id")
    @Column(name = "company_id")
    private String companyId;

    @JsonProperty("branch_name")
    @Column(name = "branch_name")
    private String branchName;
}
