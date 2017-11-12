package jacobmahoney.guardianofearth;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.util.Observable;
import java.util.Observer;

public class GameActivity extends Activity implements Observer {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/SquareFont.ttf");
        setContentView(new GamePanel(this, font)); // sets the content view of this game activity to the gamepanel

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        GameController.getInstance().addObserver(this);

    }

    public void switchToMainMenuActivity() {
        Intent intent = new Intent(this, MainMenuActivity.class);
        startActivity(intent);
    }

    @Override
    public void update(Observable observable, Object o) {
        switchToMainMenuActivity();
    }

}
