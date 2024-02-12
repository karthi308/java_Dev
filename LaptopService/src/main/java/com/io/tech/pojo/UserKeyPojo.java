package com.io.tech.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserKeyPojo {

    @JsonProperty("user_key")
    private String userKey;
}
