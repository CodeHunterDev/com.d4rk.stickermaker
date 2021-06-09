package com.d4rk.stickermaker.backgroundRemover;
import android.graphics.*;
class BitmapUtility {
    static Bitmap getResizedBitmap(Bitmap bitmap, int width, int height) {
        Bitmap background = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        float originalWidth = bitmap.getWidth();
        float originalHeight = bitmap.getHeight();
        Canvas canvas = new Canvas(background);
        float scale = width / originalWidth;
        float xTranslation = 0.0f;
        float yTranslation = (height - originalHeight * scale) / 2.0f;
        Matrix transformation = new Matrix();
        transformation.postTranslate(xTranslation, yTranslation);
        transformation.preScale(scale, scale);
        Paint paint = new Paint();
        paint.setFilterBitmap(true);
        canvas.drawBitmap(bitmap, transformation, paint);
        return background;
    }
    static Bitmap getBorderedBitmap(Bitmap image, int borderColor, int borderSize) {
        Bitmap finalImage = Bitmap.createBitmap(image.getWidth(), image.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(finalImage);
        Bitmap imageCopy = Bitmap.createScaledBitmap(image, image.getWidth() - borderSize, image.getHeight() - borderSize, true);
        Paint paint = new Paint();
        paint.setColorFilter(new PorterDuffColorFilter(borderColor, PorterDuff.Mode.SRC_ATOP));
        canvas.drawBitmap(image, 0, 0, paint);
        int width = image.getWidth();
        int height = image.getHeight();
        float centerX = (width - imageCopy.getWidth()) * 0.5f;
        float centerY = (height - imageCopy.getHeight()) * 0.5f;
        canvas.drawBitmap(imageCopy, centerX, centerY, null);
        return finalImage;
    }
}
