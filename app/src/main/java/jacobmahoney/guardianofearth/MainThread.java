package jacobmahoney.guardianofearth;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

public class MainThread extends Thread {

    private SurfaceHolder surfaceHolder;
    private GamePanel gamePanel;
    private boolean running;
    public static Canvas canvas;

    public void setRunning(boolean running) {
        this.running = running;
    }

    public MainThread(SurfaceHolder surfaceHolder, GamePanel gamePanel) {
        super();
        this.surfaceHolder = surfaceHolder;
        this.gamePanel = gamePanel;
    }

    @Override
    public void run() {

        int TICKS_PER_SECOND = 50;
        int SKIP_TICKS = 1000 / TICKS_PER_SECOND;
        int MAX_FRAMESKIP = 10;

        long next_game_tick = System.currentTimeMillis();
        int loops;

        while (running) {

            loops = 0;

            while (System.currentTimeMillis() > next_game_tick && loops < MAX_FRAMESKIP) {
                this.gamePanel.update();
                next_game_tick += SKIP_TICKS;
                loops++;
            }

            if (surfaceHolder.getSurface().isValid()) {
                canvas = this.surfaceHolder.lockCanvas();
                if (canvas == null) continue; // needed to check the while condition again to prevent a null pointer exception in gamepanel
                this.gamePanel.draw(canvas);
                surfaceHolder.unlockCanvasAndPost(canvas);
            }

        }

    }

}
