package com.io.tech.pojo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class StandardMessageResponse {
    private String systemMessage;
    private String systemMessageType;
    private boolean success;

    private List<?> data = new ArrayList<>();
}
