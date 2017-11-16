package edu.illinois.techdemonstration.Interface;


import android.view.View;

// This help implements onClick at Recycler Item

public interface ItemClickListener {
    void onClick(View view, int position, boolean isLongClick);
}
