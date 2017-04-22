package com.example.android.sunshine.app.util;

/**
 * Created by mdocevski on 22.4.17.
 */

public class StringUtils {
    public static boolean isNullOrEmpty(String string){
        return string == null || string.equalsIgnoreCase("");
    }
}
