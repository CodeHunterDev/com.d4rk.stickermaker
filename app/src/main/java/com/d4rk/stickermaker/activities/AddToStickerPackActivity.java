package com.d4rk.stickermaker.activities;
import android.net.Uri;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;
import com.d4rk.stickermaker.R;
import com.d4rk.stickermaker.identities.StickerPacksContainer;
import com.d4rk.stickermaker.utils.StickerPacksManager;
public class AddToStickerPackActivity extends AppCompatActivity {
    Uri stickerUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_sticker_pack);
        this.stickerUri = this.getIntent().getData();
        StickerPacksManager.stickerPacksContainer = new StickerPacksContainer("", "", StickerPacksManager.getStickerPacks(this));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Toast.makeText(this, stickerUri.getPath(), Toast.LENGTH_LONG).show();
    }
}