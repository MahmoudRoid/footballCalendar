package mahmoud.footballcalendar.Fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mahmoud.footballcalendar.R;

/**
 * Created by Mahmoud on 0001 9/1/2016.
 */
public class Fragment1 extends Fragment {

    private ViewGroup layout;

    public Fragment1() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        layout = (ViewGroup) inflater.inflate(R.layout.fragment1, container, false);
        return layout;
    }
}
