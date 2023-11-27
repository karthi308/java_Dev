package com.laptop.code.laptop.pojo;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddUserPojo {

    private String userId;

    private String userName;

    private String pwd;

    private String branchName;

    private String mailId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private boolean userModificationAccess;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private boolean switchBranchAccess;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private boolean vendorStockAccess;

    private String status;

}
