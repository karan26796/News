package com.example.karan.news.recycler_view;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.karan.news.R;
import com.example.karan.news.models.Item;
import com.example.karan.news.utils.Constants;
import com.squareup.picasso.Picasso;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by karan on 7/3/2017.
 *
 * Holder class defined to hold the views used in recycler view
 */
/*News article views are defined here*/
public class NewsViewHolder extends RecyclerView.ViewHolder {
    private TextView head;
    private TextView date;
    private TextView filler;
    private String theme;
    private SharedPreferences sharedPreferences,preferences;
    private ImageView imageView;
    private RelativeLayout relativeLayout;
    private boolean status=false;


    public NewsViewHolder(View itemView) {
        super(itemView);

        head= (TextView) itemView.findViewById(R.id.headline);
        date=(TextView) itemView.findViewById(R.id.date);
        filler=(TextView) itemView.findViewById(R.id.filler);
        imageView=(ImageView) itemView.findViewById(R.id.image);
        relativeLayout=(RelativeLayout) itemView.findViewById(R.id.card_relative_layout);

    }

    public void setHeadLine(String title) {
    head.setText(title);
    }

    public void setDescription(String description) {filler.setText(description);}

    public void setDate(String updated){
        date.setText(updated);}

    public void setImage(String image) {
        Picasso.with(itemView.getContext())
                .load(image)
                .fit()
                .into(imageView);
    }

    public void checkStatus(String title, Context context) {

        sharedPreferences = context.getSharedPreferences(Constants.READ_ARTICLES_STATUS_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        status = sharedPreferences.getBoolean(title, false);
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        theme=preferences.getString(Constants.KEY_APP_THEME, context.getString(R.string.theme_light));
        if (status) {
                if(theme.equals(context.getString(R.string.theme_light))){
                     relativeLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.lighter_gray));
                }
                else if(theme.equals(context.getString(R.string.theme_dark)))
                    relativeLayout.setBackgroundColor(ContextCompat.getColor(context,R.color.black_1));
        }
    }
}

