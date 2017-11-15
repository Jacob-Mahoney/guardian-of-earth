package jacobmahoney.guardianofearth;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class MainMenuActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main_menu_layout);

        TextView txt = findViewById(R.id.title);
        txt.setTypeface(LoadingActivity.getFont());
        txt = findViewById(R.id.play_game);
        txt.setTypeface(LoadingActivity.getFont());
        txt = findViewById(R.id.settings);
        txt.setTypeface(LoadingActivity.getFont());

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

    }

    public void switchToGameActivity(View view) {

        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);

    }

}
