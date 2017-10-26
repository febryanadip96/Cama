package com.example.biyan.ubama;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Biyan on 10/13/2017.
 */

public class UserToken {
    private final static String SHARED_DATA = "com.example.biyan.ubama.SHARED_DATA";
    private final static String TOKEN_KEY = "com.example.biyan.TOKEN_KEY";
    private final static String USER = "com.example.biyan.USER";
    private final static String NAMA = "com.example.biyan.NAMA";
    private final static String EMAIL = "com.example.biyan.EMAIL";

    public static String getToken(Context c) {
        SharedPreferences prefs = c.getSharedPreferences(SHARED_DATA, Context.MODE_PRIVATE);
        return prefs.getString(TOKEN_KEY, "");
    }

    public static void setToken(Context c, String token) {
        SharedPreferences prefs = c.getSharedPreferences(SHARED_DATA, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(TOKEN_KEY, token);
        editor.commit();
    }

    public static String getNama(Context c) {
        SharedPreferences prefs = c.getSharedPreferences(SHARED_DATA, Context.MODE_PRIVATE);
        return prefs.getString(NAMA, "");
    }

    public static void setNama(Context c, String nama) {
        SharedPreferences prefs = c.getSharedPreferences(SHARED_DATA, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(NAMA, nama);
        editor.commit();
    }

    public static String getEmail(Context c) {
        SharedPreferences prefs = c.getSharedPreferences(SHARED_DATA, Context.MODE_PRIVATE);
        return prefs.getString(EMAIL, "");
    }

    public static void setEmail(Context c, String email) {
        SharedPreferences prefs = c.getSharedPreferences(SHARED_DATA, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(EMAIL, email);
        editor.commit();
    }
}
