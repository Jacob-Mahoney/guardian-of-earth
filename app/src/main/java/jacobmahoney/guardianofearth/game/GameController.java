package jacobmahoney.guardianofearth.game;

import android.graphics.Canvas;
import android.graphics.Point;
import android.os.Handler;
import android.os.Looper;

import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import jacobmahoney.guardianofearth.utility.Utility;
import jacobmahoney.guardianofearth.basic_game_objects.Wave;
import jacobmahoney.guardianofearth.activities.GameActivity;
import jacobmahoney.guardianofearth.basic_game_objects.PopupText;
import jacobmahoney.guardianofearth.basic_game_objects.SideButton;
import jacobmahoney.guardianofearth.basic_game_objects.SpaceshipObject;
import jacobmahoney.guardianofearth.particles.Laser;
import jacobmahoney.guardianofearth.system_game_objects.HUD;
import jacobmahoney.guardianofearth.system_game_objects.ParticleHandler;

public class GameController implements Observer {

    private final int NUMBER_OF_WAVES = 10;

    public enum Event {METEOR_HIT_EARTH, METEOR_DESTROYED, METEORS_DONE_EMITTING, NO_METEORS_ON_SCREEN}
    private enum Status {WAVE_IN_PROGRESS, WAVE_DONE_EMITTING, DEAD, DONE}
    private Status status;

    private SpaceshipObject spaceship;
    private SideButton leftButton;
    private SideButton rightButton;
    private HUD hud;
    private ScreenDrawer screenDrawer;
    private ParticleHandler particleHandler;

    private List<Wave> waves = new LinkedList<>();
    private int currentWave;

    private int screenWidth, screenHeight;
    private float smallTextSize, largeTextSize;

    private final int LASER_SPEED = 15;
    private static int score, lives;

    GameController(int screenWidth, int screenHeight) {

        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

        this.smallTextSize = 0.025f * screenWidth;
        this.largeTextSize = 0.04f * screenWidth;

        spaceship = new SpaceshipObject();
        leftButton = new SideButton(SideButton.Side.LEFT_SIDE);
        rightButton = new SideButton(SideButton.Side.RIGHT_SIDE);
        hud = new HUD();
        screenDrawer = new ScreenDrawer(screenWidth, screenHeight);
        particleHandler = new ParticleHandler();

        particleHandler.addObserver(this);
        registerGameObjects();

        lives = 3;
        currentWave = -1;
        score = 0;

        initializeWaves();
        startNextWave();

    }

    public static int getScore() {
        return score;
    }

    public static int getLives() {
        return lives;
    }

    private void startNextWave() {

        currentWave++;
        waves.get(currentWave).start();

        status = Status.WAVE_IN_PROGRESS;
        newPopupText(waves.get(currentWave).getName(), screenWidth/2, screenHeight/2, largeTextSize, 3000);

    }

    private void newPopupText(String text, int x, int y, float textSize, int length) {
        PopupText popupText = new PopupText(text, x, y, textSize, length);
        screenDrawer.registerDrawableGameObject(popupText);
    }

    private void endGame(String message) {

        screenDrawer.unRegisterAll();

        newPopupText(message, screenWidth/2, screenHeight/2, largeTextSize, 3000);

        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                GameActivity.switchToMainMenuActivity();
            }
        }, 3000);

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
            wave.addObserver(particleHandler);
            screenDrawer.registerUpdateableGameObject(wave);
            waves.add(wave);
        }

    }

    private void registerGameObjects() {

        // registering game objects so they can be drawn/updated on screen using screenDrawer

        screenDrawer.registerUpdateableGameObject(spaceship);
        screenDrawer.registerDrawableGameObject(spaceship);

        screenDrawer.registerUpdateableGameObject(leftButton);
        screenDrawer.registerDrawableGameObject(leftButton);

        screenDrawer.registerUpdateableGameObject(rightButton);
        screenDrawer.registerDrawableGameObject(rightButton);

        screenDrawer.registerUpdateableGameObject(hud);
        screenDrawer.registerDrawableGameObject(hud);

        screenDrawer.registerUpdateableGameObject(particleHandler);
        screenDrawer.registerDrawableGameObject(particleHandler);

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
                    score += 10;
                    Point p = particleHandler.getLastDestroyedMeteorLocation();
                    if (p != null) {
                        newPopupText("+10", p.x, p.y, smallTextSize, 750);
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
                        if (currentWave+1 <= NUMBER_OF_WAVES) {
                            startNextWave();
                        } else {
                            //Log.d("GameController", "game is finished!");
                            status = Status.DONE;
                            endGame("You won! (Final score: " + score + ")");
                        }
                    }
                    break;
                }
            }

        }

    }

}
