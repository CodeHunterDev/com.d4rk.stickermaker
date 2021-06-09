package com.d4rk.stickermaker.whatsapp_api;
import android.content.Context;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import android.util.AttributeSet;
public class
BottomFadingRecyclerView extends RecyclerView {
    public BottomFadingRecyclerView(Context context) {
        super(context);
    }
    public BottomFadingRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
    public BottomFadingRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    @Override
    protected float getTopFadingEdgeStrength() {
        return 0.0f;
    }
}