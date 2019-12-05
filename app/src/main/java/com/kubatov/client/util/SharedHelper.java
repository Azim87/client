package com.kubatov.client.util;

import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class SharedHelper {

    public static void setShared(Context context, String sharedKey, String key, String value){
        SharedPreferences.Editor sharedPref = context.getSharedPreferences(sharedKey, MODE_PRIVATE).edit();
        sharedPref.putString(key, value);
        sharedPref.apply();
    }

    public static String getShared(Context context, String sharedKey, String key ){
        SharedPreferences prefs = context.getSharedPreferences(sharedKey, MODE_PRIVATE);
        return prefs.getString(key, null);
    }
}
