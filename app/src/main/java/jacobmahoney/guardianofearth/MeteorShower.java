package jacobmahoney.guardianofearth;

import android.graphics.Canvas;
import android.util.Log;

import java.util.Observable;
import java.util.Random;

public class MeteorShower extends Observable implements GameObject {

    long time;
    private int minRate, maxRate, minSpeed, maxSpeed, numberOfMeteors, counter;

    public MeteorShower(int minRate, int maxRate, int minSpeed, int maxSpeed, int numberOfMeteors) {

        this.minRate = minRate;
        this.maxRate = maxRate;
        this.minSpeed = minSpeed;
        this.maxSpeed = maxSpeed;
        this.numberOfMeteors = numberOfMeteors;
        counter = 0;
        time = -1;

    }

    @Override
    public void update(int screenWidth, int screenHeight) {

        if (time == -1) {
            time = System.currentTimeMillis();
        }

        if (counter < numberOfMeteors) {

            if (System.currentTimeMillis() > time) {

                Log.d("MeteorShower", String.valueOf(System.currentTimeMillis()));

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

                ParticleEmitter.getInstance().addParticle(new Particle((int)x, (int)y, 0, rand));

            }

        } else {
            setChanged();
            notifyObservers();
        }

    }

    @Override
    public void draw(Canvas canvas) {

    }

}
