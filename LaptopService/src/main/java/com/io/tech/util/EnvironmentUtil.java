package com.io.tech.util;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Data
@Configuration
public class EnvironmentUtil {

    @Value("${crossOriginUrl}")
    String crossOriginUrl;}
