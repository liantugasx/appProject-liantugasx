package edu.illinois.techdemonstration;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import edu.illinois.techdemonstration.Interface.ItemClickListener;
import edu.illinois.techdemonstration.Parse.Categories;
import edu.illinois.techdemonstration.ViewHolder.CategoriesViewHolder;


public class CategoriesFragment extends Fragment {

    View categoryFragment;

    RecyclerView categoryList;
    RecyclerView.LayoutManager layoutManager;

    private FirebaseRecyclerAdapter<Categories, CategoriesViewHolder> adapter;
    private FirebaseDatabase database;
    DatabaseReference categories;

    public static CategoriesFragment newInstance() {
        CategoriesFragment categoriesFragment = new CategoriesFragment();
        return categoriesFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        database = FirebaseDatabase.getInstance();
        categories = database.getReference("Category");
    }

    @Override
    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        categoryFragment = inflater.inflate(R.layout.fragment_categories, container, false);

        categoryList = (RecyclerView)categoryFragment.findViewById(R.id.category_list);
        categoryList.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(container.getContext());
        categoryList.setLayoutManager(layoutManager);

        loadAllCategories();

        return categoryFragment;
    }


    private void loadAllCategories() {
        adapter = new FirebaseRecyclerAdapter<Categories, CategoriesViewHolder>(
                Categories.class, R.layout.category_list_layout,
                CategoriesViewHolder.class, categories)
        {

            protected void populateViewHolder(CategoriesViewHolder viewHolder, final Categories allCategories, int position) {
                viewHolder.categoryName.setText(allCategories.getName());
                Picasso.with(getActivity()).load(allCategories.getImage()).into(viewHolder.categoryImage);

                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongclick) {
                        Toast.makeText(getActivity(), String.format("%s|%s", adapter.getRef(position).getKey(),
                                allCategories.getImage()), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };

        categoryList.setHasFixedSize(true);
        adapter.notifyDataSetChanged();
        categoryList.setAdapter(adapter);
    }

}
