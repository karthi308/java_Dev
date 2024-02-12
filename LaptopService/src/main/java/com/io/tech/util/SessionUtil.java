package com.io.tech.util;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


@Component
public class SessionUtil {

    private static Map<String, String> userData = new HashMap<>();

    public static void setValue(String key, String value) {
        userData.put(key, value);
    }

    public static String getValue(String key) {
        return userData.get(key);
    }
}