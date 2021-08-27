package com.d4rk.stickermaker.activities;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.d4rk.stickermaker.R;
import com.d4rk.stickermaker.backgroundRemover.CutOut;
import com.d4rk.stickermaker.identities.StickerPacksContainer;
import com.d4rk.stickermaker.utils.StickerPacksManager;
import com.d4rk.stickermaker.whatsapp_api.AddStickerPackActivity;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sangcomz.fishbun.define.Define;
import java.util.List;
import java.util.Objects;
public class MainActivity extends AddStickerPackActivity implements CheckRefreshClickListener {
    private MyStickersFragment myStickersFragment;
    private ExploreFragment exploreFragment;
    private CreateFragment createFragment;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomAppBar bottomAppBar = findViewById(R.id.bottom_app_bar);
        setSupportActionBar(bottomAppBar);
        Fresco.initialize(this);
        this.setupFragments();
        setFragmento(myStickersFragment);
        StickerPacksManager.stickerPacksContainer = new StickerPacksContainer("", "", StickerPacksManager.getStickerPacks(this));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.cusotmcreate, null));
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, NewStickerPackActivity.class);
            startActivity(intent);
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.bottom_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CutOut.CUTOUT_ACTIVITY_REQUEST_CODE || requestCode == Define.ALBUM_REQUEST_CODE) {
            Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.frame_principal);
            Objects.requireNonNull(fragment).onActivityResult(requestCode, resultCode, data);
        }
    }
    private void setupFragments() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        myStickersFragment = new MyStickersFragment();
        exploreFragment = new ExploreFragment();
        createFragment = new CreateFragment();
        fragmentTransaction.add(R.id.frame_principal, myStickersFragment);
        fragmentTransaction.add(R.id.frame_principal, exploreFragment);
        fragmentTransaction.add(R.id.frame_principal, createFragment);
        fragmentTransaction.hide(exploreFragment);
        fragmentTransaction.hide(createFragment);
        fragmentTransaction.commit();
    }
    private void setFragmento(Fragment fragmento) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        if (fragmento == myStickersFragment) {
            fragmentTransaction.hide(exploreFragment);
            fragmentTransaction.hide(createFragment);
        } else if (fragmento == exploreFragment) {
            fragmentTransaction.hide(myStickersFragment);
            fragmentTransaction.hide(createFragment);
        } else if (fragmento == createFragment) {
            fragmentTransaction.hide(myStickersFragment);
            fragmentTransaction.hide(exploreFragment);
        }
        fragmentTransaction.show(fragmento);
        fragmentTransaction.commit();
    }
    @Override
    public void onBackPressed() {}
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            finish();
            System.exit(0);
        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuItem = item.getItemId();
        if (menuItem == android.R.id.home) {
            ShowRoundDialogFragment showRoundDialogFragment = new ShowRoundDialogFragment();
            showRoundDialogFragment.show(getSupportFragmentManager(),
                    "add");
        }
        if (menuItem == R.id.share) {
            try {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_SUBJECT, "Sticker Central");
                String sAux = "\nDownload Sticker Maker for creating 100% ad free awesome WhatsApp stickers.\n\n";
                sAux = sAux + "https://play.google.com/store/apps/details?id=com.d4rk.stickermaker";
                i.putExtra(Intent.EXTRA_TEXT, sAux);
                startActivity(Intent.createChooser(i, "choose one"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (menuItem == R.id.about) {
            AboutDialogFragment ab = new AboutDialogFragment();
            ab.show(getSupportFragmentManager(), "SHOWN");
        }
        if (menuItem == R.id.support) {
            String appId = this.getPackageName();
            Intent rateIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=" + appId));
            boolean marketFound = false;
            final List < ResolveInfo > otherApps = this.getPackageManager()
                    .queryIntentActivities(rateIntent, 0);
            for (ResolveInfo otherApp: otherApps) {
                if (otherApp.activityInfo.applicationInfo.packageName
                        .equals("com.android.vending")) {
                    ActivityInfo otherAppActivity = otherApp.activityInfo;
                    ComponentName componentName = new ComponentName(
                            otherAppActivity.applicationInfo.packageName,
                            otherAppActivity.name
                    );
                    rateIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    rateIntent.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                    rateIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    rateIntent.setComponent(componentName);
                    this.startActivity(rateIntent);
                    marketFound = true;
                    break;
                }
            }
            if (!marketFound) {
                Intent webIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/details?id=" + appId));
                this.startActivity(webIntent);
            }
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onLicenseClick() {
        openPlay("com.d4rk.cleaner");
    }
    public void openPlay(String mark) {
        Intent i;
        PackageManager manager = getPackageManager();
        try {
            i = manager.getLaunchIntentForPackage(mark);
            if (i == null)
                throw new PackageManager.NameNotFoundException();
            i.addCategory(Intent.CATEGORY_LAUNCHER);
            startActivity(i);
        } catch (PackageManager.NameNotFoundException e) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("market://details?id=" + mark));
            startActivity(intent);
        }
    }
    @Override
    public void OnShareClick() {
        setFragmento(createFragment);
    }
    @Override
    public void onFeedbackClick() {
        openPlay("com.d4rk.cleaner");
    }
    @Override public void onAboutClick() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("market://details?id=" + "com.d4rk.cleaner"));
        startActivity(intent);
    }
    @Override
    public void onCreateClick() {
        setFragmento(myStickersFragment);
    }
}