package com.io.tech.util;

import com.io.tech.pojo.MessageResponsePojo;
import com.io.tech.pojo.StandardMessageResponse;

import java.util.Collections;
import java.util.List;

public class StandardResponseUtil {
    public static StandardMessageResponse successResponse(List<?> data) {

        StandardMessageResponse standardResponse = new StandardMessageResponse();
        standardResponse.setSuccess(Constants.MESSAGE_TRUE);
        standardResponse.setSystemMessage(Constants.MESSAGE_TYPE_200);
        standardResponse.setSystemMessageType(Constants.MESSAGE_TYPE_SUCCESS);
        standardResponse.setData(data);
        return standardResponse;

    }



    public static StandardMessageResponse successMessage(String message) {
        MessageResponsePojo messageResponsePojo = new MessageResponsePojo();
        messageResponsePojo.setMessage(message);
        StandardMessageResponse standardResponse = new StandardMessageResponse();
        standardResponse.setSuccess(Constants.MESSAGE_TRUE);
        standardResponse.setSystemMessage(Constants.MESSAGE_TYPE_200);
        standardResponse.setSystemMessageType(Constants.MESSAGE_TYPE_SUCCESS);
        standardResponse.setData(Collections.singletonList(messageResponsePojo));
        return standardResponse;

    }

    public static StandardMessageResponse noDataMessage(String message) {
        MessageResponsePojo messageResponsePojo = new MessageResponsePojo();
        messageResponsePojo.setMessage(message);
        StandardMessageResponse standardResponse = new StandardMessageResponse();
        standardResponse.setSuccess(Constants.MESSAGE_FALSE);
        standardResponse.setSystemMessage(Constants.MESSAGE_TYPE_200);
        standardResponse.setSystemMessageType(Constants.MESSAGE_TYPE_SUCCESS);
        standardResponse.setData(Collections.singletonList(messageResponsePojo));
        return standardResponse;

    }

    public static StandardMessageResponse internalServerErrorResponse() {

        StandardMessageResponse standardResponse = new StandardMessageResponse();
        standardResponse.setSuccess(Constants.MESSAGE_FALSE);
        standardResponse.setSystemMessage(Constants.MESSAGE_TYPE_500_INTERNAL_SERVER_ERROR);
        standardResponse.setSystemMessageType(Constants.MESSAGE_TYPE_FAILED);
        return standardResponse;
    }

    public static StandardMessageResponse badRequestResponse() {
        StandardMessageResponse standardResponse = new StandardMessageResponse();
        standardResponse.setSuccess(Constants.MESSAGE_FALSE);
        standardResponse.setSystemMessage(Constants.MESSAGE_TYPE_400_BAD_REQUEST_ERROR);
        standardResponse.setSystemMessageType(Constants.MESSAGE_TYPE_FAILED);
        return standardResponse;
    }

    public static StandardMessageResponse unAuthorizedResponse() {
        StandardMessageResponse standardResponse = new StandardMessageResponse();
        standardResponse.setSuccess(Constants.MESSAGE_FALSE);
        standardResponse.setSystemMessage(Constants.MESSAGE_TYPE_401_UNAUTHORIZED_ERROR);
        standardResponse.setSystemMessageType(Constants.MESSAGE_TYPE_FAILED);
        return standardResponse;
    }

    public static StandardMessageResponse notFound() {
        StandardMessageResponse standardResponse = new StandardMessageResponse();
        standardResponse.setSuccess(Constants.MESSAGE_FALSE);
        standardResponse.setSystemMessage(Constants.MESSAGE_TYPE_404_NOT_FOUND_ERROR);
        standardResponse.setSystemMessageType(Constants.MESSAGE_TYPE_FAILED);
        return standardResponse;
    }

    public static StandardMessageResponse badRequestResponse(String message) {
        StandardMessageResponse standardResponse = new StandardMessageResponse();
        standardResponse.setSuccess(Constants.MESSAGE_FALSE);
        standardResponse.setSystemMessage(Constants.MESSAGE_TYPE_404_NOT_FOUND_ERROR);
        standardResponse.setSystemMessageType(Constants.MESSAGE_TYPE_FAILED);
        standardResponse.setData(Collections.singletonList(message));
        return standardResponse;
    }
}

