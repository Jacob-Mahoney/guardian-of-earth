package jacobmahoney.guardianofearth;

import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;

import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class GameController extends Observable implements Observer {

    private final int NUMBER_OF_WAVES = 10;
    public enum Event {METEOR_HIT_EARTH, METEOR_DESTROYED, METEORS_DONE_EMITTING, NO_METEORS_ON_SCREEN}
    private enum Status {WAVE_EMITTING, WAVE_DONE_EMITTING, DEAD, DONE}
    private Status status;
    private SpaceshipObject spaceship;
    private LeftCircle leftCircle;
    private RightCircle rightCircle;
    private List<Wave> waves = new LinkedList<>();
    private int currentWave;
    private final int LASER_SPEED = 15;
    private int lives;

    private static GameController instance = null;

    private GameController() {
        Log.d("GameController", "here bruh");
    }

    public static GameController getInstance() {
        if (instance == null) {
            instance = new GameController();
        }
        return instance;
    }

    public void startNewGame() {

        spaceship = new SpaceshipObject();
        leftCircle = new LeftCircle();
        rightCircle = new RightCircle();
        ParticleEmitter.getInstance().removeAllParticles();
        ParticleEmitter.getInstance().addObserver(this);
        registerGameObjects();

        lives = 3;
        currentWave = -1;

        waves.clear();
        initializeWaves();
        startNextWave();

    }

    private void endGame() {

        ScreenDrawer.getInstance().unRegisterAll();

        //ParticleEmitter.getInstance().removeAllParticles();

        setChanged();
        notifyObservers();

    }

    private void startNextWave() {
        currentWave++;
        Log.d("GameController", "starting wave " + currentWave + "/" + NUMBER_OF_WAVES);
        waves.get(currentWave).start();
        status = Status.WAVE_EMITTING;
    }

    private void initializeWaves() {

        // String name, int minRate, int maxRate, int minSpeed, int maxSpeed, int numberOfMeteors

        for (int i = 0; i < NUMBER_OF_WAVES; i++) {
            String name = "Wave " + (i+1);
            if (i+1 == NUMBER_OF_WAVES) {
                name += " (last wave)";
            }
            Wave wave = new Wave(name, 1000, 3000, 2, 4, i+6);
            wave.addObserver(this);
            ScreenDrawer.getInstance().registerUpdateableGameObject(wave);
            ScreenDrawer.getInstance().registerDrawableGameObject(wave);
            waves.add(wave);
        }

    }

    private void registerGameObjects() {

        ScreenDrawer.getInstance().registerUpdateableGameObject(spaceship);
        ScreenDrawer.getInstance().registerDrawableGameObject(spaceship);

        ScreenDrawer.getInstance().registerUpdateableGameObject(leftCircle);
        ScreenDrawer.getInstance().registerDrawableGameObject(leftCircle);

        ScreenDrawer.getInstance().registerUpdateableGameObject(rightCircle);
        ScreenDrawer.getInstance().registerDrawableGameObject(rightCircle);

        ScreenDrawer.getInstance().registerUpdateableGameObject(ParticleEmitter.getInstance());
        ScreenDrawer.getInstance().registerDrawableGameObject(ParticleEmitter.getInstance());

    }

    private void spaceshipFire() {

        double rot = 90 - spaceship.getRotation();
        Point p = Utility.rotateAboutPoint(spaceship.getNosePoint(), spaceship.getPivotPoint(), Math.toRadians(spaceship.getRotation()));

        ParticleEmitter.getInstance().addParticle(new Laser(p.x, p.y, (int)(LASER_SPEED*Math.cos(Math.toRadians(rot))), -(int)(LASER_SPEED*Math.sin(Math.toRadians(rot)))));

    }

    public void actionDownEvent(int x, int y) {

        if (leftCircle.contains(x, y)) {
            spaceship.rotateLeft();
            leftCircle.active();
        }
        else if (rightCircle.contains(x, y)) {
            spaceship.rotateRight();
            rightCircle.active();
        }
        else {
            spaceshipFire();
        }

    }

    public void actionUpEvent(int x, int y) {

        if (leftCircle.contains(x, y)) {
            spaceship.rotateStop();
            leftCircle.inactive();
        }
        else if (rightCircle.contains(x, y)) {
            spaceship.rotateStop();
            rightCircle.inactive();
        }

    }

    @Override
    public void update(Observable observable, Object arg) {

        if (arg instanceof Event) {

            Event event = (Event) arg;

            switch (event) {
                case METEORS_DONE_EMITTING: {
                    status = Status.WAVE_DONE_EMITTING;
                    Log.d("GameController", "done emitting");
                    break;
                }
                case METEOR_DESTROYED: {
                    Log.d("GameController", "meteor destroyed");
                    break;
                }
                case METEOR_HIT_EARTH: {
                    lives--;
                    if (lives == 0) {
                        Log.d("GameController", "oh dear, you're dead");
                        status = Status.DEAD;
                        endGame();
                    }
                    break;
                }
                case NO_METEORS_ON_SCREEN: {
                    if (status == Status.WAVE_DONE_EMITTING) {
                        if (currentWave+1 <= NUMBER_OF_WAVES) {
                            startNextWave();
                        } else {
                            Log.d("GameController", "game is finished!");
                            status = Status.DONE;
                            endGame();
                        }
                    }
                    break;
                }
            }

        }

    }

}
