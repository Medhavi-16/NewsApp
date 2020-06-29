package com.news.newsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.news.newsapp.api.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    ApiClient apiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        apiClient=new ApiClient();
        Call<NewsModel> newsModelCall = apiClient.getApiinterface().get_data("us","a00350c809d34cb294992a0c43471a86");
        newsModelCall.enqueue(new Callback<NewsModel>() {
            @Override
            public void onResponse(Call<NewsModel> call, Response<NewsModel> response) {
                System.out.println("No. of articles "+response.body().getTotalResults());
                System.out.println("Status "+response.body().getStatus());

            }

            @Override
            public void onFailure(Call<NewsModel> call, Throwable t) {

            }
        });
    }
}