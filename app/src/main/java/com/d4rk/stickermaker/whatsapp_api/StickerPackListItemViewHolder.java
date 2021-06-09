package com.d4rk.stickermaker.whatsapp_api;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.d4rk.stickermaker.R;
public class StickerPackListItemViewHolder extends RecyclerView.ViewHolder {
    public View container;
    public TextView titleView;
    public TextView publisherView;
    public TextView filesizeView;
    public ImageView addButton;
    public LinearLayout imageRowView;
    StickerPackListItemViewHolder(final View itemView) {
        super(itemView);
        container = itemView;
        titleView = itemView.findViewById(R.id.sticker_pack_title);
        publisherView = itemView.findViewById(R.id.sticker_pack_publisher);
        filesizeView = itemView.findViewById(R.id.sticker_pack_filesize);
        addButton = itemView.findViewById(R.id.add_button_on_list);
        imageRowView = itemView.findViewById(R.id.sticker_packs_list_item_image_list);
    }
}