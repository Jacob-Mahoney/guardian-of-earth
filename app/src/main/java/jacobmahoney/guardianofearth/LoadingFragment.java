package jacobmahoney.guardianofearth;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class LoadingFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*
        Typeface tf = Typeface.createFromAsset(getAssets(),"fonts/font.ttf");
        TextView textView = findViewById(R.id.loading_text);
        textView.setTypeface(tf);
        */
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.loading_fragment, container, false);
    }

}
