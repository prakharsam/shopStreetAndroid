package com.example.coviam.myapp.Adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.coviam.myapp.Model.products.GetItemRequest;
import com.example.coviam.myapp.R;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private Context mcontex;
    private List<GetItemRequest> mcartmodel;
    private IAdapterCommunicator iAdapterCommunicator;

    public CartAdapter(Context mcontex, List<GetItemRequest> mcartmodel, IAdapterCommunicator iAdapterCommunicator) {
        this.mcontex = mcontex;
        this.mcartmodel = mcartmodel;
        this.iAdapterCommunicator = iAdapterCommunicator;
    }

    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mcontex);
        View view = inflater.inflate(R.layout.cartpage, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, final int position) {
        final GetItemRequest cartmodel = mcartmodel.get(position);
        Glide.with(holder.imageView.getContext()).load(cartmodel.getImage()).into(holder.imageView);
        holder.productname.setText(cartmodel.getProductName());
        holder.productprice.setText(String.valueOf(cartmodel.getPrice()));
        holder.quantity.setText(String.valueOf(cartmodel.getQty()));

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iAdapterCommunicator.delete(position, cartmodel.getCartid(), cartmodel.getPid(), cartmodel.getMid());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mcartmodel.size();
    }

    class CartViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView productname, merchantName, productprice;
        TextView quantity;
        CardView parent;
        Button delete;

        public CartViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.thumbnail);
            productname = itemView.findViewById(R.id.name_tag);
            merchantName = itemView.findViewById(R.id.merchant_name);
            productprice = itemView.findViewById(R.id.price_tag1);
            quantity = itemView.findViewById(R.id.Quantity_tag);
            parent = itemView.findViewById(R.id.card);
            delete = itemView.findViewById(R.id.bt_del);

        }

    }

    public interface IAdapterCommunicator {
        void delete(int position, Long cartId, Long prId, Long merchantId);
    }
}

