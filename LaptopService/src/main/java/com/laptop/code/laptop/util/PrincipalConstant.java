package com.laptop.code.laptop.util;

import lombok.Data;

@Data
public class PrincipalConstant {
    public static final String MESSAGE_TYPE_SUCCESS = "success";
    public static final String MESSAGE_TYPE_FAILED = "failed";
    public static final boolean MESSAGE_TRUE = true;
    public static final boolean MESSAGE_FALSE = false;
    public static final String MESSAGE_TYPE_200 = "200 - Success";
    public static final String MESSAGE_TYPE_400_BAD_REQUEST_ERROR = "400 - Bad Request";
    public static final String MESSAGE_TYPE_401_UNAUTHORIZED_ERROR = "401 - Unauthorized";
    public static final String MESSAGE_TYPE_404_NOT_FOUND_ERROR = "404 - Not Found";
    public static final String MESSAGE_TYPE_500_INTERNAL_SERVER_ERROR = "500 - Internal Server Error";
}
