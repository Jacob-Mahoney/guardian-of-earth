package jacobmahoney.guardianofearth;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

public class GameController extends Observable implements Observer, UpdateableGameObject, DrawableGameObject {

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

    private float textX, textY;
    private Paint paint;

    private static GameController instance = null;

    private GameController() {
        paint = new Paint();
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

    // to pause game
    // place boolean "paused" in gamepanel or screendrawer
    // and place if statement based on that boolean in update() function

    private void endGame() {

        ScreenDrawer.getInstance().unRegisterAll();

        ScreenDrawer.getInstance().registerUpdateableGameObject(this);
        ScreenDrawer.getInstance().registerDrawableGameObject(this);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                ScreenDrawer.getInstance().unRegisterAll();
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

        // using utility function to determine the nose of the spaceship based on current rotation of the spaceship
        double rot = 90 - spaceship.getRotation();
        Point spaceshipNose = Utility.rotateAboutPoint(spaceship.getNosePoint(), spaceship.getPivotPoint(), Math.toRadians(spaceship.getRotation()));
        int dx = (int) (LASER_SPEED * Math.cos(Math.toRadians(rot)));
        int dy = -(int) (LASER_SPEED * Math.sin(Math.toRadians(rot)));

        ParticleEmitter.getInstance().addParticle(new Laser(spaceshipNose.x, spaceshipNose.y, dx, dy));

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
    public void update(int screenWidth, int screenHeight) {
        textX = screenWidth / 2;
        textY = (int) ((screenHeight / 2) - ((paint.descent() + paint.ascent()) / 2));
        paint.setColor(Color.WHITE);
        paint.setTextSize(0.04f * screenWidth);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTypeface(LoadingActivity.getFont());
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawText("Game Over", textX, textY, paint);
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
