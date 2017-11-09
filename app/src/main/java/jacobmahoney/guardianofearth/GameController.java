package jacobmahoney.guardianofearth;

import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;

import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class GameController implements Observer {

    private SpaceshipObject spaceship;
    private LeftCircle leftCircle;
    private RightCircle rightCircle;
    private ParticleEmitter emitter;
    private Wave wave;
    private List<Wave> waves = new LinkedList<>();
    private final int LASER_SPEED = 15;

    public GameController() {

        spaceship = new SpaceshipObject();
        leftCircle = new LeftCircle();
        rightCircle = new RightCircle();
        emitter = ParticleEmitter.getInstance();
        emitter.addObserver(this);
        wave = new Wave("Wave 1", 1000, 3000, 2, 4, 6);
        wave.addObserver(this);

        registerGameObjects();

        wave.start();

    }

    // need to make unified enum in here maybe to handle the game status at all times to handle properly observer updates
    // also need to try "deleting" a laser and meteor if collided in checkCollisions method and see if that messes up the update() function
    // vv need to do this function right on down here brother vv

    public void initializeWaves() {

        for (int i = 0; i < 10; i++) {

        }

    }

    public void registerGameObjects() {

        ScreenDrawer.getInstance().registerUpdateableGameObject(spaceship);
        ScreenDrawer.getInstance().registerDrawableGameObject(spaceship);

        ScreenDrawer.getInstance().registerUpdateableGameObject(leftCircle);
        ScreenDrawer.getInstance().registerDrawableGameObject(leftCircle);

        ScreenDrawer.getInstance().registerUpdateableGameObject(rightCircle);
        ScreenDrawer.getInstance().registerDrawableGameObject(rightCircle);

        ScreenDrawer.getInstance().registerUpdateableGameObject(emitter);
        ScreenDrawer.getInstance().registerDrawableGameObject(emitter);

        ScreenDrawer.getInstance().registerUpdateableGameObject(wave);
        ScreenDrawer.getInstance().registerDrawableGameObject(wave);

    }

    public void spaceshipFire() {

        double rot = 90 - spaceship.getRotation();
        Point p = Utility.rotateAboutPoint(spaceship.getNosePoint(), spaceship.getPivotPoint(), Math.toRadians(spaceship.getRotation()));

        emitter.addParticle(new Laser(p.x, p.y, (int)(LASER_SPEED*Math.cos(Math.toRadians(rot))), -(int)(LASER_SPEED*Math.sin(Math.toRadians(rot)))));

    }

    public void handleTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (leftCircle.contains((int)event.getX(), (int)event.getY())) {
                    spaceship.rotateLeft();
                    leftCircle.active();
                }
                else if (rightCircle.contains((int)event.getX(), (int)event.getY())) {
                    spaceship.rotateRight();
                    rightCircle.active();
                }
                else {
                    spaceshipFire();
                }
                break;
            case MotionEvent.ACTION_UP:
                if (leftCircle.contains((int)event.getX(), (int)event.getY())) {
                    spaceship.rotateStop();
                    leftCircle.inactive();
                }
                else if (rightCircle.contains((int)event.getX(), (int)event.getY())) {
                    spaceship.rotateStop();
                    rightCircle.inactive();
                }
                break;
        }
    }

    @Override
    public void update(Observable observable, Object arg) {

        Log.d("GameController", observable.getClass().toString() + ": " + arg.toString());

    }

}
