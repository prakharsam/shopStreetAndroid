package com.example.coviam.myapp.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.coviam.myapp.Adapter.MerchantAdapter;
import com.example.coviam.myapp.Model.product.ProductDto;
import com.example.coviam.myapp.Model.products.SearchDto;
import com.example.coviam.myapp.network.LoginController;
import com.example.coviam.myapp.Model.cart.CartData;
import com.example.coviam.myapp.Model.cart.CartResponseDTO;
import com.example.coviam.myapp.Model.merchant.MerchantDto;
import com.example.coviam.myapp.network.ProjectAPI;
import com.example.coviam.myapp.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailActivity extends AppCompatActivity {


    Long productID;
    ProjectAPI projectApi;
    String merchantname, imgUrl, productName;
    Long merchantId;
    Double price;
    boolean flag = true;

    private AlertDialog.Builder alertDialog;

    SearchDto productDto = new SearchDto();


    List<MerchantDto> merchantlist;
    EditText quantityEntry;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        final Intent intent = getIntent();
        merchantlist = new ArrayList<>();
        //this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        RelativeLayout productFromOtherMerchants = findViewById(R.id.other_merchant);
        productFromOtherMerchants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(ProductDetailActivity.this, MerchantActivity.class);
                intent1.putExtra("productID", productID);
                intent1.putExtra("merchantID", merchantId);
                intent1.putExtra("price", price);
                startActivity(intent1);
            }
        });

        Button addcart = findViewById(R.id.bt_cart);
        Button buynow = findViewById(R.id.bt_buynow);
        alertDialog = new AlertDialog.Builder(ProductDetailActivity.this);

        final TextView textViewName = findViewById(R.id.name_tag);
        final TextView textViewPrice = findViewById(R.id.price_tag);
        final TextView description = findViewById(R.id.description_tag);
        final ImageView imageView = findViewById(R.id.product_image);
        final TextView merchantName = findViewById(R.id.merchant_name_tag);
        quantityEntry = findViewById(R.id.quantity_tag2);


        //recyclerview.setAdapter(merchantAdapter);
        projectApi = LoginController.getInstance().getProductClient().create(ProjectAPI.class);
        productID = intent.getLongExtra("productID", 0);
        productName = intent.getStringExtra("productName");
        merchantname = intent.getStringExtra("merchantName");
        price = intent.getDoubleExtra("productPrice", 0.0);
        merchantId = intent.getLongExtra("merchantID", 0);
        imgUrl = intent.getStringExtra("ImgUrl");


        addcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                flag = false;
                addToCart();
            }
        });

        buynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = true;
                addToCart();
            }
        });

        Call<ProductDto> userCall = projectApi.getProductById(productID);
        userCall.enqueue(new Callback<ProductDto>() {
            @Override
            public void onResponse(Call<ProductDto> call, Response<ProductDto> response) {


                if (response.body() != null) {
                    textViewName.setText("Name: " + response.body().getProductName());
                    productDto.setProductID(response.body().getProductID());

                    productDto.setProductName(response.body().getProductName());

                    textViewPrice.setText("Price: " + response.body().getProductPrice() + "");

                    productDto.setProductPrice(response.body().getProductPrice());

                    description.setText(response.body().getProductDescription());

                    productDto.setMerchantID(response.body().getMerchantID());

                    merchantName.setText("Merchant: " + response.body().getMerchantName());

                    Glide.with(ProductDetailActivity.this).load(response.body().getProductImgUrl()).into(imageView);
                }


            }

            @Override
            public void onFailure(Call<ProductDto> call, Throwable t) {

                textViewName.setText(productName);
                textViewPrice.setText(String.valueOf(price));
                merchantName.setText(merchantname);
                Glide.with(ProductDetailActivity.this).load(imgUrl).into(imageView);

            }
        });

        addcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCart();
            }
        });
    }


    private void addToCart() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.show();

        SharedPreferences preferences = getSharedPreferences("userData", MODE_PRIVATE);
        Long userid = preferences.getLong("id", 0);

        EditText editText = findViewById(R.id.quantity_tag2);
        String quantity = editText.getText().toString();

        Long qty;
        if (!quantity.isEmpty()) {
            qty = Long.parseLong(quantity);
        } else {
            qty = 1L;
        }

        ProjectAPI projectApi = LoginController.getInstance().getClient().create(ProjectAPI.class);
        Call<CartResponseDTO> call = projectApi.addToCartApi(new CartData(userid, productID, qty, merchantId, price));
        call.enqueue(new Callback<CartResponseDTO>() {
            @Override
            public void onResponse(Call<CartResponseDTO> call, Response<CartResponseDTO> response) {
                if (null != response.body()) {
                    if (response.body().getSuccess()) {
                        if (flag) {
                            Intent displayByCategory = new Intent(ProductDetailActivity.this, CartPageActivity.class);
                            startActivity(displayByCategory);
                        } else {
                            Toast.makeText(ProductDetailActivity.this, "added to cart", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        alertDialog.setTitle("OOps!!");
                        alertDialog.setMessage("Sorry Failed to add to Cart");
                        alertDialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        alertDialog.show();

                    }
                } else {
                    alertDialog.setTitle("OOps!!");
                    alertDialog.setMessage("Sorry Failed to add to Cart");
                    alertDialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    alertDialog.show();
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<CartResponseDTO> call, Throwable t) {
                alertDialog.setTitle("OOps!!");
                alertDialog.setMessage("Something went  wrong .Try after some time");
                alertDialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertDialog.show();
                progressDialog.dismiss();
            }
        });
    }
}
