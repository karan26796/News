package com.example.karan.news.activities;

import android.os.Bundle;;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.karan.news.R;
import com.example.karan.news.utils.LaunchManager;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by karan on 8/9/2017.
 */

public class NewsDetails extends AppCompatActivity implements View.OnClickListener{

    private String category,detail;
    private TextView details;
    private ImageView imageView;
    private Toolbar toolbar;
    private ImageButton imageButton;
    private DatabaseReference databaseReference;

    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_details);

        position =getIntent().getIntExtra("position",0);
        category=getIntent().getStringExtra("category");

        details=(TextView) findViewById(R.id.details);
        imageView=(ImageView)findViewById(R.id.detail_image);
        toolbar=(Toolbar)findViewById(R.id.category_toolbar);
        imageButton=(ImageButton)findViewById(R.id.imageButton);

        imageButton.setOnClickListener(this);

        databaseReference= FirebaseDatabase.getInstance().getReference().child(category);
        /*detail=databaseReference.toString();*/
        loadData();
    }

    @Override
    public void onBackPressed() {
        LaunchManager.launchHome(this);
        super.onBackPressed();}

    public void loadData(){
        toolbar.setTitle(category);
        details.setText(category);
        imageView.setImageResource(R.drawable.detail_button);
    }

    @Override
    public void onClick(View v) {
        LaunchManager.launchHome(this);
    }
}
