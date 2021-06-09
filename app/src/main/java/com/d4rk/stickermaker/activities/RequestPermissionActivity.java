package com.d4rk.stickermaker.activities;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Toast;
import com.d4rk.stickermaker.R;
import com.d4rk.stickermaker.utils.FileUtils;
import com.d4rk.stickermaker.utils.RequestPermissionsHelper;
public class RequestPermissionActivity extends AppCompatActivity {
    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_permission);
        FileUtils.initializeDirectories(this);
        if (RequestPermissionsHelper.verifyPermissions(this)) {
            startActivity(new Intent(this, MainActivity.class));
            this.finish();
        } else {
            RequestPermissionsHelper.requestPermissions(this);
        }
        findViewById(R.id.grant_permissions_button).setOnClickListener(v -> RequestPermissionsHelper.requestPermissions(this));
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        FileUtils.initializeDirectories(this);
        if (RequestPermissionsHelper.verifyPermissions(this)) {
            startActivity(new Intent(this, MainActivity.class));
        } else {
            Toast.makeText(this, "We need access to write and read files in your phone", Toast.LENGTH_SHORT).show();
        }
    }
}
