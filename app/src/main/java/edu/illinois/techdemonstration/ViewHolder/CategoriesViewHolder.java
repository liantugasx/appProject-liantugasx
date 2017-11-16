package edu.illinois.techdemonstration.ViewHolder;

// This class process every item in the Recycler Adapter

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import edu.illinois.techdemonstration.Interface.ItemClickListener;
import edu.illinois.techdemonstration.R;

public class CategoriesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView categoryName;
    public ImageView categoryImage;

    private ItemClickListener itemClickListener;


    public CategoriesViewHolder(View itemView) {
        super(itemView);
        categoryName = (TextView)itemView.findViewById(R.id.name_category);
        categoryImage = (ImageView)itemView.findViewById(R.id.image_category);

        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
    @Override
    public void onClick(View view) {

    }
}
