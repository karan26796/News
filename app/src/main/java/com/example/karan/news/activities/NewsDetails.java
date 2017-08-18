package com.example.karan.news.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by karan on 8/9/2017.
 * Common activity that displays details of the news article
 * clicked from the list on Home page
 */

public class NewsDetails extends BaseActivity implements View.OnClickListener{

    private String category, imageUrl1, news_details;
    private int color;
    private TextView details;
    private Window window;
    private PopupMenu popup;
    private ImageView imageView;
    private Toolbar toolbar;
    private ImageButton imageButton,bookmark;
    
    private ArrayList<Item> newsData;
    
    private int count=0;

    private DatabaseReference databaseReference;
    private Item newsItem = new Item();
    private int position;

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
        bookmark=(ImageButton)findViewById(R.id.bookmark);

        databaseReference= FirebaseDatabase.getInstance().getReference().child(category+position);

        getData();
        loadData();

        imageButton.setOnClickListener(this);
        bookmark.setOnClickListener(this);
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
        getBookmarkCount();
        Toast.makeText(NewsDetails.this, "\n" + imageUrl1 + "\n", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View v) {
        int id =v.getId();
        switch (id){
            case R.id.imageButton:
                LaunchManager.launchHome(this);
                break;
            case R.id.bookmark:
                showPopup(v,position);

        }
    }


    private void showPopup(View view, final int position) {

        popup = new PopupMenu(this, view);
        MenuInflater inflater = popup.getMenuInflater();

        if (category.equals(this.getString(R.string.bookmark_nav_drawer))) {

            inflater.inflate(R.menu.remove_bookmark, popup.getMenu());
            onClickRemoveFromList(position);
        } else {
            inflater.inflate(R.menu.bookmark, popup.getMenu());
            onClickBookmark(position);
        }
        popup.show();
    }

    private void onClickRemoveFromList(final int position) {

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                if (item.getItemId() == R.id.remove_bookmark_btn) {

                    // setup alert dialog
                    new AlertDialog.Builder(getApplicationContext())
                            .setMessage(R.string.remove_bookmark_alert_dialog_message)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    removeFromList(position);
                                }
                            })
                            .setNegativeButton("No", null)
                            .show();
                }

                return false;
            }
        });
    }

    public void getBookmarkCount() {

        FirebaseAuthentication firebaseAuthentication = new FirebaseAuthentication(this);

        String currentUser = firebaseAuthentication.getCurrentUser();

        databaseReference.child(this.getString(R.string.users)).child(currentUser)
                .child(this.getString(R.string.bookmark_nav_drawer)).addValueEventListener(new ValueEventListener() {
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
                    DatabaseReference bookmarkReference = databaseReference.child(getApplicationContext().getString(R.string.users))
                            .child(currentUser).child(getApplicationContext().getString(R.string.bookmark_nav_drawer))
                            .child(getApplicationContext().getString(R.string.bookmark_nav_drawer) + "_0" + String.valueOf(bookmarkCount + 1));


                    HashMap<String, String> userMap = new HashMap<>();
                    userMap.put("date", newsData.get(position).getDate());
                    userMap.put("image", newsData.get(position).getImage());
                    userMap.put("description",newsData.get(position).getDescription());
                    userMap.put("detail",newsData.get(position).getDetail());
                    // firebase database onComplete listener
                    bookmarkReference.setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()) {

                                Toast.makeText(getApplicationContext(), R.string.bookmark_added, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

                return false;
            }
        });
    }

    private void removeFromList(int position) {

        final int newsPosition = position + 1;

        // get firebase current user
        String currentUser = new FirebaseAuthentication(getApplicationContext()).getCurrentUser();

        // remove shared preferences 'Read' status
        SharedPreferences.Editor editor = getApplicationContext()
                .getSharedPreferences(Constants.READ_ARTICLES_STATUS_SHARED_PREFERENCES, MODE_PRIVATE).edit();
        editor.remove(currentUser + newsData.get(position).getTitle());
        editor.apply();

        // firebase database reference
        DatabaseReference removeDataReference = databaseReference.child(getApplicationContext().getString(R.string.users))
                .child(currentUser).child(getApplicationContext().getString(R.string.bookmark_nav_drawer))
                .child(getApplicationContext().getString(R.string.bookmark_nav_drawer) + "_0" + String.valueOf(newsPosition));
        // remove data item
        removeDataReference.removeValue();

        //-------------------------------------------------------------------------------------
        // update index of news items following deleted news item
        //-------------------------------------------------------------------------------------
        // decrease index of following news items by 1.
        for (int i = newsPosition + 1 ; i <= newsData.size() ; i++) {

            DatabaseReference bookmarkReference = databaseReference.child(getApplicationContext().getString(R.string.users))
                    .child(currentUser).child(getApplicationContext().getString(R.string.bookmark_nav_drawer))
                    .child(getApplicationContext().getString(R.string.bookmark_nav_drawer) + "_0" + String.valueOf(i - 1));

            Item newsItem = newsData.get(i - 1);

            bookmarkReference.setValue(newsItem);
        }

        // remove last item in firebase database as it has been copied to its previous location
        DatabaseReference removeDatabaseFinalValueReference = databaseReference.child(getApplicationContext().getString(R.string.users))
                .child(currentUser).child(getApplicationContext().getString(R.string.bookmark_nav_drawer))
                .child(getApplicationContext().getString(R.string.bookmark_nav_drawer) + "_0" + String.valueOf(newsData.size()));
        removeDatabaseFinalValueReference.removeValue();
    }

    @Override
    protected int getLayoutResourceId() {
        return 0;
    }

    @Override
    protected int getToolbarID() {
        return 0;
    }

    @Override
    protected String getToolbarTitle() {
        return null;
    }
}
