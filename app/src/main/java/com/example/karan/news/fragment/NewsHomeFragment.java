package com.example.karan.news.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.karan.news.R;
import com.example.karan.news.activities.NewsDetailsActivity;
import com.example.karan.news.firebasemanager.FirebaseAuthentication;
import com.example.karan.news.models.Item;
import com.example.karan.news.recyclerview.NewsAdapter;
import com.example.karan.news.recyclerview.NewsList;
import com.example.karan.news.utils.Constants;
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
 * A common fragment_news_activity is defined on home page which
 * is inflated on different scenarios in the app.
 * <p>
 * This class takes care of inflating the right
 * fragment_news_activity on user prompt or in this case, category
 * selection from navigation drawer
 */

public class NewsHomeFragment extends Fragment implements NewsAdapter.onItemClickListener {

    private RecyclerView recyclerView;
    private String child, userKey;
    private int color;
    Context context;
    private ArrayList<Item> newsItem = new ArrayList<>();
    private DatabaseReference mDatabase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_activity, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.news_activity_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        child = this.getArguments().getString(Constants.CATEGORY_NAME);
        color = this.getArguments().getInt(Constants.TOOLBAR_COLOR);

        FirebaseAuthentication firebaseAuthentication = new FirebaseAuthentication(context);
        userKey = firebaseAuthentication.getCurrentUser();

        checkCategory();
        NewsAdapter newsAdapter = new NewsAdapter(NewsList.class, R.layout.news_card, NewsAdapter.NewsViewHolder.class, mDatabase, this);
        readData(newsAdapter);
        recyclerView.setAdapter(newsAdapter);

        return view;
    }

    public void readData(final FirebaseRecyclerAdapter adapter) {

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                newsItem.clear();

                for (DataSnapshot userDataSnapshot : dataSnapshot.getChildren()) {
                    if (userDataSnapshot != null) {
                        newsItem.add(userDataSnapshot.getValue(Item.class));
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

    private void checkCategory() {
        if (child.equals(Constants.BOOKMARK_CATEGORY)) {
            mDatabase = FirebaseDatabase.getInstance().getReference().child(Constants.USERS_KEY).child(userKey).child(Constants.BOOKMARK_CATEGORY);
        } else {
            mDatabase = FirebaseDatabase.getInstance().getReference().child(child);
        }
    }

    @Override
    public void onClick(NewsAdapter.NewsViewHolder view, int position) {
        SharedPreferences.Editor editor = getActivity()
                .getSharedPreferences(Constants.READ_ARTICLES_STATUS_SHARED_PREFERENCES, MODE_PRIVATE).edit();
        editor.putBoolean(userKey + newsItem.get(position).getTitle(), true);
        editor.apply();

        Intent intent = new Intent(getContext(), NewsDetailsActivity.class);
        intent.putExtra(Constants.POSITION, position)
                .putExtra(Constants.CATEGORY_NAME, child)
                .putExtra(Constants.COLOR_VALUE, color)
                .putExtra(Constants.NEWS_DETAILS_TITLE, newsItem.get(position).getTitle())
                .putExtra(Constants.NEWS_DETAILS_DESC, newsItem.get(position).getDetail())
                .putExtra(Constants.NEWS_DETAILS_IMAGE, newsItem.get(position).getImage());

        Pair<View, String> pair1 = new Pair<>(view.itemView.findViewById(R.id.card_view_image), "News");
        Pair<View, String> pair2 = new Pair<>(view.itemView.findViewById(R.id.card_view_headline), "News1");
        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity()
                , pair1, pair2);

        startActivity(intent, optionsCompat.toBundle());
    }

    @Override
    public void onLongClick() {

    }
}

