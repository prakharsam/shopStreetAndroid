package com.example.coviam.myapp.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.coviam.myapp.Adapter.CategoryAdapter;
import com.example.coviam.myapp.Model.category.Categorymodel;
import com.example.coviam.myapp.R;

import java.util.ArrayList;
import java.util.List;

public class DisplayByCategoryActivity extends AppCompatActivity {

    private Toolbar mTopToolbar;
    RecyclerView recyclerview;
    CategoryAdapter categoryAdapter;
    List<Categorymodel> productlist;

    Menu customMenu;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveTaskToBack(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        productlist = new ArrayList<>();
        recyclerview = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerview.setHasFixedSize(true);
        recyclerview.setLayoutManager(new GridLayoutManager(this, 2));
        SharedPreferences editor = getSharedPreferences("userData", MODE_PRIVATE);
        if (!editor.contains("id")) {
            Intent intent = new Intent(DisplayByCategoryActivity.this, LoginActivity.class);
            startActivity(intent);

           Button orderHistory=findViewById(R.id.bt_orderHistory);

            orderHistory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent  = new Intent(DisplayByCategoryActivity.this,OrderHistoryActivity.class);
                    startActivity(intent);
                }
            });

        }



        productlist.add(
                new Categorymodel(
                        R.drawable.mobile, "Mobiles", 1));

        productlist.add(
                new Categorymodel(
                        R.drawable.footwear, "Footwear", 2));
        productlist.add(
                new Categorymodel(
                        R.drawable.watch, "Watches", 3));
        productlist.add(
                new Categorymodel(
                        R.drawable.clothing, "Clothing", 4));

        productlist.add(
                new Categorymodel(
                        R.drawable.laptop, "Laptops", 5));
        productlist.add(
                new Categorymodel(
                        R.drawable.tv, "TV", 6));
        categoryAdapter = new CategoryAdapter(DisplayByCategoryActivity.this, productlist);
        recyclerview.setAdapter(categoryAdapter);

        mTopToolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(mTopToolbar);
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
            Intent displayByCategory = new Intent(DisplayByCategoryActivity.this, CartPageActivity.class);
            startActivity(displayByCategory);
            return true;
        } else if (id == R.id.action_search) {

            Intent intent = new Intent(DisplayByCategoryActivity.this, ProductListActivity.class);
            intent.putExtra("isSearch", true);
            intent.putExtra("searchName", ((EditText) findViewById(R.id.et_search)).getText().toString());
            startActivity(intent);


        } else if (id == R.id.action_logout) {
            SharedPreferences.Editor editors = getSharedPreferences("userData", MODE_PRIVATE).edit();
            editors.clear();
            editors.apply();
            Intent intent = new Intent(DisplayByCategoryActivity.this, LoginActivity.class);
            startActivity(intent);
        }else if(id== R.id.action_home) {
            Intent displayByCategory = new Intent(DisplayByCategoryActivity.this, DisplayByCategoryActivity.class);
            startActivity(displayByCategory);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }


}
