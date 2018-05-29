package com.example.coviam.myapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.coviam.myapp.Adapter.CategoryAdapter;
import com.example.coviam.myapp.Adapter.ProductAdapter;
import com.example.coviam.myapp.Model.ProductModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductListActivity extends AppCompatActivity {

    //String imgUrl = "https://api.androidhive.info/images/glide/medium/deadpool.jpg";
    private Toolbar mTopToolbar;
    private ProjectAPI projectApi;
    RecyclerView recyclerview;
    ProductAdapter productadapter;
    List<ProductDto> productlist;
    List<com.example.coviam.myapp.Model.ProductDto> productlist1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        boolean boolvariable;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.productlist);
        productlist = new ArrayList<>();
        productlist1=new ArrayList<>();
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        recyclerview = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerview.setHasFixedSize(true);
        recyclerview.setLayoutManager(new GridLayoutManager(this, 2));
        Intent intent = getIntent();
        Long categoryId = intent.getLongExtra("categoryID", 0);

        String name = intent.getStringExtra("searchName");
        boolvariable = intent.getBooleanExtra("isSearch", false);

        productadapter = new ProductAdapter(this, productlist);
        recyclerview.setAdapter(productadapter);
        mTopToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mTopToolbar);
        if (boolvariable == true) {

            projectApi = LoginController.getInstance().getSearchClient().create(ProjectAPI.class);

            Call<List<com.example.coviam.myapp.Model.ProductDto>> userCall2 = projectApi.search(name);
            userCall2.enqueue(new Callback<List<com.example.coviam.myapp.Model.ProductDto>>() {
                @Override
                public void onResponse(Call<List<com.example.coviam.myapp.Model.ProductDto>> call, Response<List<com.example.coviam.myapp.Model.ProductDto>> response) {
                    productlist1.addAll(response.body());
                    for (int i = 0 ; i < response.body().size() ; i++) {
                        com.example.coviam.myapp.Model.ProductDto existingProductDto = response.body().get(i);
                        ProductDto productDto = new ProductDto();
                        productDto.setProductID(existingProductDto.getProductId());
                        productDto.setProductName(existingProductDto.getProductName());
                        productDto.setProductImgUrl(existingProductDto.getProductImgUrl());
                        productlist.add(productDto);
//                        productDto.setProductPrice(existingProductDto.getPric);
                    }
                    productadapter.notifyDataSetChanged();
                    Toast.makeText(ProductListActivity.this, "Got Products For Searchword", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(Call<List<com.example.coviam.myapp.Model.ProductDto>> call, Throwable t) {
                    Toast.makeText(ProductListActivity.this, "No Products for Searchword ", Toast.LENGTH_LONG).show();
                }
            });
        } else {
            projectApi = LoginController.getInstance().getProductClient().create(ProjectAPI.class);

            Call<List<ProductDto>> userCall = projectApi.getproductsbycid(categoryId);
            userCall.enqueue(new Callback<List<ProductDto>>() {
                @Override
                public void onResponse(Call<List<ProductDto>> call, Response<List<ProductDto>> response) {
                    productlist.addAll(response.body());
                    productadapter.notifyDataSetChanged();
                    Toast.makeText(ProductListActivity.this, "Got products of category", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(Call<List<ProductDto>> call, Throwable t) {
                    Toast.makeText(ProductListActivity.this, "No products in category", Toast.LENGTH_LONG).show();
                }

            });
        }
    }



    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menuresource, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_cart) {
            Intent displayByCategory = new Intent(ProductListActivity.this, CartPageActivity.class);
            startActivity(displayByCategory);
            //Toast.makeText(ProductListActivity.this, "Action clicked", Toast.LENGTH_LONG).show();
            return true;
        } else if (id == R.id.action_search) {
            //TODO handle search click

            Toast.makeText(ProductListActivity.this, "Action searched clicked", Toast.LENGTH_LONG).show();

        }

        return super.onOptionsItemSelected(item);
    }

}
