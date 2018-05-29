package com.example.coviam.myapp.Adapter.OrderHistoryAdapterPackage;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.coviam.myapp.Model.Orders.OrdersItem;
import com.example.coviam.myapp.OrderHistoryActivity;
import com.example.coviam.myapp.R;

import java.util.List;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.OrderHistoryViewHolder>

{
    private Context mcontex;
    private List<OrdersItem> mOrdersItemList;
    public OrderHistoryAdapter(Context context, List<OrdersItem> orderModelList, OrderHistoryActivity orderHistory) {
        this.mcontex = context;
        mOrdersItemList = orderModelList;
    }

    @Override
    public OrderHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recycler_view_order_history1, null);
        return new OrderHistoryAdapter.OrderHistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderHistoryViewHolder holder, int position) {

        OrdersItem ordersItem = mOrdersItemList.get(position);

        OrderHistoryAdapterHoldingOrders orderHistoryAdapterHoldingOrders =
                new OrderHistoryAdapterHoldingOrders(mcontex, mOrdersItemList.get(position).getItems());
        holder.mRecyclerView.setLayoutManager(new LinearLayoutManager(mcontex));
        holder.mRecyclerView.setAdapter(orderHistoryAdapterHoldingOrders);
        orderHistoryAdapterHoldingOrders.notifyDataSetChanged();
       // holder.amount.setText(String.valueOf(mOrdersItemList.get(position).getAmount()));
    }

    @Override
    public int getItemCount() {

        return  mOrdersItemList.size();
    }

    class OrderHistoryViewHolder extends RecyclerView.ViewHolder {
        RecyclerView mRecyclerView;
        TextView amount;

        public OrderHistoryViewHolder(View itemView) {
            super(itemView);
            mRecyclerView = itemView.findViewById(R.id.recycler4_view);
           // amount =itemView.findViewById(R.id.bt_amount);

        }
    }
}

