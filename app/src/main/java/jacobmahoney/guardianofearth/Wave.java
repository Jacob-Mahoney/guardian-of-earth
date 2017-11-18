package jacobmahoney.guardianofearth;

import java.util.Observable;
import java.util.Random;

public class Wave extends Observable implements UpdateableGameObject {

    private int minRate, maxRate, minSpeed, maxSpeed, numberOfMeteors, counter;
    private String name;
    private long timeUntilStart, time;
    private boolean started;

    Wave(String name, int minRate, int maxRate, int minSpeed, int maxSpeed, int numberOfMeteors) {

        this.name = name;
        this.minRate = minRate;
        this.maxRate = maxRate;
        this.minSpeed = minSpeed;
        this.maxSpeed = maxSpeed;
        this.numberOfMeteors = numberOfMeteors;

        counter = 0;
        time = -1;
        started = false;

    }

    public void start() {
        started = true;
        timeUntilStart = System.currentTimeMillis() + 3000;
        new PopupText(name, 3000);
    }

    @Override
    public void update(int screenWidth, int screenHeight) {

        if (System.currentTimeMillis() >= timeUntilStart && started) {

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

                    ParticleEmitter.getInstance().addParticle(new Meteor((int)x, (int)y, 0, rand));

                }

            } else {
                setChanged();
                notifyObservers(GameController.Event.METEORS_DONE_EMITTING);
            }

        }

    }

}
