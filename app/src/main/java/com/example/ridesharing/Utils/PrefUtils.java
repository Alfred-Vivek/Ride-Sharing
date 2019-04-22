package com.example.ridesharing.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PrefUtils {
    public static final String user_name =  "email";
    public static final String user_email  = "user_email";
    public static final String mc_address = "mcaddress";
    public static void saveToPrefs(Context context, String key, String value){
        SharedPreferences prefs         = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key,value);
        editor.commit();

    }
    public static String getFromPrefs(Context context, String key, String defaultValue){
        SharedPreferences preferences   =       PreferenceManager.getDefaultSharedPreferences(context);
        try{
            return preferences.getString(key,defaultValue);
        }catch (Exception e){
            e.printStackTrace();
            return defaultValue;
        }
    }

    public static void removePrefs(Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        final SharedPreferences.Editor editor = preferences.edit();
        editor.putString(user_name,"");
        editor.putString(user_email,"");
        editor.putString(mc_address,"");
        editor.commit();
    }
}