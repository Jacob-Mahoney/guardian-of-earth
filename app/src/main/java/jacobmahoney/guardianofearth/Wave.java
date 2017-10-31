package jacobmahoney.guardianofearth;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import java.util.Observable;
import java.util.Observer;

public class Wave implements GameObject, Observer {

    private String name;
    private MeteorShower shower;
    private enum WaveStatus {STARTING, IN_PROGRESS, DONE}
    private WaveStatus status;
    private float textX, textY;
    private Paint paint;
    private long timeUntilStart;

    Wave(String name, int minRate, int maxRate, int minSpeed, int maxSpeed, int numberOfMeteors) {

        this.name = name;
        shower = new MeteorShower(minRate, maxRate, minSpeed, maxSpeed, numberOfMeteors);
        shower.addObserver(this);
        paint = new Paint();
        status = WaveStatus.STARTING;
        timeUntilStart = System.currentTimeMillis() + 3000;

    }

    @Override
    public void update(Observable observable, Object arg) {

        Log.d("Wave", "done!");
        ScreenDrawer.getInstance().unRegisterGameObject(shower);

    }

    @Override
    public void update(int screenWidth, int screenHeight) {

        switch (status) {

            case STARTING: {
                if (System.currentTimeMillis() >= timeUntilStart) {
                    ScreenDrawer.getInstance().registerGameObject(shower);
                    status = WaveStatus.IN_PROGRESS;
                } else {
                    textX = screenWidth / 2;
                    textY = (int) ((screenHeight / 2) - ((paint.descent() + paint.ascent()) / 2));
                    paint.setColor(Color.WHITE);
                    paint.setTextSize(0.04f * screenWidth);
                    paint.setTextAlign(Paint.Align.CENTER);
                }
                break;
            }

        }

    }

    @Override
    public void draw(Canvas canvas) {

        switch (status) {

            case STARTING: {
                if (System.currentTimeMillis() < timeUntilStart) {
                    canvas.drawText(name, textX, textY, paint);
                }
                break;
            }

        }

    }

}
