package jacobmahoney.guardianofearth.basic_game_objects;

import android.graphics.Bitmap;
import java.util.Observable;

import jacobmahoney.guardianofearth.activities.GameActivity;
import jacobmahoney.guardianofearth.game.GameController;
import jacobmahoney.guardianofearth.interfaces.UpdateableGameObject;
import jacobmahoney.guardianofearth.particles.Meteor;
import jacobmahoney.guardianofearth.utility.Utility;

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

    // add random rotation to meteors as they fall
    // public static Bitmap RotateBitmap(Bitmap source, float angle) {
    //     Matrix matrix = new Matrix();
    //     matrix.postRotate(angle);
    //     return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    // }

    // change look of spaceship
    // look up low poly spaceship on google images

    // change look of earth
    // not sure what to

    @Override
    public void update(int screenWidth, int screenHeight) {

        if (System.currentTimeMillis() >= timeUntilStart && running) {

            if (time == -1) {
                time = System.currentTimeMillis();
            }

            if (counter < numberOfMeteors) {

                if (System.currentTimeMillis() > time) {

                    time += Utility.getRandomNumberBetweenTwoNumbers(minRate, maxRate); // setting the time for the next meteor to fall

                    float speed = Utility.getRandomNumberBetweenTwoNumbers(minSpeed*100, maxSpeed*100) / 100f; // getting random speed for meteor

                    // randomly choosing which meteor asset to use
                    int bitmapNum = Utility.getRandomNumberBetweenTwoNumbers(1, 3);
                    Bitmap bitmap;
                    if (bitmapNum == 1) {
                        bitmap = GameActivity.METEOR1;
                    } else if (bitmapNum == 2) {
                        bitmap = GameActivity.METEOR2;
                    } else {
                        bitmap = GameActivity.METEOR3;
                    }

                    // getting scale of meteor
                    float scale = Utility.getRandomNumberBetweenTwoNumbers(50, 80) / 100f;

                    // getting x coords of starting and ending location of meteor
                    double min = 0.1*screenWidth;
                    double max = 0.9*screenWidth;
                    double x1 = Utility.getRandomNumberBetweenTwoNumbers((int)min, (int)max);
                    double x2 = Utility.getRandomNumberBetweenTwoNumbers((int)min, (int)max);

                    double angle = Math.atan((x2-x1)/(screenHeight+100));
                    double dx = speed*Math.sin(angle);
                    double dy = speed*Math.cos(angle);

                    setChanged();
                    notifyObservers(new Meteor((int)x1, -100, speed, (float)dx, (float)dy, bitmap, scale)); // notifying particlehandler that a meteor needs to spawn at this location

                    counter++;

                }

            } else {
                running = false;
                setChanged();
                notifyObservers(GameController.Event.METEORS_DONE_EMITTING);
            }

        }

    }

}
