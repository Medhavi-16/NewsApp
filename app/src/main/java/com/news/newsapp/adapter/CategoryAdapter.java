package com.news.newsapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.joooonho.SelectableRoundedImageView;
import com.news.newsapp.MainActivity;
import com.news.newsapp.R;
import com.news.newsapp.model.Articles;
import com.news.newsapp.sharedpreferences.Store_category;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryHolder>

    {
        private String[] categories;
        private Context mContext;
        Store_category category;


    public CategoryAdapter(Context context, String[] categories, Store_category category){
        mContext = context;
        this.categories= categories;
        this.category = category;



    }

        @NonNull
        @Override
        public CategoryHolder onCreateViewHolder (@NonNull ViewGroup parent,
        int viewType){
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.category_item, parent, false);
        return new CategoryHolder(view);
    }

        @Override
        public void onBindViewHolder (@NonNull CategoryHolder holder,final int position){
        setData(holder, position);
        setOnClickListeners(holder, position);
    }

        private void setOnClickListeners ( final CategoryAdapter.CategoryHolder holder, final int position){
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.category.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
                category.set_category(categories[position]);
                notifyDataSetChanged();
                if (mContext instanceof MainActivity) {
                    ((MainActivity)mContext).setApiCall();
                }
            }
        });

    }

        private void setData (CategoryHolder holder,int position){
            holder.category.setText(categories[position]);
            if(category.get_category().equals(categories[position]))
                holder.category.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
            else
                holder.category.setTextColor(mContext.getResources().getColor(R.color.darkGrey));




    }


        @Override
        public int getItemCount () {
        return categories.length;

    }

        @Override
        public long getItemId ( int position){
        return position;
    }

        @Override
        public int getItemViewType ( int position){
        return position;
    }

        class CategoryHolder extends RecyclerView.ViewHolder {

            TextView category;


            private CategoryHolder(@NonNull View itemView) {
                super(itemView);
                setGlobals(itemView);
                //setOnClickListeners();
            }

            /*private void setOnClickListeners() {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                      category.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
                    }
                });
            }*/

            private void setGlobals(View itemView) {
                category = itemView.findViewById(R.id.category_text);
            }

        }


    }

