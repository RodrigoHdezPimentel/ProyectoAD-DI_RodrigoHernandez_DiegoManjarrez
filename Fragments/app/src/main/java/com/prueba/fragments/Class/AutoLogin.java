package com.prueba.fragments.Class;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class AutoLogin
{
    static final String PREF_USER_NAME= "username";
    static final String PREF_USER_PASS= "password";

    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void setUserName(Context ctx, String userName)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_NAME, userName);
        editor.commit();
    }

    public static String getPassord(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_USER_PASS, "");
    }
    public static String getUserName(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_USER_NAME, "");
    }
    public static void setPrefUserPass(Context ctx, String password)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_PASS, password);
        editor.commit();
    }

}
