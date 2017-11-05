package jacobmahoney.guardianofearth;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import java.util.Observable;
import java.util.Observer;

public class Wave extends Observable implements GameObject, Observer {

    private String name;
    private MeteorShower shower;
    private enum WaveStatus {NOT_STARTED, STARTING, IN_PROGRESS, DONE}
    private WaveStatus status;
    private float textX, textY;
    private Paint paint;
    private long timeUntilStart;

    Wave(String name, int minRate, int maxRate, int minSpeed, int maxSpeed, int numberOfMeteors) {

        this.name = name;
        shower = new MeteorShower(minRate, maxRate, minSpeed, maxSpeed, numberOfMeteors);
        ScreenDrawer.getInstance().registerGameObject(shower);
        shower.addObserver(this);
        paint = new Paint();
        status = WaveStatus.NOT_STARTED;
        timeUntilStart = System.currentTimeMillis() + 3000;

    }

    public void start() {
        status = WaveStatus.STARTING;
    }

    @Override
    public void update(Observable observable, Object arg) {

        //Log.d("Wave", arg.toString());
        status = WaveStatus.DONE;

        setChanged();
        notifyObservers("all the meteors have been emitted");

    }

    @Override
    public void update(int screenWidth, int screenHeight) {

        if (status == WaveStatus.STARTING) {
            if (System.currentTimeMillis() >= timeUntilStart) {
                shower.start();
                status = WaveStatus.IN_PROGRESS;
            } else {
                textX = screenWidth / 2;
                textY = (int) ((screenHeight / 2) - ((paint.descent() + paint.ascent()) / 2));
                paint.setColor(Color.WHITE);
                paint.setTextSize(0.04f * screenWidth);
                paint.setTextAlign(Paint.Align.CENTER);
            }
        }

    }

    @Override
    public void draw(Canvas canvas) {

        if (status == WaveStatus.STARTING) {
            if (System.currentTimeMillis() < timeUntilStart) {
                canvas.drawText(name, textX, textY, paint);
            }
        }

    }

}
