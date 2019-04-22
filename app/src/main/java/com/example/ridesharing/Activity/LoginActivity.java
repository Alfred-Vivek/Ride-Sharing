package com.example.ridesharing.Activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.ridesharing.Response.LoginResponse;
import com.example.ridesharing.Utils.PrefUtils;
import com.example.ridesharing.R;
import com.example.ridesharing.Rest.ApiClient;
import com.example.ridesharing.Rest.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mUserName,mPassword;
    private String username,password;
    private Button mLogin;
    ProgressDialog progressDialog;
    private ApiInterface apiServices;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mUserName = (EditText) findViewById(R.id.user_name);
        mPassword = (EditText) findViewById(R.id.paswd_edt);
        mLogin = (Button) findViewById(R.id.login);
        mLogin.setOnClickListener(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("util",PrefUtils.getFromPrefs(getApplicationContext(),PrefUtils.user_name,""));
        if (!PrefUtils.getFromPrefs(getApplicationContext(),PrefUtils.user_name,"").equalsIgnoreCase("")){
            Intent intent=new Intent(LoginActivity.this,HomePage.class);
            startActivity(intent);
        }
    }

    @Override
    public void onClick(View view) {
        if (view==this.mLogin){
            username=mUserName.getText().toString();
            password=mPassword.getText().toString();
            if (username.isEmpty())
            {
                mUserName.setError(getString(R.string.error_empty_usrname));
                return;
            }
            if (password.isEmpty())
            {
                mPassword.setError(getString(R.string.error_empty_pswd));
                return;
            }
            signIn();
        }
    }

    private void signIn() {
        apiServices = ApiClient.getClient().create(ApiInterface.class);
        Log.d("before",username);
        Call<LoginResponse> call = apiServices.sendLoginDetails(username,password);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                progressDialog.dismiss();
                Log.d("inside",response.code()+"");
                if(response.code() == 200){
                    if(response.body().getStatus().equalsIgnoreCase("success")){ ;
                        LoginResponse loginResponse = response.body();
                        processDetails(loginResponse);
                    }else{
                        invalidDialog();
                    }
                }else if(response.code() == 400){
                    invalidDialog();
                }
            }
            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.d("fail","sdad");
                progressDialog.dismiss();
                new AlertDialog.Builder(LoginActivity.this)
                        .setTitle("Failed to connect!")
                        .setMessage("Try connecting to server again?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton("Try Again", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                signIn();
                            }
                        })
                        .setNegativeButton("Exit", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                finishAffinity();
                            }
                        }).show();
            }
        });
    }
    public void processDetails(LoginResponse lr)
    {
        PrefUtils.saveToPrefs(getApplicationContext(),PrefUtils.user_name,lr.getLoginData().getName());
        PrefUtils.saveToPrefs(getApplicationContext(),PrefUtils.user_email,lr.getLoginData().getEmail());
        PrefUtils.saveToPrefs(getApplicationContext(),PrefUtils.mc_address,lr.getLoginData().getMcaddress());
        Intent intent = new Intent(LoginActivity.this,HomePage.class);
        intent.putExtra("email",username);
        startActivity(intent);
    }
    public void invalidDialog()
    {
        new AlertDialog.Builder(this)
                .setTitle("Invalid Login Details")
                .setMessage("\n Please Check Your Login Credentials")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setNeutralButton(android.R.string.ok, null).show();
    }


}
