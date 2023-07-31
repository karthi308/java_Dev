package com.laptop.code.laptop.util;

import com.laptop.code.laptop.pojo.StandardResponseMessage;

import java.util.List;

public class DefaultResponseMessage {
    public static StandardResponseMessage internalServerErrorResponse() {

        StandardResponseMessage standardResponse = new StandardResponseMessage();
        standardResponse.setSuccess(PrincipalConstant.MESSAGE_FALSE);
        standardResponse.setSystemMessage(PrincipalConstant.MESSAGE_TYPE_500_INTERNAL_SERVER_ERROR);
        standardResponse.setSystemMessageType(PrincipalConstant.MESSAGE_TYPE_FAILED);
        return standardResponse;
    }

    public static StandardResponseMessage badRequestResponse() {
        StandardResponseMessage standardResponse = new StandardResponseMessage();
        standardResponse.setSuccess(PrincipalConstant.MESSAGE_FALSE);
        standardResponse.setSystemMessage(PrincipalConstant.MESSAGE_TYPE_400_BAD_REQUEST_ERROR);
        standardResponse.setSystemMessageType(PrincipalConstant.MESSAGE_TYPE_FAILED);
        return standardResponse;
    }

    public static StandardResponseMessage successResponse(List<?> data) {

        StandardResponseMessage standardResponse = new StandardResponseMessage();
        standardResponse.setSuccess(PrincipalConstant.MESSAGE_TRUE);
        standardResponse.setSystemMessage(PrincipalConstant.MESSAGE_TYPE_200);
        standardResponse.setSystemMessageType(PrincipalConstant.MESSAGE_TYPE_SUCCESS);
        standardResponse.setData(data);
        return standardResponse;

    }
}
