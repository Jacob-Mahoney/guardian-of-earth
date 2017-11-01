package jacobmahoney.guardianofearth;

import android.graphics.Point;
import android.view.MotionEvent;

import java.util.LinkedList;
import java.util.List;

public class GameHolder {

    private SpaceshipObject spaceship;
    private LeftCircle leftCircle;
    private RightCircle rightCircle;
    private ParticleEmitter emitter;
    private Wave wave;
    private List<Wave> waves = new LinkedList<>();

    // combine leftcircle and rightcircle into one class and pass in either string "left" or "right" into it to clarify or something else

    public GameHolder() {

        spaceship = new SpaceshipObject();
        leftCircle = new LeftCircle();
        rightCircle = new RightCircle();
        emitter = ParticleEmitter.getInstance();
        wave = new Wave("Wave 1", 1000, 3000, 2, 4, 6);

        registerGameObjects();

        wave.start();

    }

    public void initializeWaves() {

        for (int i = 0; i < 10; i++) {

        }

    }

    public void registerGameObjects() {

        ScreenDrawer.getInstance().registerGameObject(spaceship);
        ScreenDrawer.getInstance().registerGameObject(leftCircle);
        ScreenDrawer.getInstance().registerGameObject(rightCircle);
        ScreenDrawer.getInstance().registerGameObject(emitter);
        ScreenDrawer.getInstance().registerGameObject(wave);

    }

    public void spaceshipFire() {

        double rot = 90 - spaceship.getRotation();
        Point p = Utility.rotateAboutPoint(spaceship.getNosePoint(), spaceship.getPivotPoint(), Math.toRadians(spaceship.getRotation()));

        emitter.addParticle(new Particle(p.x, p.y, (int)(15*Math.cos(Math.toRadians(rot))), -(int)(15*Math.sin(Math.toRadians(rot)))));

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

}
