package com.example.coviam.myapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
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
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coviam.myapp.Adapter.CategoryAdapter;
import com.example.coviam.myapp.Model.Categorymodel;

import java.util.ArrayList;
import java.util.List;

public class DisplayByCategory extends AppCompatActivity {

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
            Intent intent = new Intent(DisplayByCategory.this, LoginActivity.class);
            startActivity(intent);
        }

        Button orderhistory = findViewById(R.id.bt_orderhistory);
        orderhistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DisplayByCategory.this, OrderHistoryActivity.class);
                startActivity(intent);
            }
        });
/*


        SharedPreferences.Editor editors = getSharedPreferences("userData", MODE_PRIVATE).edit();
        editors.clear();
        editors.apply();
        Intent intent=new Intent(DisplayByCategory.this, LoginActivity.class);
        startActivity(intent);
*/

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
        categoryAdapter = new CategoryAdapter(DisplayByCategory.this, productlist);
        recyclerview.setAdapter(categoryAdapter);

        mTopToolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(mTopToolbar);
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
            Intent displayByCategory = new Intent(DisplayByCategory.this, CartPageActivity.class);
            startActivity(displayByCategory);
            return true;
        } else if (id == R.id.action_search) {
            Intent intent = new Intent(DisplayByCategory.this, ProductListActivity.class);
            ;
            intent.putExtra("isSearch", true);
            intent.putExtra("searchName", ((EditText) findViewById(R.id.et_search)).getText().toString());
            startActivity(intent);
            Toast.makeText(DisplayByCategory.this, "Action searched clicked", Toast.LENGTH_LONG).show();

        } else if (id == R.id.action_logout) {
            SharedPreferences.Editor editors = getSharedPreferences("userData", MODE_PRIVATE).edit();
            editors.clear();
            editors.apply();
            Intent intent = new Intent(DisplayByCategory.this, LoginActivity.class);
            startActivity(intent);
        }else if(id== R.id.action_home) {
            Intent displayByCategory = new Intent(DisplayByCategory.this, DisplayByCategory.class);
            startActivity(displayByCategory);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }


}
