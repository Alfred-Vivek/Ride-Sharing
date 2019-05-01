package com.example.ridesharing.Activity;
import android.Manifest;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.ridesharing.Response.LoginResponse;
import com.example.ridesharing.Utils.PrefUtils;
import com.example.ridesharing.R;
import com.example.ridesharing.Rest.ApiClient;
import com.example.ridesharing.Rest.ApiInterface;
import java.text.DateFormat;
import java.util.Date;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText mUserName, mPassword;
    private String username, password;
    private Button mLogin;
    ProgressDialog progressDialog;
    private ApiInterface apiServices;
    LocationService lsr;
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mUserName = (EditText) findViewById(R.id.user_name);
        mPassword = (EditText) findViewById(R.id.paswd_edt);
        mLogin = (Button) findViewById(R.id.login);
        mLogin.setOnClickListener(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        lsr = new LocationService(this);
    }
    @Override
    public void onClick(View view) {
        if (view == this.mLogin) {
            username = mUserName.getText().toString();
            password = mPassword.getText().toString();
            if (username.isEmpty()) {
                mUserName.setError(getString(R.string.error_empty_usrname));
                return;
            }
            if (password.isEmpty()) {
                mPassword.setError(getString(R.string.error_empty_pswd));
                return;
            }
            signIn();
        }
    }
    private void signIn() {
//        if (username.equalsIgnoreCase("a"))
//            processDetails();
        progressDialog.show();
        apiServices = ApiClient.getClient().create(ApiInterface.class);
        Call<LoginResponse> call = apiServices.sendLoginDetails(username,password);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                progressDialog.dismiss();
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
        progressDialog.show();
        PrefUtils.saveToPrefs(getApplicationContext(),PrefUtils.user_name,lr.getLoginData().getName());
        PrefUtils.saveToPrefs(getApplicationContext(),PrefUtils.user_email,lr.getLoginData().getEmail());
        PrefUtils.saveToPrefs(getApplicationContext(),PrefUtils.mc_address,lr.getLoginData().getMcaddress());
        if (checkPermissions()) {
            progressDialog.dismiss();
            Intent intent = new Intent(LoginActivity.this,MapsActivity.class);
            startActivity(intent);
        } else if (!checkPermissions()) {
            requestPermissions();
        }
    }
    public void invalidDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Invalid Login Details")
                .setMessage("\n Please Check Your Login Credentials")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setNeutralButton(android.R.string.ok, null).show();
    }
    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }
    private void requestPermissions() {
        progressDialog.dismiss();
        boolean shouldProvideRationale = ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        if (shouldProvideRationale) {
            showSnackbar(R.string.permission_rationale,
                    android.R.string.ok, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ActivityCompat.requestPermissions(LoginActivity.this,
                                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                    REQUEST_PERMISSIONS_REQUEST_CODE);
                        }
                    });
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSIONS_REQUEST_CODE: {
                if (grantResults.length <= 0) {
                } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(LoginActivity.this,MapsActivity.class);
                    intent.putExtra("email",username);
                    startActivity(intent);
                } else {
                    Intent in = new Intent(this, SetPermission.class);
                    in.putExtra("message", "OPEN SETTINGS");
                    startActivity(in);
                }
                return;
            }
        }
    }
    private void showSnackbar(final int mainTextStringId, final int actionStringId,View.OnClickListener listener) {
        Snackbar.make(
                findViewById(android.R.id.content),
                getString(mainTextStringId),
                Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(actionStringId), listener).show();
    }
}
