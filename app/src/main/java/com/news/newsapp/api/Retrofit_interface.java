package com.news.newsapp.api;

import com.news.newsapp.model.NewsModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Retrofit_interface {

    @GET("top-headlines")
    Call<NewsModel> get_data(@Query("country") String country, @Query("category") String category, @Query("apiKey") String apiKey);

    /*@GET
    Call<NewsModel> get_category_data(@Query("country") String country, @Query("category") String category, @Query("apiKey") String apiKey);*/
}
