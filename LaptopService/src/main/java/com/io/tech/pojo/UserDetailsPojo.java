package com.io.tech.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDetailsPojo {
    private String user_name;
    private String branch_name;
    private String branch_short_name;
    private String mail_id;
    private boolean company_details_access;
    private boolean switch_branch_access;
    private boolean user_details_access;
    private boolean vendor_stock_access;
    private String user_key;
    private String status;
    private byte[] profile_image;
}

