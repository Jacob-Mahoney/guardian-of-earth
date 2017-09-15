package jacobmahoney.guardianofearth;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

// instead of fragments, make an activity for loading screen, main menu, game, settings, etc. because these are fullscreen views
// but since we have gamepanel class that is a surfaceholder, just make another gameactivity or something the sets content view to a new gamepanel

public class LoadingActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.loading_layout);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

    }

    public void switchToMainMenuActivity(View view) {

        Intent intent = new Intent(this, MainMenuActivity.class);
        startActivity(intent);

    }

}
