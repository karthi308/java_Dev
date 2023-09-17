package com.laptop.code.laptop.pojo;

import lombok.Data;

import java.util.List;

@Data
public class AddIntakePojoResponse {
    private List<?> customerDetails;
    private String pdf;
}
