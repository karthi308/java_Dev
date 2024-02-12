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
public class StockDetailsId implements Serializable {

    @Column(name = "stock_id")
    @JsonProperty("stock_id")
    private String stockId;

    @Column(name = "company_id")
    @JsonProperty("company_id")
    private String companyId;


}
