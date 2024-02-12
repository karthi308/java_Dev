package com.io.tech.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.StreamUtils;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Slf4j
public class CommonUtil {
    public static String getUuid() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replace("-", "");
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

}
