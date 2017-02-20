package com.moodybugs.testuber;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Android on 1/16/2017.
 */
public class SharedPrefDatabase {
    public static final String PREFS_KEY_NUMBER = "PREFS_NUMBER";

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;
    public SharedPrefDatabase(Context ctx) {
        this.context = ctx;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = sharedPreferences.edit();
    }

    public void StoreNumber(String data){
        editor.putString(PREFS_KEY_NUMBER, data);
        editor.commit();
    }
    public String RetriveNumber(){
        String text = sharedPreferences.getString(PREFS_KEY_NUMBER, null);
        return text;
    }
}
