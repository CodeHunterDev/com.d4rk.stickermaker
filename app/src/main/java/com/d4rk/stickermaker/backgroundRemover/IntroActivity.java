package com.d4rk.stickermaker.backgroundRemover;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;
import com.d4rk.stickermaker.R;
public class IntroActivity extends AppIntro {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addSlide(AppIntroFragment.newInstance(getString(R.string.intro_magic_title), getString(R.string.intro_magic_description), R.drawable.ic_magic_wand, getResources().getColor(R.color.colorGoogleYellow)));
        addSlide(AppIntroFragment.newInstance(getString(R.string.intro_manual_title), getString(R.string.intro_manual_description), R.drawable.ic_pencil, getResources().getColor(R.color.colorGoogleYellow)));
        addSlide(AppIntroFragment.newInstance(getString(R.string.intro_zoom_title), getString(R.string.intro_zoom_description), R.drawable.ic_search, getResources().getColor(R.color.colorGoogleYellow)));
        setBarColor(Color.parseColor("#f4b400"));
        setSeparatorColor(Color.parseColor("#f4b400"));
        showSkipButton(true);
        setProgressButtonEnabled(true);
        setVibrate(true);
        setFadeAnimation();
    }
    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        finish();
    }
    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        finish();
    }
    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
    }
}