package com.io.tech.util;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Validator {

    public static boolean hasData(int data) {
        return data != 0;
    }

    public static boolean hasData(long data) {
        return data != 0;
    }

    public static boolean hasData(String data) {
        return data != null && !data.isEmpty();
    }

    public static boolean hasData(Object data) {
        return data != null;
    }

    public static boolean hasData(List<?> data) {
        return data != null && !data.isEmpty();
    }

    public static boolean hasData(Optional<?> data) {
        return data.isPresent();
    }

    public static boolean hasData(Map<?, ?> data) {
        return data != null && !data.isEmpty();
    }
}
