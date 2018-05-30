package com.example.coviam.myapp.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.coviam.myapp.R;

public class CredentialsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.credentials);
        Button signIn = (Button) findViewById(R.id.bt_signUp);
        Button signUp = (Button) findViewById(R.id.bt_signIn);
        Button skip = (Button) findViewById(R.id.bt_skip);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login = new Intent(CredentialsActivity.this, LoginActivity.class);
                startActivity(login);
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signUp = new Intent(CredentialsActivity.this, SignupActivity.class);
                startActivity(signUp);
            }

            //skip.setOnClickListener(new View.OnClickListener())

        });
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent category = new Intent(CredentialsActivity.this, DisplayByCategoryActivity.class);
                startActivity(category);
            }
        });


    }
}
