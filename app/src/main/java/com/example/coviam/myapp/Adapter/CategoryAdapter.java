package com.example.coviam.myapp.Adapter;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.coviam.myapp.DisplayByCategory;
import com.example.coviam.myapp.LoginActivity;
import com.example.coviam.myapp.LoginController;
import com.example.coviam.myapp.Model.Categorymodel;
import com.example.coviam.myapp.ProductDto;
import com.example.coviam.myapp.ProductListActivity;
import com.example.coviam.myapp.ProjectAPI;
import com.example.coviam.myapp.R;
import com.example.coviam.myapp.ResponseFromUser;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ProductViewHolder> {

    private Context mcontex;
    private ProjectAPI projectApi;
    private List<Categorymodel> mCategoryModel;

    public CategoryAdapter(Context mcontex, List<Categorymodel> productmodel) {
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
        final Categorymodel categorymodel = mCategoryModel.get(position);
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
        TextView textViewTitle, textViewShortDesc, textViewRating, textViewPrice;
        CardView parent;

        public ProductViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            parent = itemView.findViewById(R.id.cv_parent);
        }

    }
}