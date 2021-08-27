package com.d4rk.stickermaker.whatsapp_api;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import com.facebook.drawee.view.SimpleDraweeView;
import com.d4rk.stickermaker.R;
public class StickerPreviewViewHolder extends RecyclerView.ViewHolder {
    public SimpleDraweeView stickerPreviewView;
    StickerPreviewViewHolder(final View itemView) {
        super(itemView);
        stickerPreviewView = itemView.findViewById(R.id.sticker_preview);
    }
}