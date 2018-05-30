package com.example.coviam.myapp.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.coviam.myapp.network.LoginController;
import com.example.coviam.myapp.network.ProjectAPI;
import com.example.coviam.myapp.R;
import com.example.coviam.myapp.Model.ResponseFromUser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private ProjectAPI projectApi;
    private EditText userName;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginpage);
        Button login = findViewById(R.id.bt_login1);
        Button signup = findViewById(R.id.bt_signUp1);

        userName = findViewById(R.id.et_username);
        password = findViewById(R.id.et_password);

        projectApi = LoginController.getInstance().getLoginClient().create(ProjectAPI.class);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userName.getText().toString().length() != 0 & userName.getText().toString().length() < 20) {
                    Call<ResponseFromUser> userCall = projectApi.authorize(userName.getText().toString(), password.getText().toString());
                    userCall.enqueue(new Callback<ResponseFromUser>() {
                        @Override
                        public void onResponse(Call<ResponseFromUser> call, Response<ResponseFromUser> response) {
                        if(response.body() != null && response.body().isResponse()) {
                                //edit
                                SharedPreferences.Editor editor = getSharedPreferences("userData", MODE_PRIVATE).edit();
                                editor.putLong("id", response.body().getUserId());
                                editor.putString("email", response.body().getEmail());
                                editor.apply();
                                Toast.makeText(LoginActivity.this, "Login success", Toast.LENGTH_SHORT).show();


                                Intent displayByCategory = new Intent(LoginActivity.this, DisplayByCategoryActivity.class);
                                startActivity(displayByCategory);
                            } else {
                                Toast.makeText(LoginActivity.this, "Username/password is wrong", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseFromUser> call, Throwable t) {
                            Toast.makeText(LoginActivity.this, "Failed to add user into database", Toast.LENGTH_LONG);
                        }
                    });
                } else {
                    Toast.makeText(LoginActivity.this, "Enter UserName", Toast.LENGTH_LONG).show();
                }
            }
        });


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent signup = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(signup);
            }
        });


    }
}
