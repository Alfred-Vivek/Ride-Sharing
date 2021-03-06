package com.example.ridesharing.Activity;
import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.os.Bundle;
import com.example.ridesharing.R;
import com.example.ridesharing.Utils.PrefUtils;

public class SplashScreen extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        if (getIntent().getExtras() != null && getIntent().getExtras().getBoolean("EXIT", false)) {
            finish();
        }
        start();
    }
    private void start() {
        Thread splash = new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    if (!PrefUtils.getFromPrefs(getApplicationContext(), PrefUtils.user_name, "").equalsIgnoreCase("")) {
                        Intent intent = new Intent(SplashScreen.this, MapsActivity.class);
                        startActivity(intent);
                    }
                    else
                    {
                        Intent login = new Intent(SplashScreen.this,LoginActivity.class);
                        startActivity(login);
                    }
                }
            }
        };
        splash.start();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    @Override
    protected void onResume() {
        super.onResume();

    }
}