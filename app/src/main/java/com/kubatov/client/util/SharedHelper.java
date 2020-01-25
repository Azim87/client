package com.kubatov.client.util;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class SharedHelper {

    public static void setShared(Context context, String sharedKey, String key, String value){
        SharedPreferences.Editor sharedPref = context.getSharedPreferences(sharedKey, MODE_PRIVATE).edit();
        sharedPref.putString(key, value);
        sharedPref.apply();
    }

    public static void setSharedState(Context context, String sharedKey, String key, String key2,String key3, String value){
        SharedPreferences.Editor sharedPref = context.getSharedPreferences(sharedKey, MODE_PRIVATE).edit();
        sharedPref.putString(key, value);
        sharedPref.putString(key2, value);
        sharedPref.putString(key3, value);
        sharedPref.apply();
    }

    public static String getShared(Context context, String sharedKey, String key ){
        SharedPreferences prefs = context.getSharedPreferences(sharedKey, MODE_PRIVATE);
        return prefs.getString(key, null);
    }

    public static void saveState(Context context, String prefKey, boolean isFirstTime){
        SharedPreferences preferences = context.getSharedPreferences(prefKey, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("booleanKey", isFirstTime);
        editor.apply();
    }

    public static boolean getSavedState(Context context, String prefKey, boolean isFirstTime) {
        SharedPreferences preferences = context.getSharedPreferences(prefKey, MODE_PRIVATE);
        return preferences.getBoolean(prefKey, isFirstTime);
    }
}
