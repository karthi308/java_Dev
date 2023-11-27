package com.laptop.code.laptop.util;

import com.laptop.code.laptop.pojo.MessageResponsePojo;
import com.laptop.code.laptop.pojo.StandardResponseMessage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StandardResposneUtil {
    public static StandardResponseMessage successResponse(List<?> data) {

        StandardResponseMessage standardResponse = new StandardResponseMessage();
        standardResponse.setSuccess(PrincipalConstant.MESSAGE_TRUE);
        standardResponse.setSystemMessage(PrincipalConstant.MESSAGE_TYPE_200);
        standardResponse.setSystemMessageType(PrincipalConstant.MESSAGE_TYPE_SUCCESS);
        standardResponse.setData(data);
        return standardResponse;

    }
    public static StandardResponseMessage successMessage(String message) {
        MessageResponsePojo messageResponsePojo =new MessageResponsePojo();
        messageResponsePojo.setMessage(message);
        StandardResponseMessage standardResponse = new StandardResponseMessage();
        standardResponse.setSuccess(PrincipalConstant.MESSAGE_TRUE);
        standardResponse.setSystemMessage(PrincipalConstant.MESSAGE_TYPE_200);
        standardResponse.setSystemMessageType(PrincipalConstant.MESSAGE_TYPE_SUCCESS);
        standardResponse.setData(Collections.singletonList(messageResponsePojo));
        return standardResponse;

    }
    public static StandardResponseMessage noDataResponse() {
        List<?> data =new ArrayList<>();
        StandardResponseMessage standardResponse = new StandardResponseMessage();
        standardResponse.setSuccess(PrincipalConstant.MESSAGE_FALSE);
        standardResponse.setSystemMessage(PrincipalConstant.MESSAGE_TYPE_200);
        standardResponse.setSystemMessageType(PrincipalConstant.MESSAGE_TYPE_SUCCESS);
        standardResponse.setData(data);
        return standardResponse;

    }
    public static StandardResponseMessage noDataMessage(String message) {
        MessageResponsePojo messageResponsePojo =new MessageResponsePojo();
        messageResponsePojo.setMessage(message);
        StandardResponseMessage standardResponse = new StandardResponseMessage();
        standardResponse.setSuccess(PrincipalConstant.MESSAGE_FALSE);
        standardResponse.setSystemMessage(PrincipalConstant.MESSAGE_TYPE_200);
        standardResponse.setSystemMessageType(PrincipalConstant.MESSAGE_TYPE_SUCCESS);
        standardResponse.setData(Collections.singletonList(messageResponsePojo));
        return standardResponse;

    }

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

    public static StandardResponseMessage unAuthorizedResponse() {
        StandardResponseMessage standardResponse = new StandardResponseMessage();
        standardResponse.setSuccess(PrincipalConstant.MESSAGE_FALSE);
        standardResponse.setSystemMessage(PrincipalConstant.MESSAGE_TYPE_401_UNAUTHORIZED_ERROR);
        standardResponse.setSystemMessageType(PrincipalConstant.MESSAGE_TYPE_FAILED);
        return standardResponse;
    }

    public static StandardResponseMessage notFound() {
        StandardResponseMessage standardResponse = new StandardResponseMessage();
        standardResponse.setSuccess(PrincipalConstant.MESSAGE_FALSE);
        standardResponse.setSystemMessage(PrincipalConstant.MESSAGE_TYPE_404_NOT_FOUND_ERROR);
        standardResponse.setSystemMessageType(PrincipalConstant.MESSAGE_TYPE_FAILED);
        return standardResponse;
    }

    public static StandardResponseMessage badRequestResponse(String message) {
        StandardResponseMessage standardResponse = new StandardResponseMessage();
        standardResponse.setSuccess(PrincipalConstant.MESSAGE_FALSE);
        standardResponse.setSystemMessage(PrincipalConstant.MESSAGE_TYPE_404_NOT_FOUND_ERROR);
        standardResponse.setSystemMessageType(PrincipalConstant.MESSAGE_TYPE_FAILED);
        standardResponse.setData(Collections.singletonList(message));
        return standardResponse;
    }
}
