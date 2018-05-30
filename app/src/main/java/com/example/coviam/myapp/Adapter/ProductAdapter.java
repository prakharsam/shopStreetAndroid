package com.example.coviam.myapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.coviam.myapp.Activity.ProductDetailActivity;
import com.example.coviam.myapp.Model.product.ProductDto;
import com.example.coviam.myapp.R;

import java.util.List;


public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private Context mcontex;
    private List<ProductDto> mProductDto;

    public ProductAdapter(Context mcontex, List<ProductDto> productDto) {
        this.mcontex = mcontex;
        mProductDto = productDto;
    }


    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mcontex);
        View view = inflater.inflate(R.layout.card_view_product_list, parent,false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ProductViewHolder holder, final int position) {
        final ProductDto productDto = mProductDto.get(position);
        holder.textViewTitle.setText(productDto.getProductName());
        holder.textViewPrice.setText("₹"+String.valueOf(productDto.getProductPrice()));
        Glide.with(holder.imageView.getContext()).load(productDto.getProductImgUrl()).into(holder.imageView);
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Long productID = productDto.getProductID();
                String productName=productDto.getProductName();
                String ImgUrl=productDto.getProductImgUrl();
                String merchantName=productDto.getMerchantName();
                Double price=productDto.getProductPrice();
                Long merchantID=productDto.getMerchantID();
                Intent intent = new Intent(holder.parent.getContext(), ProductDetailActivity.class);
                intent.putExtra("productID", productID);
                intent.putExtra("productName",productName);
                intent.putExtra("merchantName",merchantName);
                intent.putExtra("productPrice",price);
                intent.putExtra("ImgUrl",ImgUrl);
                intent.putExtra("merchantID",merchantID);

                holder.parent.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mProductDto.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textViewTitle, textViewPrice;
        CardView parent;

        public ProductViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.thumbnail);
            textViewTitle = itemView.findViewById(R.id.ProductName);
            textViewPrice = itemView.findViewById(R.id.Price);
            parent = itemView.findViewById(R.id.card_tag);
        }
    }

}

