package com.news.newsapp.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ApiClient {
    Retrofit_interface apiinterface;
    public Retrofit_interface getApiinterface() {
        return apiinterface;
    }

    public ApiClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://newsapi.org/v2/")              // define the base url which is common in all urls
                .addConverterFactory(GsonConverterFactory.create())               // used for convert Json file into the object
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        apiinterface = retrofit.create(Retrofit_interface.class);                 // create the instance of Retrofit interface
    }
}
