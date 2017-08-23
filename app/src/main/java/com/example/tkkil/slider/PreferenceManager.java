package com.example.tkkil.slider;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by tkkil on 16-08-2017.
 */

public class PreferenceManager {
    private Context mContext;
    private SharedPreferences sharedPreferences;

    public PreferenceManager(Context mContext) {
        this.mContext = mContext;
        getSharedPreference();
    }

    public void getSharedPreference() {
        sharedPreferences = mContext.getSharedPreferences("DATA", Context.MODE_PRIVATE);
    }

    public void writeSharedPreference() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("KEY_1", "INIT_OK");
        editor.commit();
    }

    public boolean checkPreference() {
        boolean status;
        if (sharedPreferences.getString("KEY_1", "null").equals("null")) {
            status = false;
        } else {
            status = true;
        }
        return status;
    }

    public void clearSharedPreference() {
        sharedPreferences.edit().clear().commit();
    }
}
