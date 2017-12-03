package jacobmahoney.guardianofearth.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Process;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.io.IOException;

import jacobmahoney.guardianofearth.game.GamePanel;
import jacobmahoney.guardianofearth.app.MyApp;
import jacobmahoney.guardianofearth.utility.Utility;

public class GameActivity extends Activity {

    public static Bitmap SPACESHIP_BITMAP, EARTH_BITMAP, METEOR1, METEOR2, METEOR3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        try {

            SPACESHIP_BITMAP = Utility.getBitmapFromAsset(MyApp.getAppContext(), "game objects/spaceship.png");
            EARTH_BITMAP = Utility.getBitmapFromAsset(MyApp.getAppContext(), "game objects/earth.png");
            METEOR1 = Utility.getBitmapFromAsset(MyApp.getAppContext(), "game objects/meteor1.png");
            METEOR2 = Utility.getBitmapFromAsset(MyApp.getAppContext(), "game objects/meteor2.png");
            METEOR3 = Utility.getBitmapFromAsset(MyApp.getAppContext(), "game objects/meteor3.png");

            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            requestWindowFeature(Window.FEATURE_NO_TITLE);

            setContentView(new GamePanel(this)); // sets the content view of this game activity to the gamepanel

            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        } catch (IOException e) { // fatal error: in case all game assets were not loaded... kill app
            e.printStackTrace();
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            Process.killProcess(Process.myPid());
        }

    }

    // making sure status and nav bars stay hidden
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    }

    public static void switchToMainMenuActivity() {
        Intent intent = new Intent(MyApp.getAppContext(), MainMenuActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        MyApp.getAppContext().startActivity(intent);
    }

}
