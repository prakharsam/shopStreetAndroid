package com.example.coviam.myapp.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.coviam.myapp.Adapter.OrderHistoryAdapterPackage.OrderHistoryAdapter;
import com.example.coviam.myapp.network.LoginController;
import com.example.coviam.myapp.Model.Orders.ItemsItem;
import com.example.coviam.myapp.Model.Orders.OrdersParentResponse;
import com.example.coviam.myapp.network.ProjectAPI;
import com.example.coviam.myapp.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderHistoryActivity extends AppCompatActivity {

    private RecyclerView recyclerview;

    private ProjectAPI mIProductAPI1;
    private AlertDialog.Builder alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_view_order_history_level1);


        recyclerview.setHasFixedSize(true);
        recyclerview.setLayoutManager(new LinearLayoutManager(this.getApplicationContext()));
        mIProductAPI1 = LoginController.getInstance().getOrderHistoryClient().create(ProjectAPI.class);
        alertDialog = new AlertDialog.Builder(OrderHistoryActivity.this);
        viewOrderHistoryApi();

        Button categoryreturn = findViewById(R.id.back_to_category);
        categoryreturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(OrderHistoryActivity.this, DisplayByCategoryActivity.class);
                startActivity(i1);
            }
        });

    }

    private void viewOrderHistoryApi() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.show();

        SharedPreferences preferences = getSharedPreferences("userData", MODE_PRIVATE);
        Long userId = preferences.getLong("id", 0L);

        Call<OrdersParentResponse> call = mIProductAPI1.orderHistory(userId);

        call.enqueue(new Callback<OrdersParentResponse>() {

            @Override
            public void onResponse(Call<OrdersParentResponse> call, Response<OrdersParentResponse> response) {
                progressDialog.dismiss();

                if (null != response.body()) {
                    recyclerview.setAdapter(new OrderHistoryAdapter(
                            OrderHistoryActivity.this, response.body().getOrders(), OrderHistoryActivity.this));
                } else {

                    alertDialog.setTitle("OOps!!");
                    alertDialog.setMessage("No orders to show");
                    alertDialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    alertDialog.show();

                }
            }

            @Override
            public void onFailure(Call<OrdersParentResponse> call, Throwable t) {

                alertDialog.setTitle("OOps!!");
                alertDialog.setMessage("Something went wrong .try again!!");
                alertDialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertDialog.show();
            }
        });
        progressDialog.show();


    }

}
