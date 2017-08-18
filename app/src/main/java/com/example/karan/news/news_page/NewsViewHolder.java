package com.example.karan.news.news_page;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.karan.news.R;
import com.squareup.picasso.Picasso;

import java.util.Locale;

/**
 * Created by karan on 7/3/2017.
 */
/*News article views are defined here*/
public class NewsViewHolder extends RecyclerView.ViewHolder {
    private TextView head,date,filler;
    private ImageView imageView;

    public NewsViewHolder(View itemView) {
        super(itemView);

        head= (TextView) itemView.findViewById(R.id.headline);
        date=(TextView) itemView.findViewById(R.id.date);
        filler=(TextView) itemView.findViewById(R.id.filler);
        imageView=(ImageView) itemView.findViewById(R.id.image);
    }

    public void setHeadLine(String title) {
    head.setText(title);
    }

    public void setDescription(String description) {filler.setText(description);}

    public void setDate(String updated){date.setText("Date: "+updated);}

    public void setImage(String image) {
        Picasso.with(itemView.getContext())
                .load(image)
                .fit()
                .into(imageView);
    }

}

