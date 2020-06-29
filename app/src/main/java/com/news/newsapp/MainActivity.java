package com.news.newsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.news.newsapp.adapter.HeadlinesAdapter;
import com.news.newsapp.api.ApiClient;
import com.news.newsapp.model.Articles;
import com.news.newsapp.model.NewsModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    ApiClient apiClient;
    ShimmerRecyclerView recyclerView;
    HeadlinesAdapter adapter;
    Articles[] articles;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        apiClient=new ApiClient();
        initViews();
        setFCM();





    }

    private void setFCM() {
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O)
        {
            NotificationChannel channel1=new NotificationChannel("News","News", NotificationManager.IMPORTANCE_DEFAULT);

            NotificationManager manager=getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);
        }

        FirebaseMessaging.getInstance().subscribeToTopic("NewsApp")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "Hello";
                        if (!task.isSuccessful()) {
                            msg = "Failed";
                        }
                        Log.d("TAG", msg);
                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
    }


    private void setApiCall() {
        Call<NewsModel> newsModelCall = apiClient.getApiinterface().get_data("in","a00350c809d34cb294992a0c43471a86");
        newsModelCall.enqueue(new Callback<NewsModel>() {
            @Override
            public void onResponse(Call<NewsModel> call, Response<NewsModel> response) {
                System.out.println("No. of articles "+response.body().getTotalResults());
                System.out.println("Status "+response.body().getStatus());
                recyclerView.hideShimmerAdapter();
                articles = new Articles[Integer.parseInt(response.body().getTotalResults())];
                articles = response.body().getArticles();
                adapter = new HeadlinesAdapter(getApplicationContext() , articles);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<NewsModel> call, Throwable t) {

            }
        });
    }

    private void initViews() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.showShimmerAdapter();

    }

    @Override
    protected void onResume() {
        super.onResume();
        setApiCall();
    }
}