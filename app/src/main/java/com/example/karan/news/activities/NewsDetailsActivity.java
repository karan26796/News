package com.example.karan.news.activities;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.karan.news.R;
import com.example.karan.news.firebase_essentials.FirebaseAuthentication;
import com.example.karan.news.models.Item;
import com.example.karan.news.utils.Constants;
import com.example.karan.news.utils.LaunchManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

/**
 * Created by karan on 8/9/2017.
 * Common activity that displays details of the news article
 * clicked from the list on Home page
 */

public class NewsDetailsActivity extends BaseActivity implements View.OnClickListener{

    private String newsCategory, imageUrl1, newsDetails,userName;
    private int color;
    private TextView mTextViewDetails;
    private Window mWindow;
    private ImageView imageView;
    private Toolbar mToolbar;
    private ImageButton backButton,bookmarkButton;
    private DatabaseReference databaseReference;
    private Item newsItem = new Item();
    private int position;
    private PopupMenu popup;
    private boolean status;
    private int count;
    SharedPreferences sharedPreferences;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);

        configureToolbar(this);

        valuesFetch();
        //Used to set color of status bar
        mWindow=getWindow();
        FirebaseAuthentication firebaseAuthentication =new FirebaseAuthentication(this);
        userName=firebaseAuthentication.getCurrentUser();

        mTextViewDetails=(TextView) findViewById(R.id.news_details);
        imageView=(ImageView)findViewById(R.id.detail_image);
        mToolbar=(Toolbar)findViewById(R.id.category_toolbar);
        bookmarkButton=(ImageButton)findViewById(R.id.img_btn_bookmark);

        databaseReference= FirebaseDatabase.getInstance().getReference();
        sharedPreferences=getSharedPreferences(Constants.READ_ARTICLES_STATUS_SHARED_PREFERENCES,MODE_PRIVATE);
        status =sharedPreferences.getBoolean(userName+newsItem.getTitle(),true);

        getData();
        loadData();
        getBookmarkCount();

        bookmarkButton.setOnClickListener(this);
    }

    private void valuesFetch(){
        //Values fetched from newsFragment Bundle
        position =getIntent().getIntExtra(Constants.POSITION,0);
        newsCategory=getIntent().getStringExtra(Constants.CATEGORY_NAME);
        color=getIntent().getIntExtra(Constants.COLOR_VALUE,R.color.colorAccent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void loadData(){

        mToolbar.setTitle(newsCategory);
        mToolbar.setBackgroundColor(color);
        mWindow.setStatusBarColor(color);
        mTextViewDetails.setText(newsDetails);
    }

    protected void getData() {

        newsItem = (Item) getIntent().getSerializableExtra(Constants.NEWS_DETAILS);
        this.imageUrl1=newsItem.getImage();
        this.newsDetails=newsItem.getDetail();
        loadImage(imageView);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case android.R.id.home:
                onBackPressed();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.img_btn_bookmark:
                showPopup(v, position);
                break;
        }
    }

    private void loadImage(final ImageView imageView) {
        this.imageView=imageView;

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        theme = sharedPreferences.getString(Constants.KEY_APP_THEME, getString(R.string.theme_light));
        boolean image_download = sharedPreferences.getBoolean(Constants.KEY_DOWNLOAD_IMAGES, true);

        if(image_download){
            Picasso.with(getApplicationContext())
                    .load(imageUrl1)
                    .fit()
                    .into(imageView);
        }
        else
        {
            if(theme.equals(getResources().getString(R.string.theme_light))) {
                imageView.setImageResource(R.drawable.ic_image_broken_variant_black);
            }
            else{
                imageView.setImageResource(R.drawable.ic_image_broken_variant_white);
            }

        }
    }

    private void showPopup(View view, final int position) {

        popup = new PopupMenu(this, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.bookmark, popup.getMenu());
        onClickBookmark(position);
        popup.show();
    }

    public void getBookmarkCount() {

        FirebaseAuthentication firebaseAuthentication = new FirebaseAuthentication(this);

        String currentUser = firebaseAuthentication.getCurrentUser();

        databaseReference.child(this.getString(R.string.users)).child(currentUser)
                .child(Constants.BOOKMARK_CATEGORY).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // gives total number of children in the database reference
                count = (int) dataSnapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void onClickBookmark(final int position) {

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                int bookmarkCount = count;

                if (item.getItemId() == R.id.bookmark_btn) {

                    // get firebase current user
                    FirebaseAuthentication firebaseAuthentication = new FirebaseAuthentication(getApplicationContext());
                    String currentUser = firebaseAuthentication.getCurrentUser();

                    // firebase database reference
                    DatabaseReference bookmarkReference = FirebaseDatabase.getInstance().getReference().child(Constants.USERS_KEY).child(currentUser)
                            .child(Constants.BOOKMARK_CATEGORY).child(Constants.BOOKMARK_CATEGORY + String.valueOf(bookmarkCount + 1));

                    Item item1 = new Item(newsItem.getDetail(),newsItem.getImage(),newsItem.getTitle(),newsItem.getDate(),newsItem.getDescription());

                    bookmarkReference.setValue(item1).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()) {

                                Toast.makeText(getApplicationContext(), R.string.toast_bookmark_added, Toast.LENGTH_SHORT).show();
                            } else {

                                Toast.makeText(NewsDetailsActivity.this, newsItem.getDetail(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

                return false;
            }
        });
    }



    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_news_details;
    }

    @Override
    protected int getToolbarID() {
        return R.id.category_toolbar;
    }

    @Override
    protected String getToolbarTitle() {
        return "";
    }
}
