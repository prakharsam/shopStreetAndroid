package com.example.coviam.myapp.Adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.coviam.myapp.Model.MerchantDto;
import com.example.coviam.myapp.ProductDetailActivity;
import com.example.coviam.myapp.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MerchantAdapter extends RecyclerView.Adapter<MerchantAdapter.ViewHolder>{
        private List<MerchantDto> mMerchant;
        private IAdapterCommunicator iAdapterCommunicator;

        public MerchantAdapter(List<MerchantDto> merchants, IAdapterCommunicator iAdapterCommunicator) {
            mMerchant = merchants;
            this.iAdapterCommunicator = iAdapterCommunicator;
        }


        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            // Input the layout id
            View view =
                    LayoutInflater.from(parent.getContext()).inflate(R.layout.merchant_card_view, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
            //Binding UI
            final MerchantDto merchantDto=mMerchant.get(position);
            holder.merchantName.setText(merchantDto.getMerchantName());
            holder.price.setText(merchantDto.getProductPrice()+"");
            holder.quantity.setText(merchantDto.getProductStock()+"");
            holder.rating.setText(merchantDto.getWeightedFactor()+"");

            }

        @Override
        public int getItemCount() {
            // Set the list count
            return mMerchant.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            
            Button btaddtocart;
            TextView merchantName;
            TextView rating;
            TextView quantity;
            TextView price;
            CardView parent;

            public ViewHolder(View itemView) {
                super(itemView);
                merchantName = itemView.findViewById(R.id.merchant_name);
                price = itemView.findViewById(R.id.price_tag1);
                quantity = itemView.findViewById(R.id.Quantity_tag);
                rating=itemView.findViewById(R.id.Rating_tag);
                btaddtocart = itemView.findViewById(R.id.bt_addtocart);
                parent = itemView.findViewById(R.id.cv_parent_merchant);

            }
        }

        public static interface IAdapterCommunicator {
            //void deleteItem(int position);
        }
    }



