package jacobmahoney.guardianofearth;

import android.graphics.Canvas;
import android.util.Log;

import java.util.Observable;
import java.util.Random;

public class MeteorShower extends Observable implements GameObject {

    private long time;
    private int minRate, maxRate, minSpeed, maxSpeed, numberOfMeteors, counter;
    private boolean running;

    public MeteorShower(int minRate, int maxRate, int minSpeed, int maxSpeed, int numberOfMeteors) {

        this.minRate = minRate;
        this.maxRate = maxRate;
        this.minSpeed = minSpeed;
        this.maxSpeed = maxSpeed;
        this.numberOfMeteors = numberOfMeteors;
        running = false;
        counter = 0;
        time = -1;

    }

    public void start() {
        running = true;
    }

    @Override
    public void update(int screenWidth, int screenHeight) {

        if (running) {

            if (time == -1) {
                time = System.currentTimeMillis();
            }

            if (counter < numberOfMeteors) {

                if (System.currentTimeMillis() > time) {

                    counter++;

                    Random r;
                    int min, max, rand;

                    double x = Math.random() * screenWidth;
                    double y = -50;

                    r = new Random();
                    min = minRate;
                    max = maxRate;
                    rand = r.nextInt(max-min) + min;
                    time += rand;

                    r = new Random();
                    min = minSpeed;
                    max = maxSpeed;
                    rand = r.nextInt(max-min) + min;
                    //Log.d("MeteorShower", "" + rand);

                    ParticleEmitter.getInstance().addParticle(new Meteor((int)x, (int)y, 0, rand));

                }

            } else {
                running = false;
                setChanged();
                notifyObservers();
            }

        }

    }

    @Override
    public void draw(Canvas canvas) {

    }

}
