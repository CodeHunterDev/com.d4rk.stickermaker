package com.d4rk.stickermaker.activities;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.airbnb.lottie.LottieAnimationView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.sangcomz.fishbun.FishBun;
import com.sangcomz.fishbun.adapter.image.impl.GlideAdapter;
import com.sangcomz.fishbun.define.Define;
import com.d4rk.stickermaker.R;
import com.d4rk.stickermaker.constants.Constants;
import com.d4rk.stickermaker.utils.FileUtils;
import com.d4rk.stickermaker.utils.StickerPacksManager;
import com.d4rk.stickermaker.whatsapp_api.Sticker;
import com.d4rk.stickermaker.whatsapp_api.StickerContentProvider;
import com.d4rk.stickermaker.whatsapp_api.StickerPack;
import com.d4rk.stickermaker.whatsapp_api.StickerPackDetailsActivity;
import androidx.appcompat.widget.Toolbar;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
public class NewStickerPackActivity extends AppCompatActivity {
    ImageAdapter imageAdapter;
    EditText nameEdit;
    EditText authorEdit;
    LottieAnimationView empty;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_sticker_pack);
        Toolbar tool=findViewById(R.id.toolbar1);
        tool.setTitle("Sticker");
        setSupportActionBar(tool);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        nameEdit = findViewById(R.id.sticker_pack_name_edit);
        empty=findViewById(R.id.animation_view);
        authorEdit = findViewById(R.id.sticker_pack_author_edit);
        FrameLayout btnCreate = findViewById(R.id.btn_create_pack);
        btnCreate.setOnClickListener(v -> {
            empty.setVisibility(View.GONE);
            FishBun.with(NewStickerPackActivity.this)
                    .setImageAdapter(new GlideAdapter())
                    .setMaxCount(30)
                    .exceptGif(true)
                    .setActionBarColor(Color.parseColor("#fead00"), Color.parseColor("#fead00"), false)
                    .setMinCount(3).setActionBarTitleColor(Color.parseColor("#ffffff"))
                    .startAlbum();
        });
        GridView gridview = findViewById(R.id.sticker_pack_grid_images_preview);
        imageAdapter = new ImageAdapter(this);
        gridview.setAdapter(imageAdapter);
        gridview.setOnItemClickListener((parent, v, position, id) -> {
            Toast.makeText(NewStickerPackActivity.this, "Image removed", Toast.LENGTH_SHORT).show();
            imageAdapter.uries.remove(position);
            imageAdapter.notifyDataSetChanged();
        });
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_new_sticker_pack, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.save_sticker_pack) {
            if (validateValues()) {
                Toast.makeText(this, "You have to fill all empty spaces", Toast.LENGTH_SHORT).show();
            } else {
                saveStickerPack(imageAdapter.uries, nameEdit.getText().toString(), authorEdit.getText().toString());
            }
        }
        return super.onOptionsItemSelected(item);
    }
    private boolean validateValues() {
        return nameEdit.getText().toString().trim().length() == 0 || authorEdit.getText().toString().trim().length() == 0 || imageAdapter.uries.size() == 0;
    }
    private void saveStickerPack(List<Uri> uries, String name, String author) {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Wait a moment while we process your stickers...");
        progressDialog.setTitle("Processing images");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        progressDialog.setCancelable(false);
        new Thread(() -> {
            try {
                Intent intent = new Intent(NewStickerPackActivity.this, StickerPackDetailsActivity.class);
                intent.putExtra(StickerPackDetailsActivity.EXTRA_SHOW_UP_BUTTON, true);
                String identifier = "." + FileUtils.generateRandomIdentifier();
                StickerPack stickerPack = new StickerPack(identifier, name, author, Objects.requireNonNull(uries.toArray())[0].toString(), "", "", "", "");
                List<Sticker> stickerList = StickerPacksManager.saveStickerPackFilesLocally(stickerPack.identifier, uries, NewStickerPackActivity.this);
                stickerPack.setStickers(stickerList);
                String stickerPath = Constants.STICKERS_DIRECTORY_PATH + identifier;
                String trayIconFile = FileUtils.generateRandomIdentifier() + ".png";
                StickerPacksManager.createStickerPackTrayIconFile(uries.get(0), Uri.parse(stickerPath + "/" + trayIconFile), NewStickerPackActivity.this);
                stickerPack.trayImageFile = trayIconFile;
                StickerPacksManager.stickerPacksContainer.addStickerPack(stickerPack);
                StickerPacksManager.saveStickerPacksToJson(StickerPacksManager.stickerPacksContainer);
                insertStickerPackInContentProvider(stickerPack);
                intent.putExtra(StickerPackDetailsActivity.EXTRA_STICKER_PACK_DATA, stickerPack);
                startActivity(intent);
                NewStickerPackActivity.this.finish();
            } catch (Exception e) {
                e.printStackTrace();
            }
            progressDialog.dismiss();
        }).start();
    }
    private void insertStickerPackInContentProvider(StickerPack stickerPack) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("stickerPack", new Gson().toJson(stickerPack));
        getContentResolver().insert(StickerContentProvider.AUTHORITY_URI, contentValues);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Define.ALBUM_REQUEST_CODE) {
            ArrayList<Uri> uries = new ArrayList<>();
            if (resultCode == RESULT_OK) {
                uries = data.getParcelableArrayListExtra(Define.INTENT_PATH);
                if (uries.size() > 0) {
                    imageAdapter.uries = uries;
                    imageAdapter.notifyDataSetChanged();
                    ((TextView) findViewById(R.id.stickers_selected_textview)).setText(uries.size() + " stickers selected");
                }
            }
        }
    }
    static class ImageAdapter extends BaseAdapter {
        private Context mContext;
        public ImageAdapter(Context c) {
            mContext = c;
        }
        public int getCount() {
            return uries.size();
        }
        public Object getItem(int position) {
            return null;
        }
        public long getItemId(int position) {
            return 0;
        }
        public View getView(int position, View convertView, ViewGroup parent) {
            SimpleDraweeView imageView;
            if (convertView == null) {
                imageView = new SimpleDraweeView(mContext);
                imageView.setLayoutParams(new ViewGroup.LayoutParams(150, 150));
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                imageView.setAdjustViewBounds(true);
                imageView.setPadding(8, 8, 8, 8);
            } else {
                imageView = (SimpleDraweeView) convertView;
            }
            imageView.setImageURI(uries.get(position));
            return imageView;
        }
        ArrayList<Uri> uries = new ArrayList<>();
    }
}