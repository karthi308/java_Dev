package com.laptop.code.laptop.util;

import com.laptop.code.laptop.pojo.StandardResponseMessage;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class CommonUtil {
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
}
