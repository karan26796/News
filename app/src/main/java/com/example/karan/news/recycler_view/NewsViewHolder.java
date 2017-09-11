package com.example.karan.news.recycler_view;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.karan.news.R;
import com.example.karan.news.firebase_essentials.FirebaseAuthentication;
import com.example.karan.news.utils.Constants;
import com.squareup.picasso.Picasso;
/**
 * Created by karan on 7/3/2017.
 *
 * Holder class defined to hold the views used in recycler view
 */
/*News article views are defined here*/
@SuppressWarnings("FieldCanBeLocal")
public class NewsViewHolder extends RecyclerView.ViewHolder {
    private TextView mTextViewHeadline,mTextViewDate,mTextViewFiller;
    private String mTheme,userKey;
    private SharedPreferences sharedPreferences,mPreferences;
    private ImageView imageView;
    private RelativeLayout relativeLayout;
    private boolean mStatus=false;


    public NewsViewHolder(View itemView) {
        super(itemView);

        mTextViewHeadline= (TextView) itemView.findViewById(R.id.card_view_headline);
        mTextViewDate=(TextView) itemView.findViewById(R.id.card_view_date);
        mTextViewFiller=(TextView) itemView.findViewById(R.id.card_view_filler);
        imageView=(ImageView) itemView.findViewById(R.id.card_view_image);
        relativeLayout=(RelativeLayout) itemView.findViewById(R.id.card_relative_layout);
    }

    public void setHeadLine(String title) {
    mTextViewHeadline.setText(title);
    }

    public void setDescription(String description) {mTextViewFiller.setText(description);}

    public void setDate(String updated){
        mTextViewDate.setText(updated);}

    public void setImage(String image) {
        Picasso.with(itemView.getContext())
                .load(image)
                .fit()
                .into(imageView);
    }

    //This method checks whether a news article has been read and changes the bg color of the article accordingly
    public void checkStatus(String title, Context context) {

        FirebaseAuthentication firebaseAuthentication=new FirebaseAuthentication(context);
        userKey=firebaseAuthentication.getCurrentUser();

        sharedPreferences = context.getSharedPreferences(Constants.READ_ARTICLES_STATUS_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        mStatus = sharedPreferences.getBoolean(userKey+title, false);
        mPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        mTheme=mPreferences.getString(Constants.KEY_APP_THEME, context.getString(R.string.theme_light));
        if (mStatus) {
                if(mTheme.equals(context.getString(R.string.theme_light))){
                     relativeLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.lighter_gray));
                }
                else if(mTheme.equals(context.getString(R.string.theme_dark)))
                    relativeLayout.setBackgroundColor(ContextCompat.getColor(context,R.color.black_1));
        }
    }
}

