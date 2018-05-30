package com.example.coviam.myapp.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.coviam.myapp.network.LoginController;
import com.example.coviam.myapp.Model.authentication.ResponseFromUser;
import com.example.coviam.myapp.Model.authentication.UserInfo;
import com.example.coviam.myapp.network.ProjectAPI;
import com.example.coviam.myapp.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {
    private ProjectAPI projectApi;
    private EditText nameEditText;
    private EditText userName;
    private EditText email;
    private TextInputEditText password;
    private TextInputEditText confirmPassword;
    private EditText address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Button register = findViewById(R.id.bt_register);
        Button skip = findViewById(R.id.bt_skip2);
        nameEditText = findViewById(R.id.et_name);
        userName = findViewById(R.id.et_username);
        email = findViewById(R.id.et_email);
        password = findViewById(R.id.et_password);
        address = findViewById(R.id.et_address);
        confirmPassword=findViewById(R.id.et_confirmpassword);


        projectApi = LoginController.getInstance().getLoginClient().create(ProjectAPI.class);
        register.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(userName.getText().toString().length()!=0 & userName.getText().toString().length()<20 & email.getText().toString().length()!=0 & address.getText().toString().length()!=0) {
                    if (password.getText().toString().equals(confirmPassword.getText().toString())) {
                        Call<ResponseFromUser> userCall = projectApi.addUser(new UserInfo(userName.getText().toString(), password.getText().toString(), email.getText().toString(), address.getText().toString(), nameEditText.getText().toString()));
                        userCall.enqueue(new Callback<ResponseFromUser>() {
                            @Override
                            public void onResponse(Call<ResponseFromUser> call, Response<ResponseFromUser> response) {
                                if (response.body().isResponse() == true) {
                                    //edit
                                    SharedPreferences.Editor editor = getSharedPreferences("userData", MODE_PRIVATE).edit();
                                    editor.putLong("id", response.body().getUserId());
                                    editor.putString("email", response.body().getEmail());
                                    editor.apply();

                                    Toast.makeText(SignupActivity.this, "Sign up success", Toast.LENGTH_SHORT).show();

                                    Intent displayByCategory = new Intent(SignupActivity.this, DisplayByCategoryActivity.class);
                                    startActivity(displayByCategory);
                                } else {
                                    Toast.makeText(SignupActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseFromUser> call, Throwable t) {
                                Toast.makeText(SignupActivity.this, "Failed to add user into database", Toast.LENGTH_LONG);
                            }
                        });
                    } else {

                        Toast.makeText(SignupActivity.this,"Passwords doesn't match",Toast.LENGTH_LONG).show();

                    }
                }else{

                    Toast.makeText(SignupActivity.this, "Enter userName and Email", Toast.LENGTH_LONG).show();

                }
            }
        });


        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent displayByCategory = new Intent(SignupActivity.this, DisplayByCategoryActivity.class);
                startActivity(displayByCategory);
            }
        });
    }
}
