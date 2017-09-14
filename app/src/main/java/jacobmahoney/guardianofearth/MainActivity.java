package jacobmahoney.guardianofearth;

import android.app.Activity;
import android.os.Bundle;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        LoadingFragment fragment = new LoadingFragment();
        ft.replace(android.R.id.content, fragment);
        ft.commit();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                DifferentFragment fragment = new DifferentFragment();
                ft.setCustomAnimations(R.animator.fade_in, R.animator.fade_out);
                ft.replace(android.R.id.content, fragment);
                ft.commit();
            }
        }, 2000);

    }

    public void switchContent(View view) {

        setContentView(new GamePanel(this));

    }

}
