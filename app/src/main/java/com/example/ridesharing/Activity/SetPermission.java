package com.example.ridesharing.Activity;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import com.example.ridesharing.BuildConfig;
import com.example.ridesharing.R;

public class SetPermission extends AppCompatActivity {
    String message;
    Button permBTM;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);
        permBTM = findViewById(R.id.permBTN);
        Bundle intent = getIntent().getExtras();
        if(intent != null)
        {
            message = intent.getString("message");
            permBTM.setText(message);
        }
        permBTM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(permBTM.getText().toString().equalsIgnoreCase("Turn On GPS"))
                {
                }
                else if(permBTM.getText().toString().equalsIgnoreCase("OPEN SETTINGS"))
                {
                    Intent intent = new Intent();
                    intent.setAction(
                            Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package",
                            BuildConfig.APPLICATION_ID, null);
                    intent.setData(uri);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {
            permBTM.setText("Turn On GPS");
        }
    }
}
