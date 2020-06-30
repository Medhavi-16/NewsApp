package com.news.newsapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.method.LinkMovementMethod;
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
import com.news.newsapp.model.Articles;
import com.news.newsapp.R;

public class HeadlinesAdapter extends RecyclerView.Adapter<HeadlinesAdapter.HeadlinesHolder> {
    private Articles[] articles;
    private Context mContext;


    public HeadlinesAdapter(Context context, Articles[] articles) {
        mContext = context;
        this.articles = articles;



    }

    @NonNull
    @Override
    public HeadlinesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new HeadlinesHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HeadlinesHolder holder, final int position) {
        setData(holder, position);
        setOnClickListeners(holder, position);
    }

    private void setOnClickListeners(final HeadlinesHolder holder, final int position) {
        holder.expand_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.content.setVisibility(View.VISIBLE);
                holder.expand_more.setVisibility(View.GONE);
                holder.expand_less.setVisibility(View.VISIBLE);
                holder.visit_url.setVisibility(View.VISIBLE);
                holder.url_layout.setVisibility(View.VISIBLE);
            }
        });

        holder.expand_less.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.content.setVisibility(View.GONE);
                holder.expand_less.setVisibility(View.GONE);
                holder.expand_more.setVisibility(View.VISIBLE);
                holder.visit_url.setVisibility(View.GONE);
                holder.url_layout.setVisibility(View.GONE);
            }
        });

        holder.visit_url.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(articles[position].getUrl()); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                mContext.startActivity(intent);
            }
        });
    }

    private void setData(HeadlinesHolder holder, int position)  {
        Glide.with(mContext)
                .asBitmap()
                .load(articles[position].getUrlToImage())
                .into(holder.image);
        holder.title.setText(articles[position].getTitle());
        holder.description.setText(articles[position].getDescription());
        holder.post.setText(articles[position].getSource().getName());
        holder.content.setText(articles[position].getContent());
        holder.visit_url.setText(articles[position].getUrl());



    }





    @Override
    public int getItemCount() {
        return articles.length;

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    static class HeadlinesHolder extends RecyclerView.ViewHolder {
        SelectableRoundedImageView image;
        TextView title, description, post, content, visit_url;
        ImageView expand_more,expand_less;
        LinearLayout url_layout;

        private HeadlinesHolder(@NonNull View itemView) {
            super(itemView);
            setGlobals(itemView);
        }

        private void setGlobals(View itemView) {
            image = itemView.findViewById(R.id.image);
            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
            post = itemView.findViewById(R.id.post);
            content = itemView.findViewById(R.id.content);
            expand_less = itemView.findViewById(R.id.expand_less);
            expand_more = itemView.findViewById(R.id.expand_more);
            visit_url = itemView.findViewById(R.id.visit_url);
            url_layout = itemView.findViewById(R.id.url_layout);
        }

    }


}
