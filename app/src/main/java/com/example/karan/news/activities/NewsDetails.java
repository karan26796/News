package com.example.karan.news.activities;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.karan.news.R;
import com.example.karan.news.utils.Constants;
import com.example.karan.news.utils.LaunchManager;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by karan on 8/9/2017.
 */

public class NewsDetails extends AppCompatActivity implements View.OnClickListener{

    private String category;
    private int color;
    private TextView details;
    private ImageView imageView;
    private Toolbar toolbar;
    private ImageButton imageButton;
    private DatabaseReference databaseReference;

    int position;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_details);

        position =getIntent().getIntExtra(Constants.POSITION,0);
        category=getIntent().getStringExtra(Constants.CATEGORY_NAME);
        color=getIntent().getIntExtra(Constants.COLOR_VALUE,R.color.colorAccent);

        Window window=getWindow();
        window.setStatusBarColor(color);
        details=(TextView) findViewById(R.id.details);
        imageView=(ImageView)findViewById(R.id.detail_image);
        toolbar=(Toolbar)findViewById(R.id.category_toolbar);
        imageButton=(ImageButton)findViewById(R.id.imageButton);

        imageButton.setOnClickListener(this);
        //getData();
        databaseReference= FirebaseDatabase.getInstance().getReference().child(category+position);
        /*detail=databaseReference.toString();*/
        loadData();
    }

    @Override
    public void onBackPressed() {
        LaunchManager.categoryFragment(this,category);
        super.onBackPressed();}

    public void loadData(){
        toolbar.setTitle(category);
        details.setText(category);
        for(int i=0;i<=6;i++)
        {
            if(i==position){
                toolbar.setBackgroundColor(color);
            }
        }

        imageView.setImageResource(R.drawable.detail_button);
    }
    /*protected void getData() {

        newsItem = (Item) getIntent().getSerializableExtra(Constants.INTENT_KEY_NEWS_DATA);

        headline = newsItem.getHeadline();
        imageUrl1 = newsItem.getImageUrl1();
        date=newsItem.getDate();
    }*/

    @Override
    public void onClick(View v) {
        LaunchManager.launchHome(this);
    }
}
