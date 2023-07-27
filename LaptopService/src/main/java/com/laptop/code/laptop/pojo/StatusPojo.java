package com.laptop.code.laptop.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class StatusPojo {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String intakeNo;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String searchNo;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String mobileNo;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String branch;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String rejectedReason;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String status;
}
