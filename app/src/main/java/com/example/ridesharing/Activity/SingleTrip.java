package com.example.ridesharing.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.example.ridesharing.R;
import com.example.ridesharing.Response.UnlockResponse;
import com.example.ridesharing.Rest.ApiClient;
import com.example.ridesharing.Rest.ApiInterface;
import com.example.ridesharing.Utils.PrefUtils;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.CaptureActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SingleTrip extends AppCompatActivity {

    private TextView mcartitle_tv;
    private Button mUnlock_btn;
    private Button mScan_btn;
    ApiInterface apiInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_trip);

        mcartitle_tv = (TextView) findViewById(R.id.carName);
        mScan_btn  = (Button) findViewById(R.id.scan);
        mUnlock_btn = (Button) findViewById(R.id.unlock);
        mScan_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator integrator = new IntentIntegrator(SingleTrip.this);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                //integrator.setPrompt("Scan a barcode");
                integrator.setCameraId(0);  // Use a specific camera of the device
                integrator.setBeepEnabled(true);
                integrator.setCaptureActivity(CaptureActivityPortrait.class);
                //integrator.setBarcodeImageEnabled(true);
                integrator.initiateScan();
            }
        });
        mUnlock_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    String mcAddress = PrefUtils.getFromPrefs(getApplicationContext(),PrefUtils.mc_address,"");
                    String encKey = mcartitle_tv.getText().toString();
                    sendToServer(mcAddress,encKey);
            }
        });

        Bundle mbundle = getIntent().getExtras();
        if (mbundle!=null){
            String string =mbundle.getString("title");
        }
    }

    private void sendToServer(final String mcAddress, final String encKey)
    {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Log.d("reached",mcAddress+"------"+encKey);
        Call<UnlockResponse> call = apiInterface.unlockCar(mcAddress,encKey);
        call.enqueue(new Callback<UnlockResponse>() {
            @Override
            public void onResponse(Call<UnlockResponse> call, Response<UnlockResponse> response) {
                Log.d("unlock",response.body().getStatus());
                new AlertDialog.Builder(SingleTrip.this)
                        .setTitle("Booking Details")
                        .setMessage(response.body().getUnlockData().getMessage())
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent in = new Intent(SingleTrip.this,HomePage.class);
                                startActivity(in);
                            }
                        }).show();
            }
            @Override
            public void onFailure(Call<UnlockResponse> call, Throwable t) {
                Log.d("fail","Unlock Failed");
                /*new AlertDialog.Builder(SingleTrip.this)
                        .setTitle("Booking Details")
                        .setMessage("Unable to Connect Server")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                sendToServer(mcAddress,encKey);
                            }
                        }).show();*/
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Scan Success"/* + result.getContents()*/, Toast.LENGTH_LONG).show();
                mcartitle_tv.setText("");
                mcartitle_tv.setText(result.getContents());
                mScan_btn.setVisibility(View.GONE);
                mUnlock_btn.setVisibility(View.VISIBLE);

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }

    }
}
