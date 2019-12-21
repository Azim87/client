package com.kubatov.client.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateHelper {
    public static String convertToHour(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        return date = sdf.format(new Date());
    }

    public static String convertToDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        return date = sdf.format(new Date());
    }

    public static String convertUnixToDate(long dt) {
        Date date = new Date(dt);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String formatted = sdf.format(date);
        return formatted;
    }
}
