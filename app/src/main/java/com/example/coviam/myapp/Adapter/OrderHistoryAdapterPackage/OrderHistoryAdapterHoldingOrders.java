package com.example.coviam.myapp.Adapter.OrderHistoryAdapterPackage;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.coviam.myapp.Model.Orders.ItemsItem;
import com.example.coviam.myapp.R;

import java.util.List;

public class OrderHistoryAdapterHoldingOrders extends RecyclerView.Adapter {

    private Context mcontex;
    private List<ItemsItem> mItemsItem;

    public OrderHistoryAdapterHoldingOrders(Context mcontex, List<ItemsItem> itemsItem) {
        this.mcontex = mcontex;
        mItemsItem = itemsItem;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.cardviewoforderhistory, null);
        return new OrderHistoryViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final ItemsItem itemsItem = mItemsItem.get(position);
        OrderHistoryViewHolder viewHolder = (OrderHistoryViewHolder) holder;
        Glide.with(viewHolder.imageView.getContext()).load(itemsItem.getImage()).into(viewHolder.imageView);
        viewHolder.productName.setText((String.valueOf(itemsItem.getProductName())));
        viewHolder.merchantName.setText((String.valueOf(itemsItem.getMid())));
        viewHolder.price.setText((String.valueOf(itemsItem.getPrice())));
        viewHolder.quantity.setText((String.valueOf(itemsItem.getQty())));
        }

    @Override
    public int getItemCount() {
        return mItemsItem.size();
    }

    class OrderHistoryViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView productName, merchantName,price,quantity;


        public OrderHistoryViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.yoyo);
            productName = itemView.findViewById(R.id.product_id);
            merchantName = itemView.findViewById(R.id.merchant_id);
            price = itemView.findViewById(R.id.price_tag2);
            quantity = itemView.findViewById(R.id.Quantity_tag1);

        }
    }
}
