package com.example.biyan.ubama;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Biyan on 10/13/2017.
 */

public class TokenSaver {
    private final static String SHARED_PREF_NAME = "com.example.biyan.ubama.SHARED_PREF_NAME";
    private final static String TOKEN_KEY = "com.example.biyan.TOKEN_KEY";

    public static String getToken(Context c) {
        SharedPreferences prefs = c.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return prefs.getString(TOKEN_KEY, "");
    }

    public static void setToken(Context c, String token) {
        SharedPreferences prefs = c.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(TOKEN_KEY, token);
        editor.commit();
    }
}
