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
public class ProductId implements Serializable {

    @Column(name = "intake_no")
    @JsonProperty("intake_no")
    private String intakeNo;

    @JsonProperty("company_id")
    @Column(name = "company_id")
    private String companyId;

}
