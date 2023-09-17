package com.laptop.code.laptop.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatter {
    public static String getCurrentDate() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(Constant.DD_MMM_YYYY);
        String toDate = formatter.format(date);
        return toDate;
    }
}
