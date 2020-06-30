package com.news.newsapp.sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

public class Store_category {
    public SharedPreferences preferences;
    public SharedPreferences.Editor editor;

    public Store_category(Context context)
    {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = preferences.edit();
    }

    public void set_category(String category)
    {
        editor.putString("category", category);
        editor.apply();
    }
    public String get_category()
    {
        return preferences.getString("category","General");
    }
}
