package jacobmahoney.guardianofearth;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {

    private MainThread thread;
    private int screenWidth;
    private int screenHeight;
    private GameHolder gameHolder;

    public GamePanel(Context context) {

        super(context);
        getHolder().addCallback(this);
        setFocusable(true);
        getScreenSize(context);
        gameHolder = new GameHolder(screenWidth, screenHeight);

    }

    public void getScreenSize(Context context) {

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getRealSize(size);
        screenWidth = size.x;
        screenHeight = size.y;

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread = new MainThread(getHolder(), this);
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while (retry) {
            try {
                thread.setRunning(false);
                thread.join();
            } catch(Exception e) {
                e.printStackTrace();
            }
            retry = false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        gameHolder.handleTouchEvent(event);
        return true;

    }

    public void update() {

        gameHolder.updateGameObjects();

    }

    @Override
    public void draw(Canvas canvas) {

        super.draw(canvas);

        gameHolder.drawGameObjects(canvas);

    }

}
