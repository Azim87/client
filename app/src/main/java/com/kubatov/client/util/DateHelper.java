package com.kubatov.client.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateHelper {
    public static String convertToHour(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        return date = sdf.format(new Date());
    }
}
