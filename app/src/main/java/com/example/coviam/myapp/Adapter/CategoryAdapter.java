package com.example.coviam.myapp.Adapter;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.coviam.myapp.network.LoginController;
import com.example.coviam.myapp.Model.CategoryModel;
import com.example.coviam.myapp.Activity.ProductListActivity;
import com.example.coviam.myapp.network.ProjectAPI;
import com.example.coviam.myapp.R;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ProductViewHolder> {

    private Context mcontex;
    private ProjectAPI projectApi;
    private List<CategoryModel> mCategoryModel;


    public CategoryAdapter(Context mcontex, List<CategoryModel> productmodel) {
        this.mcontex = mcontex;
        mCategoryModel = productmodel;
    }


    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_category, null);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ProductViewHolder holder, int position) {
        projectApi = LoginController.getInstance().getLoginClient().create(ProjectAPI.class); //doubt
        final CategoryModel categorymodel = mCategoryModel.get(position);
        holder.textViewTitle.setText(categorymodel.getName());
        holder.imageView.setImageDrawable(ContextCompat.getDrawable(holder.imageView.getContext(),
                categorymodel.getImageId()));




        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long categoryID = categorymodel.getCategoryid();
                Intent displayByCategory = new Intent(holder.parent.getContext(), ProductListActivity.class);
                displayByCategory.putExtra("categoryID", categoryID);
                holder.parent.getContext().startActivity(displayByCategory);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCategoryModel.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textViewTitle;
        CardView parent;


        public ProductViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);

            parent = itemView.findViewById(R.id.cv_parent);
        }

    }
}