package com.d4rk.stickermaker.backgroundRemover;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
public class CutOut {
    public static final short CUTOUT_ACTIVITY_REQUEST_CODE = 368;
    public static final short CUTOUT_ACTIVITY_RESULT_ERROR_CODE = 3680;
    static final String CUTOUT_EXTRA_SOURCE = "CUTOUT_EXTRA_SOURCE";
    static final String CUTOUT_EXTRA_RESULT = "CUTOUT_EXTRA_RESULT";
    static final String CUTOUT_EXTRA_BORDER_COLOR = "CUTOUT_EXTRA_BORDER_COLOR";
    static final String CUTOUT_EXTRA_CROP = "CUTOUT_EXTRA_CROP";
    static final String CUTOUT_EXTRA_INTRO = "CUTOUT_EXTRA_INTRO";
    public static ActivityBuilder activity() {
        return new ActivityBuilder();
    }
    /**
     * Builder used for creating CutOut Activity by user request.
     */
    public static final class ActivityBuilder {
        @Nullable
        private Uri source;
        private boolean bordered;
        private boolean intro;
        private int borderColor = Color.WHITE;
        private ActivityBuilder() {}
        /**
         * Get {@link CutOutActivity} intent to start the activity.
         */
        private Intent getIntent(@NonNull Context context) {
            Intent intent = new Intent();
            intent.setClass(context, CutOutActivity.class);
            if (source != null) {
                intent.putExtra(CUTOUT_EXTRA_SOURCE, source);
            }
            if (bordered) {
                intent.putExtra(CUTOUT_EXTRA_BORDER_COLOR, borderColor);
            }
            intent.putExtra(CUTOUT_EXTRA_CROP, true);
            if (intro) {
                intent.putExtra(CUTOUT_EXTRA_INTRO, true);
            }
            return intent;
        }
        /**
         * By default the user can select images from camera or gallery but you can also call this method to load a pre-saved image
         *
         * @param source {@link Uri} instance of the image to be loaded
         */
        public ActivityBuilder src(Uri source) {
            this.source = source;
            return this;
        }
        /**
         * This method adds a colorWhite border around the final PNG image
         */
        public ActivityBuilder bordered() {
            this.bordered = true;
            return this;
        }
        /**
         * This method adds a border around the final PNG image
         *
         * @param borderColor The border color. You can pass any {@link Color}
         */
        public ActivityBuilder bordered(int borderColor) {
            this.borderColor = borderColor;
            return bordered();
        }

        /**
         * Shows an introduction to the activity, explaining every button usage. The intro is show only once.
         */
        public ActivityBuilder intro() {
            this.intro = true;
            return this;
        }
        /**
         * Start {@link CutOutActivity}.
         *
         * @param activity activity to receive result
         */
        public void start(@NonNull Activity activity) {
            activity.startActivityForResult(getIntent(activity), CUTOUT_ACTIVITY_REQUEST_CODE);
        }
    }
    /**
     * Reads the {@link Uri} from the result data. This Uri is the path to the saved PNG
     *
     * @param data Result data to get the Uri from
     */
    public static Uri getUri(@Nullable Intent data) {
        return data != null ? data.getParcelableExtra(CUTOUT_EXTRA_RESULT) : null;
    }
    /**
     * Gets an Exception from the result data if the {@link CutOutActivity} failed at some point
     *
     * @param data Result data to get the Exception from
     */
    public static void getError(@Nullable Intent data) {
        if (data != null) {
            data.getSerializableExtra(CUTOUT_EXTRA_RESULT);
        }
    }
}