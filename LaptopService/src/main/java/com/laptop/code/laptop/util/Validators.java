package com.laptop.code.laptop.util;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Validators {

    public static boolean hasData(int data) {
        if (data != 0) {
            return true;
        }
        return false;
    }
    public static boolean hasData(String data) {
        if (data != null && !data.isEmpty()) {
            return true;
        }
        return false;
    }

    public static boolean hasData(Object data) {
        if (data != null) {
            return true;
        }
        return false;
    }

    public static boolean hasData(List<?> data) {
        if (data != null && data.size() > 0) {
            return true;
        }
        return false;
    }
    public static boolean hasData(Optional<?> data) {
        if (!data.isEmpty()) {
            return true;
        }
        return false;
    }

    public static boolean hasData(Map<?, ?> data) {
        if (data != null && data.size() > 0) {
            return true;
        }
        return false;
    }
}
