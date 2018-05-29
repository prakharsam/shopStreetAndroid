package com.example.coviam.myapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.coviam.myapp.Adapter.MerchantAdapter;
import com.example.coviam.myapp.Adapter.ProductAdapter;
import com.example.coviam.myapp.Model.CartData;
import com.example.coviam.myapp.Model.CartResponseDTO;
import com.example.coviam.myapp.Model.MerchantDto;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailActivity extends AppCompatActivity implements MerchantAdapter.IAdapterCommunicator {
    //IProductAPI mIProductAPI;

    Long productID;
    String merchantname,imgUrl,productName;
    Long merchantId;
    Double price;

    ProjectAPI projectApi;
    ProductDto productDto = new ProductDto();
    MerchantDto merchantDto;
    ImageView imageView;
    TextView textViewName, textViewPrice, description, merchantName, rating, productPrice, quantity;
    Button cart, buynow;
    RecyclerView recyclerview;
    MerchantAdapter merchantAdapter;
    List<MerchantDto> merchantlist;
    EditText quantityEntry;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        final Intent intent = getIntent();
        merchantlist = new ArrayList<>();
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        recyclerview = (RecyclerView) findViewById(R.id.recycler_view1);
        recyclerview.setLayoutManager(new GridLayoutManager(ProductDetailActivity.this, 1));

        Button addcart = findViewById(R.id.bt_cart);
        Button buynow = findViewById(R.id.bt_buynow);
        Button addtocart=findViewById(R.id.bt_addtocart);
        final TextView textViewName = findViewById(R.id.name_tag);
        final TextView textViewPrice = findViewById(R.id.price_tag);
        final TextView description = findViewById(R.id.description_tag);
        final ImageView imageView = findViewById(R.id.product_image);
        final TextView merchantName = findViewById(R.id.merchant_name_tag);
        final EditText quantityEntry = findViewById(R.id.quantity_tag2);


        merchantAdapter = new MerchantAdapter(merchantlist, this);
        recyclerview.setAdapter(merchantAdapter);
        projectApi = LoginController.getInstance().getProductClient().create(ProjectAPI.class);
        productID = intent.getLongExtra("productID", 0);
        productName=intent.getStringExtra("productName");
        merchantname=intent.getStringExtra("merchantName");
        price = intent.getDoubleExtra("productPrice",0.0);
        merchantId=intent.getLongExtra("merchantID",0);
        imgUrl=intent.getStringExtra("ImgUrl");

        Call<ProductDto> userCall = projectApi.getproductbyid(productID);
        userCall.enqueue(new Callback<ProductDto>() {
            @Override
            public void onResponse(Call<ProductDto> call, Response<ProductDto> response) {
                if(response.body()!=null) {
                    textViewName.setText("NAME:" + response.body().getProductName());
                    productDto.setProductID(response.body().getProductID());

                    productDto.setProductName(response.body().getProductName());

                    textViewPrice.setText("PRICE:" + response.body().getProductPrice() + "");

                    productDto.setProductPrice(response.body().getProductPrice());

                    description.setText(response.body().getProductDescription());

                    productDto.setMerchantID(response.body().getMerchantID());

                    merchantName.setText("MERCHANT:" + response.body().getMerchantName());

                    Glide.with(ProductDetailActivity.this).load(response.body().getProductImgUrl()).into(imageView);
                }
                else if(response.body()==null){
                    textViewName.setText(productName);
                    textViewPrice.setText(String.valueOf(price));
                    merchantName.setText(merchantname);
                    Glide.with(ProductDetailActivity.this).load(imgUrl).into(imageView);
                }
            }

            @Override
            public void onFailure(Call<ProductDto> call, Throwable t) {
                Toast.makeText(ProductDetailActivity.this, "No description to display", Toast.LENGTH_LONG).show();
            }
        });


        Call<List<MerchantDto>> userCall1 = projectApi.getmerchantsbypid(productID);
        userCall1.enqueue(new Callback<List<MerchantDto>>() {
            @Override
            public void onResponse(Call<List<MerchantDto>> call, Response<List<MerchantDto>> response) {
                merchantlist.addAll(response.body());
                merchantAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<MerchantDto>> call, Throwable t) {
                Toast.makeText(ProductDetailActivity.this, "No merchants to display", Toast.LENGTH_LONG).show();
            }
        });


        addcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();

                intent.putExtra("pid", productID);

                intent.putExtra("mid", productDto.getMerchantID());

                System.out.println("QTY" + quantityEntry.getText().toString());

                intent.putExtra("qty", quantityEntry.getText().toString());


                intent.putExtra("price", productDto.getProductPrice());


                projectApi = LoginController.getInstance().getClient().create(ProjectAPI.class);
                addToCart();

            }
        });

//        addtocart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = getIntent();
//
//                intent.putExtra("pid", productID);
//
//                intent.putExtra("mid", productDto.getMerchantID());
//
//                System.out.println("QTY" + quantityEntry.getText().toString());
//
//                intent.putExtra("qty", quantityEntry.getText().toString());
//
//
//                intent.putExtra("price", productDto.getProductPrice());
//
//
//                projectApi = LoginController.getInstance().getClient().create(ProjectAPI.class);
//                addToCart();
//
//            }
//        });

//        buynow.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                projectApi = LoginController.getInstance().getClient().create(ProjectAPI.class);
//                addToCart();
//            }
//        });

    }


    private void addToCart() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.show();

        SharedPreferences preferences = getSharedPreferences("userData", MODE_PRIVATE);
        Long userid = preferences.getLong("id", 0);


        Long pid = getIntent().getLongExtra("pid", 0L);
        String quantity = getIntent().getStringExtra("qty");
        Long mid = getIntent().getLongExtra("mid", 0L);
        Double price = getIntent().getDoubleExtra("price", 0);

        Long qty = 0L;
        if (quantity != null && !quantity.isEmpty()) {
            qty = Long.parseLong(quantity);
        } else {
            qty = 1L;
        }


        Call<CartResponseDTO> call = projectApi.addToCartApi(new CartData(userid, pid, qty, mid, price));
        call.enqueue(new Callback<CartResponseDTO>() {
            @Override
            public void onResponse(Call<CartResponseDTO> call, Response<CartResponseDTO> response) {
                if (response.body().getSuccess()) {
                    progressDialog.dismiss();
                    Intent displayByCategory = new Intent(ProductDetailActivity.this, CartPageActivity.class);
                    startActivity(displayByCategory);

                    Toast.makeText(ProductDetailActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(ProductDetailActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<CartResponseDTO> call, Throwable t) {
                progressDialog.dismiss();
            }
        });

//        buynow.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = getIntent();
//
//                intent.putExtra("pid", productID);
//
//                intent.putExtra("mid", productDto.getMerchantID());
//
//                System.out.println("QTY" + quantityEntry.getText().toString());
//
//                intent.putExtra("qty", quantityEntry.getText().toString());
//
//
//                intent.putExtra("price", productDto.getProductPrice());
//
//
//                projectApi = LoginController.getInstance().getClient().create(ProjectAPI.class);
//                addToCart();
//
//            }
//        });
    }


}
