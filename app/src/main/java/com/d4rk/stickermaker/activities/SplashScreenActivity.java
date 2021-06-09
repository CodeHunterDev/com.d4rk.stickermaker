package com.d4rk.stickermaker.activities;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import androidx.appcompat.app.AppCompatActivity;
import com.d4rk.stickermaker.R;
public class SplashScreenActivity extends AppCompatActivity {
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash_screen);
        handler = new Handler();
        handler.postDelayed(() -> {
            Intent intent = new Intent(SplashScreenActivity.this, RequestPermissionActivity.class);
            startActivity(intent);
            finish();
        }, 2500);
    }
}