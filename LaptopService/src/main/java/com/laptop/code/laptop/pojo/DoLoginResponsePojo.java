package com.laptop.code.laptop.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
public class DoLoginResponsePojo {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String loginStatus;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String userKey;
}
