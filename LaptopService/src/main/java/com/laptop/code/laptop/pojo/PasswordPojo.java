package com.laptop.code.laptop.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
public class PasswordPojo {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String oldPassword;

    private String newPassword;
    private String confirmPassword;
}
