package com.example.karan.news.activities;

import android.content.res.Resources;
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
import android.widget.Toast;

import com.example.karan.news.R;
import com.example.karan.news.models.Item;
import com.example.karan.news.utils.Constants;
import com.example.karan.news.utils.LaunchManager;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by karan on 8/9/2017.
 * Common activity that displays details of the news article
 * clicked from the list on Home page
 */

public class NewsDetails extends AppCompatActivity implements View.OnClickListener{

    private String category, imageUrl1, news_details;
    private int color;
    private TextView details;
    private Window window;
    private ImageView imageView;
    private Toolbar toolbar;
    private ImageButton imageButton;

    DatabaseReference databaseReference;
    Item newsItem = new Item();
    int position;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_details);

        position =getIntent().getIntExtra(Constants.POSITION,0);
        category=getIntent().getStringExtra(Constants.CATEGORY_NAME);
        color=getIntent().getIntExtra(Constants.COLOR_VALUE,R.color.colorAccent);

        window=getWindow();

        details=(TextView) findViewById(R.id.details);

        imageView=(ImageView)findViewById(R.id.detail_image);

        toolbar=(Toolbar)findViewById(R.id.category_toolbar);
        imageButton=(ImageButton)findViewById(R.id.imageButton);

        imageButton.setOnClickListener(this);
        databaseReference= FirebaseDatabase.getInstance().getReference().child(category+position);

        getData();
        loadData();
    }

    @Override
    public void onBackPressed() {
        LaunchManager.categoryFragment(this,category);
        super.onBackPressed();}

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void loadData(){
        toolbar.setTitle(category);
        toolbar.setBackgroundColor(color);
        window.setStatusBarColor(color);
        details.setText(news_details);

        Picasso.with(getApplicationContext())
                .load(imageUrl1)
                .fit()
                .into(imageView);
    }

    protected void getData() {

        newsItem = (Item) getIntent().getSerializableExtra(Constants.NEWS_DETAILS);

        this.imageUrl1=newsItem.getImage();
        this.news_details=newsItem.getDetail();

        Toast.makeText(NewsDetails.this, "\n" + imageUrl1 + "\n", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View v) {
        LaunchManager.launchHome(this);
    }
}
