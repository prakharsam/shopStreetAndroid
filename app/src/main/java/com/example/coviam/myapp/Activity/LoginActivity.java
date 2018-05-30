package com.example.coviam.myapp.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.example.coviam.myapp.Model.authentication.ResponseFromUser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private ProjectAPI projectApi;
    private EditText userName;
    private EditText password;
    private AlertDialog.Builder alertDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginpage);
        Button login = findViewById(R.id.bt_login1);
        Button signup = findViewById(R.id.bt_signUp1);
        alertDialog = new AlertDialog.Builder(LoginActivity.this);

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
                            if (response.body() != null && response.body().isResponse()) {
                                //edit
                                SharedPreferences.Editor editor = getSharedPreferences("userData", MODE_PRIVATE).edit();
                                editor.putLong("id", response.body().getUserId());
                                editor.putString("email", response.body().getEmail());
                                editor.apply();
                                Toast.makeText(LoginActivity.this, "Login success", Toast.LENGTH_SHORT).show();


                                Intent displayByCategory = new Intent(LoginActivity.this, DisplayByCategoryActivity.class);
                                startActivity(displayByCategory);
                            } else {

                                alertDialog.setTitle("OOps!!");
                                alertDialog.setMessage("password does not match!!");
                                alertDialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                alertDialog.show();

                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseFromUser> call, Throwable t) {
                            alertDialog.setTitle("OOps!!");
                            alertDialog.setMessage("could'nt sign you in!!");
                            alertDialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            alertDialog.show();

                        }
                    });
                } else {
                    alertDialog.setTitle("OOps!!");
                    alertDialog.setMessage("Something Went wrong with our Server .Try  again!!");
                    alertDialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    alertDialog.show();

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
