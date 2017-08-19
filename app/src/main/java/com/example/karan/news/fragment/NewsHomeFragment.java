package com.example.karan.news.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.karan.news.R;
import com.example.karan.news.activities.NewsDetails;
import com.example.karan.news.firebase_essentials.FirebaseAuthentication;
import com.example.karan.news.models.Item;
import com.example.karan.news.news_page.NewsList;
import com.example.karan.news.news_page.NewsViewHolder;
import com.example.karan.news.utils.Constants;
import com.example.karan.news.utils.LaunchManager;
import com.example.karan.news.utils.RecyclerViewClickListener;
import com.example.karan.news.utils.RecyclerViewTouchListener;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by karan on 7/19/2017.
 * A common fragment is defined on home page which
 * is inflated on different scenarios in rhe app.
 */

public class NewsHomeFragment extends Fragment implements RecyclerViewClickListener{

    private RecyclerView recyclerView;

    private DatabaseReference databaseReference;
    private String child,theme;
    private int color;
    Context context;

    ArrayList<Item> newsItem = new ArrayList<>();

    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.fragment,container,false);

        loadPreferences();

        //Recycler view contains the list of news articles foe different categories
        recyclerView=(RecyclerView) view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        child = this.getArguments().getString(Constants.CATEGORY_NAME);
        color=this.getArguments().getInt(Constants.TOOLBAR_COLOR);

        databaseReference= FirebaseDatabase.getInstance().getReference().child(child);

        //Firebase Recycler adapter method fetches details from firebase database
        FirebaseRecyclerAdapter<NewsList,NewsViewHolder> adapter= new FirebaseRecyclerAdapter<NewsList, NewsViewHolder>(NewsList.class
                ,R.layout.news_card,NewsViewHolder.class,databaseReference) {
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

        mDatabase.child(child).addValueEventListener(new ValueEventListener() {
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

    @Override
    public void onClick(View view, int position) {
        LaunchManager.showDetailsPage(getActivity(),position,child,color, newsItem.get(position));
    }

    @Override
    public void onLongClick(View view, int position) {
            AlertDialog alertbox = new AlertDialog.Builder(getActivity())
                    .setMessage(R.string.bookmark)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                            return;
                        }
                    })
                    .show();
        }

    protected void loadPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        theme = sharedPreferences.getString(Constants.KEY_APP_THEME, getString(R.string.theme_light));
        boolean color  = sharedPreferences.getBoolean("image_download", true);
        Toast.makeText(getActivity(), String.valueOf(color)+ theme, Toast.LENGTH_LONG).show();
    }
}

