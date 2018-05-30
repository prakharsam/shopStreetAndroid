package com.example.coviam.myapp.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.coviam.myapp.Model.merchant.MerchantDto;
import com.example.coviam.myapp.R;

import java.util.List;

public class MerchantAdapter extends RecyclerView.Adapter<MerchantAdapter.ViewHolder>  {
        private List<MerchantDto> mMerchant;
        private IAdapterCommunicator iAdapterCommunicator;

        public MerchantAdapter(List<MerchantDto> merchants, IAdapterCommunicator iAdapterCommunicator) {
            mMerchant = merchants;
            this.iAdapterCommunicator = iAdapterCommunicator;
        }


        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

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
            holder.btaddtocart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   iAdapterCommunicator.addtocartfromadapter();
                }
            });

            }

        @Override
        public int getItemCount() {

            return mMerchant.size();
        }



    public static interface IAdapterCommunicator {
        void addtocartfromadapter();
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


    }



