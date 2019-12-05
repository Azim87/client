package com.kubatov.client.util;

import android.widget.Toast;

import com.kubatov.client.App;

public class ShowToast {
    public static void me(String message){
        Toast.makeText(App.instance, message, Toast.LENGTH_SHORT).show();
    }
}
