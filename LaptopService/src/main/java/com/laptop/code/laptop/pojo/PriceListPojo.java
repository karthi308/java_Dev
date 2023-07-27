package com.laptop.code.laptop.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
public class PriceListPojo {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String date;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String branch;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String list;
}
