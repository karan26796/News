package com.example.karan.news.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.karan.news.R;
import com.example.karan.news.firebase_essentials.FirebaseAuthentication;
import com.example.karan.news.models.Item;
import com.example.karan.news.news_page.NewsList;
import com.example.karan.news.news_page.NewsViewHolder;
import com.example.karan.news.utils.Constants;
import com.example.karan.news.utils.LaunchManager;
import com.example.karan.news.utils.RecyclerViewClickListener;
import com.example.karan.news.utils.RecyclerViewTouchListener;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by karan on 7/19/2017.
 * A common fragment is defined on home page which
 * is inflated on different scenarios in rhe app.
 */

public class NewsHomeFragment extends Fragment implements RecyclerViewClickListener{

    private RecyclerView recyclerView;

    private String child,user_key;
    private int color;
    Context context;
    private ArrayList<Item> newsItem = new ArrayList<>();
    private DatabaseReference mDatabase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment, container, false);

        //Recycler view contains the list of news articles foe different categories
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        child = this.getArguments().getString(Constants.CATEGORY_NAME);
        color = this.getArguments().getInt(Constants.TOOLBAR_COLOR);

        FirebaseAuthentication firebaseAuthentication=new FirebaseAuthentication(context);
        //User key stores the String value of user's key to distinguish b/w users and show bookmarks accordingly
        user_key=firebaseAuthentication.getCurrentUser();

        checkCategory();
        //Firebase Recycler adapter method fetches details from firebase database
        FirebaseRecyclerAdapter<NewsList,NewsViewHolder> adapter= new FirebaseRecyclerAdapter<NewsList, NewsViewHolder>(NewsList.class
                ,R.layout.news_card,NewsViewHolder.class,mDatabase) {
            @Override
            protected void populateViewHolder(NewsViewHolder viewHolder, NewsList model, int position) {

                /*Text and image details of news articles are fetched from firebase and
                ser to corresponding views*/
                viewHolder.setHeadLine(model.getTitle());
                viewHolder.setDescription(model.getDescription());
                viewHolder.setDate(model.getDate());
                viewHolder.setImage(model.getImage());
            }
        };
        adapter.getItemCount();
        readData(adapter);

        recyclerView.addOnItemTouchListener(new RecyclerViewTouchListener(context, recyclerView, this));
        recyclerView.setAdapter(adapter);
        return view;
    }

    public void readData(final FirebaseRecyclerAdapter adapter) {

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // clear newsItem and newsListData to remove any redundant data
                newsItem.clear();

                for ( DataSnapshot userDataSnapshot : dataSnapshot.getChildren() ) {
                    if (userDataSnapshot != null) {
                        // add values fetched from firebase database to 'Item' newsItem
                        newsItem.add(userDataSnapshot.getValue(Item.class));

                        // update recycler view adapter
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                adapter.notifyDataSetChanged();
            }
        });
    }

    //Method defined to check the incoming category from news home page to inflate the respective fragment.
    private void checkCategory(){
        if (child.equals(Constants.BOOKMARK_CATEGORY)){
            mDatabase =FirebaseDatabase.getInstance().getReference().child(Constants.USERS_KEY).child(user_key).child(Constants.BOOKMARK_CATEGORY);
        }
        else{
            mDatabase = FirebaseDatabase.getInstance().getReference().child(child);
        }
    }

    @Override
    public void onClick(View view, int position) {
        FirebaseAuthentication firebaseAuthentication=new FirebaseAuthentication(getContext());
        String currentUser=firebaseAuthentication.getCurrentUser();
        SharedPreferences.Editor editor = getActivity()
                .getSharedPreferences(Constants.READ_ARTICLES_STATUS_SHARED_PREFERENCES, MODE_PRIVATE).edit();
        editor.putBoolean("read_status", true);
        editor.apply();

        LaunchManager.showDetailsPage(getActivity(),position,child,color, newsItem.get(position));
    }

    @Override
    public void onLongClick(View view, int position) {

        }

}

