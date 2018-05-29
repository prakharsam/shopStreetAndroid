package com.example.coviam.myapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coviam.myapp.Adapter.OrderHistoryAdapterPackage.OrderHistoryAdapter;
import com.example.coviam.myapp.Model.Orders.ItemsItem;
import com.example.coviam.myapp.Model.Orders.OrdersParentResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

    public class OrderHistoryActivity extends AppCompatActivity {

        RecyclerView recyclerview;
        OrderHistoryAdapter orderAdapter;
        List<ItemsItem> itemsItems;

        ProjectAPI mIProductAPI1;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.recycler_view_order_history);

            recyclerview = (RecyclerView) findViewById(R.id.recycler3_view);
            recyclerview.setHasFixedSize(true);
            recyclerview.setLayoutManager(new LinearLayoutManager(this.getApplicationContext()));
            mIProductAPI1 = LoginController.getInstance().getOrderHistoryClient().create(ProjectAPI.class);



            viewOrderHistoryApi();

//            orderAdapter = new OrderHistoryAdapter(OrderHistoryActivity.this,ordersItems,this);
//            recyclerview.setAdapter(orderAdapter);



            Button categoryreturn =findViewById(R.id.back_to_category);
            categoryreturn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i1= new Intent(OrderHistoryActivity.this,DisplayByCategory.class);
                    startActivity(i1);
                }
            });

        }

        private void viewOrderHistoryApi(){
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.show();

            SharedPreferences preferences = getSharedPreferences("userData", MODE_PRIVATE);
            Long userId = preferences.getLong("id", 0L);

            Call<OrdersParentResponse> call = mIProductAPI1.orderHistory(userId);

            call.enqueue(new Callback<OrdersParentResponse>()
            {

                @Override
                public void onResponse(Call<OrdersParentResponse> call, Response<OrdersParentResponse> response) {
                    progressDialog.dismiss();
                    recyclerview.setAdapter(new OrderHistoryAdapter(
                            OrderHistoryActivity.this, response.body().getOrders(), OrderHistoryActivity.this));
                }

                @Override
                public void onFailure(Call<OrdersParentResponse> call, Throwable t)
                {
                    Toast.makeText(OrderHistoryActivity.this,"Failed to Retrieve Data",Toast.LENGTH_LONG).show();
                }
            });
            progressDialog.show();


        }

}
