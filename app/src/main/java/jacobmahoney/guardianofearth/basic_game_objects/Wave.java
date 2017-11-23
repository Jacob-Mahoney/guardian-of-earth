package jacobmahoney.guardianofearth.basic_game_objects;

import android.graphics.Bitmap;
import android.util.Log;

import java.util.Observable;
import java.util.Random;

import jacobmahoney.guardianofearth.activities.GameActivity;
import jacobmahoney.guardianofearth.game.GameController;
import jacobmahoney.guardianofearth.interfaces.UpdateableGameObject;
import jacobmahoney.guardianofearth.particles.Meteor;

public class Wave extends Observable implements UpdateableGameObject {

    private int minRate, maxRate, minSpeed, maxSpeed, numberOfMeteors, counter;
    public String name;
    private long timeUntilStart, time;
    private boolean running;

    public Wave(String name, int minRate, int maxRate, int minSpeed, int maxSpeed, int numberOfMeteors) {

        this.name = name;
        this.minRate = minRate;
        this.maxRate = maxRate;
        this.minSpeed = minSpeed;
        this.maxSpeed = maxSpeed;
        this.numberOfMeteors = numberOfMeteors;

        counter = 0;
        time = -1;
        running = false;

    }

    public String getName() {
        return name;
    }

    public void start() {
        running = true;
        timeUntilStart = System.currentTimeMillis() + 3000;
    }

    @Override
    public void update(int screenWidth, int screenHeight) {

        if (System.currentTimeMillis() >= timeUntilStart && running) {

            if (time == -1) {
                time = System.currentTimeMillis();
            }

            if (counter < numberOfMeteors) {

                if (System.currentTimeMillis() > time) {

                    counter++;

                    Random r = new Random();

                    // getting x and y components of random speed
                    double x = Math.random() * screenWidth;
                    int y = -50;

                    time += r.nextInt(maxRate-minRate+1) + minRate; // setting the time for the next meteor to fall

                    int speed = r.nextInt(maxSpeed-minSpeed+1) + minSpeed; // getting random speed for meteor

                    int bitmapNum = r.nextInt(3) + 1;
                    Bitmap bitmap;
                    if (bitmapNum == 1) {
                        bitmap = GameActivity.METEOR1;
                    } else if (bitmapNum == 2) {
                        bitmap = GameActivity.METEOR2;
                    } else {
                        bitmap = GameActivity.METEOR3;
                    }

                    float scale = (r.nextInt(31) + 50) / 100f;

                    Log.d("Wave", "bitmapNum: " + bitmapNum + " scale: " + scale);

                    setChanged();
                    notifyObservers(new Meteor((int)x, y, 0, speed, bitmap, scale)); // notifying particlehandler that a meteor needs to spawn at this location

                }

            } else {
                running = false;
                setChanged();
                notifyObservers(GameController.Event.METEORS_DONE_EMITTING);
            }

        }

    }

}
