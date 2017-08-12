package com.example.karan.news.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.karan.news.R;
import com.example.karan.news.news_page.NewsList;
import com.example.karan.news.news_page.NewsViewHolder;
import com.example.karan.news.utils.Constants;
import com.example.karan.news.utils.LaunchManager;
import com.example.karan.news.utils.RecyclerViewClickListener;
import com.example.karan.news.utils.RecyclerViewTouchListener;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by karan on 7/19/2017.
 * A common fragment is defined on home page which
 * is inflated on different scenarios in rhe app.
 */

public class NewsHomeFragment extends Fragment implements RecyclerViewClickListener{

    private RecyclerView recyclerView;
    private DatabaseReference databaseReference;
    private String child;
    private int color;
    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.fragment,container,false);

        //Recycler view contains the list of news articles foe different categories
        recyclerView=(RecyclerView) view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        child = this.getArguments().getString(Constants.CATEGORY_NAME);
        color=this.getArguments().getInt(Constants.TOOLBAR_COLOR);

        databaseReference= FirebaseDatabase.getInstance().getReference().child(child);

        //Firebase Recycler adaptermethod fetches details from firebase database
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

        recyclerView.addOnItemTouchListener(new RecyclerViewTouchListener(context, recyclerView, this));
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onClick(View view, int position) {
        LaunchManager.showDetailsPage(getActivity(),position,child,color);
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
    }

