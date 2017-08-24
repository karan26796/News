package com.example.karan.news.recycler_view;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.karan.news.R;
import com.example.karan.news.utils.Constants;
import com.squareup.picasso.Picasso;

/**
 * Created by karan on 7/3/2017.
 */
/*News article views are defined here*/
public class NewsViewHolder extends RecyclerView.ViewHolder {
    private TextView head;
    private TextView date;
    private TextView filler;
    private String theme;
    private Context context;
    private ImageView imageView;
    private SharedPreferences preferences,sharedPreferences;

    private CardView cardView;
    private boolean status=false;


    public NewsViewHolder(View itemView) {
        super(itemView);

        cardView=(CardView) itemView.findViewById(R.id.card_view);
        head= (TextView) itemView.findViewById(R.id.headline);
        date=(TextView) itemView.findViewById(R.id.date);
        filler=(TextView) itemView.findViewById(R.id.filler);
        imageView=(ImageView) itemView.findViewById(R.id.image);
        preferences=context.getSharedPreferences(Constants.READ_ARTICLES_STATUS_SHARED_PREFERENCES,Context.MODE_PRIVATE);
        sharedPreferences=context.getSharedPreferences("sharedPreferences",Context.MODE_PRIVATE);
        setCardView();
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

    public void setCardView() {

        theme=preferences.getString(Constants.KEY_APP_THEME, String.valueOf(R.string.theme_light));
        status=sharedPreferences.getBoolean("read_status",false);
        if(theme.equals(String.valueOf(R.string.theme_light))){
            if(status)
            {
                cardView.setBackgroundColor(ContextCompat.getColor(context,R.color.off_white));
            }
            else{
                cardView.setBackgroundColor(ContextCompat.getColor(context,R.color.white));
            }
        }
        else if (theme.equals(String.valueOf(R.string.theme_dark))){
            if(status)
            {
                cardView.setBackgroundColor(ContextCompat.getColor(context,R.color.lighter_gray));
            }
            else{
                cardView.setBackgroundColor(ContextCompat.getColor(context,R.color.gray_dark));
            }
        }
    }
}

