package com.news.newsapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    }

    private void setData(HeadlinesHolder holder, int position)  {
        Glide.with(mContext)
                .asBitmap()
                .load(articles[position].getUrlToImage())
                .into(holder.image);
        holder.title.setText(articles[position].getTitle());
        holder.description.setText(articles[position].getDescription());
        holder.post.setText(articles[position].getSource().getName());


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
        TextView title, description, post;

        private HeadlinesHolder(@NonNull View itemView) {
            super(itemView);
            setGlobals(itemView);
        }

        private void setGlobals(View itemView) {
            image = itemView.findViewById(R.id.image);
            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
            post = itemView.findViewById(R.id.post);

        }

    }


}
