package com.example.coviam.myapp.Activity;

import android.app.ProgressDialog;
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
    //IProductAPI mIProductAPI;

    Long productID;
    ProjectAPI projectApi, projectApi1;
    String merchantname, imgUrl, productName;
    Long merchantId;
    Double price;

    SearchDto productDto = new SearchDto();
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
        //recyclerview = (RecyclerView) findViewById(R.id.recycler_view1);
        //recyclerview.setLayoutManager(new GridLayoutManager(ProductDetailActivity.this, 1));
        RelativeLayout productFromOtherMerchants = findViewById(R.id.other_merchant);
        TextView otherMerchant=findViewById(R.id.no_of_merchant);
        otherMerchant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(ProductDetailActivity.this,MerchantActivity.class);
                intent1.putExtra("productID",productID);
                intent1.putExtra("merchantID",merchantId);
                intent1.putExtra("price",price);
                startActivity(intent1);
            }
        });

        Button addcart = findViewById(R.id.bt_cart);
        Button buynow = findViewById(R.id.bt_buynow);

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



    private void addToCart (){
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
    }
}
