package com.example.karan.news.news_page;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.karan.news.R;
import com.squareup.picasso.Picasso;

/**
 * Created by karan on 7/3/2017.
 */
/*Recycler view items are defined here to be held in the view*/
public class NewsViewHolder extends RecyclerView.ViewHolder {
    TextView head,filler;
    ImageView imageView;

    public NewsViewHolder(View itemView) {
        super(itemView);
        head= (TextView) itemView.findViewById(R.id.headline);
        filler= (TextView) itemView.findViewById(R.id.filler);
        imageView=(ImageView) itemView.findViewById(R.id.image);
    }

    public void setHeadLine(String title) {
    head.setText(title);
    }

    public void setDescription(String description) {
        filler.setText(description);
    }

    public void setImage(String image) {
        Picasso.with(itemView.getContext())
                .load(image)
                .resize(300,200)
                .centerInside()
                .into(imageView);
    }


}

