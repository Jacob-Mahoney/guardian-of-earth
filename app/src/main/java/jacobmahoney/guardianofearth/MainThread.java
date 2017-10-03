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
        Log.d("MainThread", "here");
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

        long prev = System.currentTimeMillis();

        while (running) {

            Log.d("MainThread", "" + (System.currentTimeMillis() - prev)); // this value drastically increases over time
            prev = System.currentTimeMillis();

            loops = 0;

            while (System.currentTimeMillis() > next_game_tick && loops < MAX_FRAMESKIP) {
                this.gamePanel.update();
                next_game_tick += SKIP_TICKS;
                loops++;
            }

            canvas = this.surfaceHolder.lockCanvas();
            this.gamePanel.draw(canvas);
            surfaceHolder.unlockCanvasAndPost(canvas);

        }

        /*
        int FRAMES_PER_SECOND = 25;
        int SKIP_TICKS = 1000 / FRAMES_PER_SECOND;

        long next_game_tick = System.currentTimeMillis();
        long sleep_time;

        long prev = System.currentTimeMillis();

        while (running) {

            Log.d("MainThread", "" + (System.currentTimeMillis() - prev)); // this value drastically increases over time
            prev = System.currentTimeMillis();

            this.gamePanel.update();
            canvas = this.surfaceHolder.lockCanvas(null);
            this.gamePanel.draw(canvas);
            surfaceHolder.unlockCanvasAndPost(canvas);

            next_game_tick += SKIP_TICKS;
            sleep_time = next_game_tick - System.currentTimeMillis();

            if (sleep_time >= 0) {
                try {
                    sleep(sleep_time);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
        */

    }

}
