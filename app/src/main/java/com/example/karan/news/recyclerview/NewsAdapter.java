package com.example.karan.news.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.karan.news.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

/**
 * Created by karan on 4/16/2018.
 */

public class NewsAdapter extends FirebaseRecyclerAdapter<NewsList, NewsAdapter.NewsViewHolder> {

    private onItemClickListener mClicklistener;

    public interface onItemClickListener {
        void onClick(NewsViewHolder view, int position);

        void onLongClick();
    }

    public NewsAdapter(Class<NewsList> modelClass, int modelLayout, Class<NewsViewHolder> viewHolderClass, Query ref, onItemClickListener mClicklistener) {
        super(modelClass, modelLayout, viewHolderClass, ref);
        this.mClicklistener = mClicklistener;
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_card, parent, false);
        return new NewsAdapter.NewsViewHolder(itemView);
    }

    @Override
    protected void populateViewHolder(NewsViewHolder viewHolder, NewsList model, int position) {
        viewHolder.mTextViewHeadline.setText(model.getTitle());
        viewHolder.mTextViewDate.setText(model.getDate());
        viewHolder.mTextViewFiller.setText(model.getDescription());
        Picasso.with(viewHolder.itemView.getContext())
                .load(model.getImage())
                .into(viewHolder.imageView);
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mTextViewHeadline, mTextViewDate, mTextViewFiller, mTextViewStatus;
        ImageView imageView;

        public NewsViewHolder(View itemView) {
            super(itemView);

            mTextViewStatus = (TextView) itemView.findViewById(R.id.textViewStatus);
            mTextViewHeadline = (TextView) itemView.findViewById(R.id.card_view_headline);
            mTextViewDate = (TextView) itemView.findViewById(R.id.card_view_date);
            mTextViewFiller = (TextView) itemView.findViewById(R.id.card_view_filler);
            imageView = (ImageView) itemView.findViewById(R.id.card_view_image);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mClicklistener.onClick(this, getAdapterPosition());
        }
    }
}