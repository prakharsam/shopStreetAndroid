package com.example.coviam.myapp.Activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coviam.myapp.Adapter.ProductAdapter;
import com.example.coviam.myapp.Model.product.ProductDto;
import com.example.coviam.myapp.Model.products.SearchDto;
import com.example.coviam.myapp.network.LoginController;
import com.example.coviam.myapp.network.ProjectAPI;
import com.example.coviam.myapp.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductListActivity extends AppCompatActivity {


    private Toolbar mTopToolbar;
    private ProjectAPI projectApi;
    RecyclerView recyclerview;
    ProductAdapter productadapter;
    List<ProductDto> productlist;
    List<SearchDto> productlist1;
    boolean boolvariable;
    private AlertDialog alertDialog = new AlertDialog.Builder(ProductListActivity.this).create();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.productlist);
        productlist = new ArrayList<>();
        productlist1 = new ArrayList<>();
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        recyclerview = findViewById(R.id.recycler_view);
        recyclerview.setHasFixedSize(true);
        recyclerview.setLayoutManager(new GridLayoutManager(this, 2));
        Intent intent = getIntent();
        Long categoryId = intent.getLongExtra("categoryID", 0);

        String name = intent.getStringExtra("searchName");
        boolvariable = intent.getBooleanExtra("isSearch", false);
        TextView text = findViewById(R.id.searchEditText);
        text.setText(name);
        productadapter = new ProductAdapter(this, productlist);
        recyclerview.setAdapter(productadapter);
        mTopToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mTopToolbar);
        if (boolvariable == true) {

            projectApi = LoginController.getInstance().getSearchClient().create(ProjectAPI.class);

            Call<List<SearchDto>> userCall2 = projectApi.search(name);
            userCall2.enqueue(new Callback<List<SearchDto>>() {
                @Override
                public void onResponse(Call<List<SearchDto>> call, Response<List<SearchDto>> response) {
                    if (null != response.body()) {
                        productlist1.addAll(response.body());
                        for (int i = 0; i < response.body().size(); i++) {
                            SearchDto existingSearchDto = response.body().get(i);
                            ProductDto productDto = new ProductDto();
                            if (response.body() == null) {
                                alertDialog.setTitle("OOps!!");
                                alertDialog.setMessage("No products available!!");
                                alertDialog.show();


                            } else {
                                productDto.setProductPrice(existingSearchDto.getProductPrice());
                                productDto.setProductImgUrl(existingSearchDto.getProductImgUrl());
                                productDto.setProductName(existingSearchDto.getProductName());
                                productDto.setProductID(existingSearchDto.getProductID());
                                productDto.setMerchantName(existingSearchDto.getProductMerchantName());
                                productlist.add(productDto);
                            }
                        }
                        productadapter.notifyDataSetChanged();

                    }
                }

                @Override
                public void onFailure(Call<List<SearchDto>> call, Throwable t) {
                    alertDialog.setTitle("OOps!!");
                    alertDialog.setMessage("Not able to retrieve products!!");
                    alertDialog.show();
                }
            });}

        else {
            projectApi = LoginController.getInstance().getProductClient().create(ProjectAPI.class);

            Call<List<ProductDto>> userCall = projectApi.getProductsByCid(categoryId);
            userCall.enqueue(new Callback<List<ProductDto>>() {
                @Override
                public void onResponse(Call<List<ProductDto>> call, Response<List<ProductDto>> response) {
                    productlist.addAll(response.body());
                    productadapter.notifyDataSetChanged();

                }

                @Override
                public void onFailure(Call<List<ProductDto>> call, Throwable t) {
                    Toast.makeText(ProductListActivity.this, "No Products  retrieved", Toast.LENGTH_LONG).show();
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

        getMenuInflater().inflate(R.menu.menuresource, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        if (id == R.id.action_cart) {
            Intent displayByCategory = new Intent(ProductListActivity.this, CartPageActivity.class);
            startActivity(displayByCategory);
            return true;
        } else if (id == R.id.action_search) {
            Intent intent = new Intent(ProductListActivity.this, ProductListActivity.class);
            intent.putExtra("isSearch", true);
            intent.putExtra("searchName", ((EditText) findViewById(R.id.searchEditText)).getText().toString());
            startActivity(intent);
            Toast.makeText(ProductListActivity.this, "Action searched clicked", Toast.LENGTH_LONG).show();

        } else if (id == R.id.action_logout) {
            SharedPreferences.Editor editors = getSharedPreferences("userData", MODE_PRIVATE).edit();
            editors.clear();
            editors.apply();
            Intent intent = new Intent(ProductListActivity.this, LoginActivity.class);
            startActivity(intent);
        } else if (id == R.id.action_home) {
            Intent displayByCategory = new Intent(ProductListActivity.this, DisplayByCategoryActivity.class);
            startActivity(displayByCategory);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
