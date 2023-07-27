package com.laptop.code.laptop.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class OtpPojo {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String userId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String mailId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private int otp;
}
