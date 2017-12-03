package jacobmahoney.guardianofearth.game;

import android.graphics.Canvas;
import android.graphics.Point;
import android.os.Handler;
import android.os.Looper;

import java.util.Observable;
import java.util.Observer;

import jacobmahoney.guardianofearth.basic_game_objects.*;
import jacobmahoney.guardianofearth.particles.Meteor;
import jacobmahoney.guardianofearth.system_game_objects.*;
import jacobmahoney.guardianofearth.activities.GameActivity;
import jacobmahoney.guardianofearth.particles.Laser;
import jacobmahoney.guardianofearth.utility.Utility;

public class GameController implements Observer {

    public enum Event {METEOR_HIT_EARTH, METEOR_DESTROYED, METEORS_DONE_EMITTING, NO_METEORS_ON_SCREEN}
    private enum Status {WAVE_IN_PROGRESS, WAVE_DONE_EMITTING, DEAD, DONE}
    private Status status;

    private SpaceshipObject spaceship;
    private Earth earth;
    private SideButton leftButton;
    private SideButton rightButton;
    private HUD hud;
    private ScreenDrawer screenDrawer;
    private ParticleHandler particleHandler;

    private int currentWaveNumber;

    private int screenWidth, screenHeight;
    private float smallTextSize, largeTextSize;

    private final int LASER_SPEED = 15;
    private static int score, lives;

    public GameController(int screenWidth, int screenHeight) {

        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

        this.smallTextSize = 0.025f * screenWidth;
        this.largeTextSize = 0.04f * screenWidth;

        spaceship = new SpaceshipObject();
        earth = new Earth();
        leftButton = new SideButton(SideButton.Side.LEFT_SIDE);
        rightButton = new SideButton(SideButton.Side.RIGHT_SIDE);
        hud = new HUD();
        screenDrawer = new ScreenDrawer(screenWidth, screenHeight);
        particleHandler = new ParticleHandler();

        particleHandler.addObserver(this);
        registerGameObjects();

        score = 0;
        lives = 3;

        currentWaveNumber = -1;
        startNextWave();

    }

    public static int getScore() {
        return score;
    }

    public static int getLives() {
        return lives;
    }

    private void newPopupText(String text, int x, int y, float textSize, int timeLength) {

        final PopupText popupText = new PopupText(text, x, y, textSize, timeLength);
        screenDrawer.registerDrawableGameObject(popupText);

        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() { // after time length, remove popuptext from screen
            @Override
            public void run() {
                screenDrawer.unRegisterDrawableGameObject(popupText);
            }
        }, timeLength);

    }

    private void startNextWave() {

        currentWaveNumber++;

        String name = "Wave " + (currentWaveNumber+1);
        int minRate = 2600-currentWaveNumber*200;
        int maxRate = 4600-currentWaveNumber*200;
        int minSpeed = currentWaveNumber+1;
        int maxSpeed = currentWaveNumber+2;
        int numberOfMeteors = currentWaveNumber+8;

        Wave newWave = new Wave(name, minRate, maxRate, minSpeed, maxSpeed, numberOfMeteors);

        newWave.addObserver(this);
        newWave.addObserver(particleHandler);
        screenDrawer.registerUpdateableGameObject(newWave);

        newWave.start();

        status = Status.WAVE_IN_PROGRESS;
        newPopupText(newWave.getName(), screenWidth/2, screenHeight/2, largeTextSize, 3000);

    }

    private void endGame(String message) {

        screenDrawer.unRegisterAll();

        newPopupText(message, screenWidth/2, screenHeight/2, largeTextSize, 3000);

        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() { // after 3 seconds, switch back to main menu
            @Override
            public void run() {
                GameActivity.switchToMainMenuActivity();
            }
        }, 3000);

    }

    private void registerGameObjects() {

        // registering game objects so they can be drawn/updated on screen using screenDrawer

        screenDrawer.registerUpdateableGameObject(spaceship);
        screenDrawer.registerDrawableGameObject(spaceship);

        screenDrawer.registerUpdateableGameObject(earth);
        screenDrawer.registerDrawableGameObject(earth);

        screenDrawer.registerUpdateableGameObject(leftButton);
        screenDrawer.registerDrawableGameObject(leftButton);

        screenDrawer.registerUpdateableGameObject(rightButton);
        screenDrawer.registerDrawableGameObject(rightButton);

        screenDrawer.registerUpdateableGameObject(particleHandler);
        screenDrawer.registerDrawableGameObject(particleHandler);

        screenDrawer.registerUpdateableGameObject(hud);
        screenDrawer.registerDrawableGameObject(hud);

    }

    private void spaceshipFire() {

        // using utility function to determine the nose of the spaceship based on current rotation of the spaceship
        Point spaceshipNose = Utility.rotateAboutPoint(spaceship.getNosePoint(), spaceship.getPivotPoint(), Math.toRadians(spaceship.getRotation()));

        // calculating x and y components of velocity for the laser based on spaceship rotation
        double rot = 90 - spaceship.getRotation();
        int dx = (int) (LASER_SPEED * Math.cos(Math.toRadians(rot)));
        int dy = -(int) (LASER_SPEED * Math.sin(Math.toRadians(rot)));

        // adding laser particle at the location of the spaceship nose
        particleHandler.addParticle(new Laser(spaceshipNose.x, spaceshipNose.y, dx, dy));

    }

    public void actionDownEvent(int x, int y) {

        // called when user presses down on the screen, x and y location passed in

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

        // called when user releases press on the screen, x and y location passed in

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

        // observer function for receiving signals from particle emitter and wave

        if (arg instanceof Event) {

            Event event = (Event) arg;

            switch (event) {
                case METEORS_DONE_EMITTING: {
                    status = Status.WAVE_DONE_EMITTING;
                    break;
                }
                case METEOR_DESTROYED: {
                    Meteor m = particleHandler.getLastDestroyedMeteor();
                    Point p = m.getLocation();
                    int pointWorth = m.getPointWorth();
                    score += pointWorth;
                    if (p != null) {
                        newPopupText("+" + pointWorth, p.x, p.y, smallTextSize, 750);
                    }
                    break;
                }
                case METEOR_HIT_EARTH: {
                    lives--;
                    if (lives == 0) {
                        status = Status.DEAD;
                        endGame("Game Over (Final score: " + score + ")");
                    }
                    break;
                }
                case NO_METEORS_ON_SCREEN: {
                    if (status == Status.WAVE_DONE_EMITTING) {
                        startNextWave();
                    }
                    break;
                }
            }

        }

    }

}
