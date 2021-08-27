package com.d4rk.stickermaker.utils;
import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
public class RequestPermissionsHelper {
    private static final int CODE_REQUEST_WRITE_READ_EXTERNAL_STORAGE = 0;
    public static boolean verifyPermissions(Context context) {
        int permissionToWriteCheck = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permissionToReadCheck = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE);
        return permissionToWriteCheck != PackageManager.PERMISSION_DENIED || permissionToReadCheck != PackageManager.PERMISSION_DENIED;
    }
    @RequiresApi(api = Build.VERSION_CODES.R)
    public static void requestPermissions(Activity context) {
        ActivityCompat.requestPermissions(context, new String[] {
                Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.MANAGE_EXTERNAL_STORAGE
        }, CODE_REQUEST_WRITE_READ_EXTERNAL_STORAGE);
    }
}