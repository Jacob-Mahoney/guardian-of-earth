package jacobmahoney.guardianofearth.basic_game_objects;

import java.util.Observable;
import java.util.Random;

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

                    setChanged();
                    notifyObservers(new Meteor((int)x, (int)y, 0, rand));

                }

            } else {
                running = false;
                setChanged();
                notifyObservers(GameController.Event.METEORS_DONE_EMITTING);
            }

        }

    }

}
