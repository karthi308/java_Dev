package com.io.tech.util;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateFormatter {
    public static String getCurrentYearMonth() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy-MM");
        return currentDate.format(formatter).replace("-", "");
    }

    public static String getCurrentDate() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(Constants.DD_MMM_YYYY);
        return formatter.format(date);
    }
}
