package com.laptop.code.laptop.pojo;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
public class CreateNewUserPojo {

    private String userId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String userName;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String pwd;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String branch;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String mailId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String adminAccess;

}
