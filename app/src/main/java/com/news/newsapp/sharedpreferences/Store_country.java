package com.news.newsapp.sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

public class Store_country {

    public SharedPreferences preferences;
    public SharedPreferences.Editor editor;

    public Store_country(Context context)
    {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = preferences.edit();
    }

    public int get_spinner_position()
    {
        return preferences.getInt("current_country_position",0);
    }

    public void set_current_position(int position)
    {
        editor.putInt("current_country_position",position);
        editor.apply();
    }

    public String get_current_country()
    {
        return preferences.getString("current_country","NA");

    }
    public void set_current_country(String name)
    {
        editor.putString("current_country",name);
        editor.apply();
        Log.e("uijh",name);
    }
    public String get_current_country_code() {
        String s = preferences.getString("current_country", "NA");
        if (s == "NA")
            return "IN";
        else {
            String[] a = s.split("\\(");
            return a[1].substring(0, 2);
        }
    }
}
