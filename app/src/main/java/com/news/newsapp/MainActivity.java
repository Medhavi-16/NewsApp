package com.news.newsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.news.newsapp.adapter.CategoryAdapter;
import com.news.newsapp.adapter.HeadlinesAdapter;
import com.news.newsapp.api.ApiClient;
import com.news.newsapp.model.Articles;
import com.news.newsapp.model.NewsModel;
import com.news.newsapp.sharedpreferences.Store_category;
import com.news.newsapp.sharedpreferences.Store_country;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    ApiClient apiClient;
    ShimmerRecyclerView recyclerView;
    HeadlinesAdapter adapter;
    Articles[] articles;
    Spinner spinner;
    String[] categories;
    RecyclerView category_list;
    CategoryAdapter categoryAdapter;
    Store_category store_category;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        apiClient = new ApiClient();
        initViews();
        setCategories();
        setFCM();
        spinner_control();


    }

    private void setFCM() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel1 = new NotificationChannel("News", "News", NotificationManager.IMPORTANCE_DEFAULT);

            NotificationManager manager = getSystemService(NotificationManager.class);
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


    public void setApiCall() {
        recyclerView.showShimmerAdapter();
        Log.e("gftr",new Store_country(getApplicationContext()).get_current_country());
        Call<NewsModel> newsModelCall = apiClient.getApiinterface().get_data(new Store_country(getApplicationContext()).get_current_country_code(), store_category.get_category(), "a00350c809d34cb294992a0c43471a86");
        newsModelCall.enqueue(new Callback<NewsModel>() {
            @Override
            public void onResponse(Call<NewsModel> call, Response<NewsModel> response) {
                System.out.println("No. of articles " + response.body().getTotalResults());
                System.out.println("Status " + response.body().getStatus());
                recyclerView.hideShimmerAdapter();
                articles = new Articles[Integer.parseInt(response.body().getTotalResults())];
                articles = response.body().getArticles();
                adapter = new HeadlinesAdapter(MainActivity.this, articles);
                recyclerView.setAdapter(adapter);


            }

            @Override
            public void onFailure(Call<NewsModel> call, Throwable t) {

            }
        });
    }

    private void setCategories() {
        categories = new String[]{"General", "Entertainment", "Business", "Sports", "Technology", "Health", "Science"};
        categoryAdapter = new CategoryAdapter(MainActivity.this, categories, store_category);
        category_list.setAdapter(categoryAdapter);
    }

    private void initViews() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.showShimmerAdapter();
        spinner=findViewById(R.id.country_spinner);
        category_list = findViewById(R.id.recyclerCategory);
        store_category = new Store_category(MainActivity.this);





    }

    public void spinner_control()
    {
        ArrayList<String> list=new ArrayList<>();
        list.add("India (IN)");
        list.add("Bangladesh (BD)");
        list.add("China (CN)");
        list.add("Germany (DE)");
        list.add("USA (US)");
        list.add("UK (GB)");

        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item,list);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        spinner.post(new Runnable() {
            @Override
            public void run() {
                spinner.setSelection(new Store_country(getApplicationContext()).get_spinner_position());
            }
        });
        //  spinner.setSelection(0,false);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(getApplicationContext(),parent.getItemAtPosition(position).toString(),Toast.LENGTH_LONG).show();
                Store_country data=new Store_country(getApplicationContext());

                if (position != 0) {
                    data.set_current_country(parent.getItemAtPosition(position).toString());
                    data.set_current_position(position);
                }
                setApiCall();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        setApiCall();
    }



}