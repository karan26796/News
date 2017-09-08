package com.example.karan.news.utils;
import android.view.View;

//Interface to add user defined functions to handle click activities on recycler view
public interface RecyclerViewClickListener {
    void onClick(View view, int position);
    void onLongClick(View view, int position);
}



