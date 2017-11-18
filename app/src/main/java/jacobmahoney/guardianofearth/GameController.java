package jacobmahoney.guardianofearth;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

public class GameController extends Observable implements Observer {

    private final int NUMBER_OF_WAVES = 10;

    public enum Event {METEOR_HIT_EARTH, METEOR_DESTROYED, METEORS_DONE_EMITTING, NO_METEORS_ON_SCREEN}
    private enum Status {WAVE_EMITTING, WAVE_DONE_EMITTING, DEAD, DONE}
    private Status status;

    private SpaceshipObject spaceship;
    private SideButton leftButton;
    private SideButton rightButton;
    private ScreenDrawer screenDrawer;
    private ParticleEmitter particleEmitter;

    private List<Wave> waves = new LinkedList<>();
    private int currentWave;
    private final int LASER_SPEED = 15;
    private int lives;
    private int score;

    GameController(int screenWidth, int screenHeight) {

        spaceship = new SpaceshipObject();
        leftButton = new SideButton(SideButton.SIDE.LEFT_SIDE);
        rightButton = new SideButton(SideButton.SIDE.RIGHT_SIDE);
        screenDrawer = new ScreenDrawer(screenWidth, screenHeight);
        particleEmitter = new ParticleEmitter();

        particleEmitter.addObserver(this);
        registerGameObjects();

        lives = 3;
        currentWave = -1;
        score = 0;

        //waves.clear();
        initializeWaves();
        startNextWave();

    }

    // make gamecontroller, particleemitter, and screendrawer not singletons
    // can add addparticle function and registergameobject function in gamecontroller class
    // only problem is need to figure out how to make gamecontroller signal gamepanel
    // and make gamepanel signal gameactivity when the game ends
    // gamepanel already extends surfaceview so it cant extend observable to get signal from gamecontroller

    // may run into problem making particleemitter and screendrawer not singletons
    // when having to register meteorshower in each wave (consider making wave handle the shower and discard meteorshower)
    // and also when having to add add meteor particle
    // have to make addparticle and registergameobject functions static...

    // to pause game
    // place boolean "paused" in gamepanel or screendrawer
    // and place if statement based on that boolean in update() function

    private void endGame() {

        screenDrawer.unRegisterAll();

        PopupText asdf = new PopupText("Game Over", 3000);
        screenDrawer.registerUpdateableGameObject(asdf);
        screenDrawer.registerDrawableGameObject(asdf);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                screenDrawer.unRegisterAll();
                setChanged();
                notifyObservers();
            }
        }, 3000);

    }

    private void startNextWave() {
        currentWave++;
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
            Wave wave = new Wave(this, name, 1000, 3000, 2, 4, i+6);
            wave.addObserver(this);
            screenDrawer.registerUpdateableGameObject(wave);
            waves.add(wave);
        }

    }

    public void registerUpdateableGameObject(UpdateableGameObject updateableGameObject) {
        screenDrawer.registerUpdateableGameObject(updateableGameObject);
    }

    public void registerDrawableGameObject(DrawableGameObject drawableGameObject) {
        screenDrawer.registerDrawableGameObject(drawableGameObject);
    }

    public void addParticle(Particle p) {
        particleEmitter.addParticle(p);
    }

    private void registerGameObjects() {

        screenDrawer.registerUpdateableGameObject(spaceship);
        screenDrawer.registerDrawableGameObject(spaceship);

        screenDrawer.registerUpdateableGameObject(leftButton);
        screenDrawer.registerDrawableGameObject(leftButton);

        screenDrawer.registerUpdateableGameObject(rightButton);
        screenDrawer.registerDrawableGameObject(rightButton);

        screenDrawer.registerUpdateableGameObject(particleEmitter);
        screenDrawer.registerDrawableGameObject(particleEmitter);

    }

    private void spaceshipFire() {

        // using utility function to determine the nose of the spaceship based on current rotation of the spaceship
        double rot = 90 - spaceship.getRotation();
        Point spaceshipNose = Utility.rotateAboutPoint(spaceship.getNosePoint(), spaceship.getPivotPoint(), Math.toRadians(spaceship.getRotation()));
        int dx = (int) (LASER_SPEED * Math.cos(Math.toRadians(rot)));
        int dy = -(int) (LASER_SPEED * Math.sin(Math.toRadians(rot)));

        particleEmitter.addParticle(new Laser(spaceshipNose.x, spaceshipNose.y, dx, dy));

    }

    public void actionDownEvent(int x, int y) {

        if (leftButton.contains(x, y)) {
            spaceship.rotateLeft();
            leftButton.active();
        }
        else if (rightButton.contains(x, y)) {
            spaceship.rotateRight();
            rightButton.active();
        }
        else {
            spaceshipFire();
        }

    }

    public void actionUpEvent(int x, int y) {

        if (leftButton.contains(x, y)) {
            spaceship.rotateStop();
            leftButton.inactive();
        }
        else if (rightButton.contains(x, y)) {
            spaceship.rotateStop();
            rightButton.inactive();
        }

    }

    public void updateGameObjects() {
        screenDrawer.updateGameObjects();
    }

    public void drawGameObjects(Canvas canvas) {
        screenDrawer.drawGameObjects(canvas);
    }

    @Override
    public void update(Observable observable, Object arg) {

        if (arg instanceof Event) {

            Event event = (Event) arg;

            switch (event) {
                case METEORS_DONE_EMITTING: {
                    status = Status.WAVE_DONE_EMITTING;
                    break;
                }
                case METEOR_DESTROYED: {
                    score += 10;
                    break;
                }
                case METEOR_HIT_EARTH: {
                    lives--;
                    if (lives == 0) {
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
