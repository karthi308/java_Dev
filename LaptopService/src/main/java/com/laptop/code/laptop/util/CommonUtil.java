package com.laptop.code.laptop.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.laptop.code.laptop.entity.UserDetailsEntity;
import com.laptop.code.laptop.pojo.StandardResponseMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Slf4j
public class CommonUtil {
    @Autowired
    private RedisTemplate<String, String> redisCache;

    public static ResponseEntity<StandardResponseMessage> getReturnResponse(StandardResponseMessage result) {
        if (result.getSystemMessage().equals(PrincipalConstant.MESSAGE_TYPE_400_BAD_REQUEST_ERROR))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        else if (result.getSystemMessage().equals(PrincipalConstant.MESSAGE_TYPE_404_NOT_FOUND_ERROR)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        } else if (result.getSystemMessage().equals(PrincipalConstant.MESSAGE_TYPE_401_UNAUTHORIZED_ERROR))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
        else
            return ResponseEntity.ok(result);
    }

    public static String readHtmlFromFile(String filePath) {
        Resource resource = new ClassPathResource(filePath);
        String fileData = null;
        try {
            InputStream inputStream = resource.getInputStream();
            fileData = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileData;
    }

    public static byte[] getPdfByte(String htmlContent) {
        byte[] pdf = null;
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(htmlContent);
            renderer.layout();
            renderer.createPDF(byteArrayOutputStream);
            pdf = byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            log.error("Error Occurred in getPdfByte :" + e.getMessage());
        }
        return pdf;
    }

    public static UserDetailsEntity getASJsonArray(String object) {
        try {
            return new ObjectMapper().readValue(object, UserDetailsEntity.class);
        } catch (Exception e) {
            log.error("Error Occurred in common util getASJsonArray : " + e.getMessage());
        }
        return null;
    }

    public static String getASJsonString(Object object) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonString = objectMapper.writeValueAsString(object);
            System.out.println("fff " +jsonString);
            return jsonString;
        } catch (Exception e) {
            log.error("Error Occurred in common util getASJsonArray : " + e.getMessage());
        }
        return null;
    }
    public static Sort getSortedObject(String sortName){
        return Sort.by(Sort.Order.asc(sortName));
    }
}
