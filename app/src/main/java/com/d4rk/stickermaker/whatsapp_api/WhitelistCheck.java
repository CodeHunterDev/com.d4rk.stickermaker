package com.d4rk.stickermaker.whatsapp_api;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.net.Uri;
import androidx.annotation.NonNull;
import com.d4rk.stickermaker.BuildConfig;
public class WhitelistCheck {
    private static final String AUTHORITY_QUERY_PARAM = "authority";
    private static final String IDENTIFIER_QUERY_PARAM = "identifier";
    private static final String STICKER_APP_AUTHORITY = BuildConfig.CONTENT_PROVIDER_AUTHORITY;
    public static String CONSUMER_WHATSAPP_PACKAGE_NAME = "com.whatsapp";
    public static String SMB_WHATSAPP_PACKAGE_NAME = "com.whatsapp.w4b";
    private static final String CONTENT_PROVIDER = ".provider.sticker_whitelist_check";
    private static final String QUERY_PATH = "is_whitelisted";
    private static final String QUERY_RESULT_COLUMN_NAME = "result";
    public static boolean isWhitelisted(@NonNull Context context, @NonNull String identifier) {
        try {
            boolean consumerResult = isWhitelistedFromProvider(context, identifier, CONSUMER_WHATSAPP_PACKAGE_NAME);
            boolean smbResult = isWhitelistedFromProvider(context, identifier, SMB_WHATSAPP_PACKAGE_NAME);
            return consumerResult && smbResult;
        } catch (Exception e) {
            return false;
        }
    }
    private static boolean isWhitelistedFromProvider(@NonNull Context context, @NonNull String identifier, String whatsappPackageName) {
        final PackageManager packageManager = context.getPackageManager();
        if (isPackageInstalled(whatsappPackageName, packageManager)) {
            final String whatsappProviderAuthority = whatsappPackageName + CONTENT_PROVIDER;
            final ProviderInfo providerInfo = packageManager.resolveContentProvider(whatsappProviderAuthority, PackageManager.GET_META_DATA);
            if (providerInfo == null) {
                return false;
            }
            final Uri queryUri = new Uri.Builder().scheme(ContentResolver.SCHEME_CONTENT).authority(whatsappProviderAuthority).appendPath(QUERY_PATH).appendQueryParameter(AUTHORITY_QUERY_PARAM, STICKER_APP_AUTHORITY).appendQueryParameter(IDENTIFIER_QUERY_PARAM, identifier).build();
            try (final Cursor cursor = context.getContentResolver().query(queryUri, null, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    final int whiteListResult = cursor.getInt(cursor.getColumnIndexOrThrow(QUERY_RESULT_COLUMN_NAME));
                    return whiteListResult == 1;
                }
            }
        } else {
            return true;
        }
        return false;
    }
    public static boolean isPackageInstalled(String packageName, PackageManager packageManager) {
        try {
            final ApplicationInfo applicationInfo = packageManager.getApplicationInfo(packageName, 0);
            if (applicationInfo != null) {
                return applicationInfo.enabled;
            } else {
                return false;
            }
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
}