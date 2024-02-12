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
public class UserDetailsId implements Serializable {

    @Column(name = "company_id")
    @JsonProperty("company_id")
    private String companyId;

    @Column(name = "user_id")
    @JsonProperty("user_id")
    private String userId;

}
