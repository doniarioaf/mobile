package com.daltonpro.bizzapps.util.localstorage;

import android.content.Context;
import android.content.SharedPreferences;

public class Session {
    private static SharedPreferences pref;
    private static SharedPreferences.Editor editor;

    public static String SESSION_USERNAME = "username";
    public static String SESSION_USER_PASSWORD = "userpassword";
    public static String SESSION_USER_TOKEN = "token";
    public static String SESSION_CALLPLAN_LAST_SELECTED = "callplan_last_selected";
    public static String SESSION_USER_REMEMBER_ME = "rememberme";


    public static void beginInitialization(Context context) {
        pref = context.getSharedPreferences("MySession", 0); // 0 - for private mode
        editor = pref.edit();
    }

    public static void setSessionGlobal(String key, String val) {
        editor.putString(key, val);
        editor.commit();
    }

    public static String getSessionGlobal(String key) {
        return pref.getString(key, null);
    }

    public static void clearSessionGlobal(String key) {
        editor.remove(key);
        editor.commit();
    }

    public static void sessionClearEverything() {
        editor.clear();
        editor.commit();
    }
}
