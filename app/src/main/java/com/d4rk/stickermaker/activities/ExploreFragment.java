package com.d4rk.stickermaker.activities;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.d4rk.stickermaker.R;
public class ExploreFragment extends Fragment {
    public ExploreFragment() {
    }
    // TODO: Rename and change types and number of parameters
    public static ExploreFragment newInstance(String param1, String param2) {
        return new ExploreFragment();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_explore, container, false);
    }
}