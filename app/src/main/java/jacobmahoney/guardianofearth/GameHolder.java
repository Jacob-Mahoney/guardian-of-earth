package jacobmahoney.guardianofearth;

import android.graphics.Canvas;
import android.graphics.Point;
import android.view.MotionEvent;

public class GameHolder {

    private SpaceshipObject spaceship;
    private LeftCircle leftCircle;
    private RightCircle rightCircle;
    private ParticleEmitter emitter;
    private Wave wave;

    GameHolder(int screenWidth, int screenHeight) {

        spaceship = new SpaceshipObject(screenWidth, screenHeight);
        leftCircle = new LeftCircle(screenWidth, screenHeight);
        rightCircle = new RightCircle(screenWidth, screenHeight);
        emitter = new ParticleEmitter(screenWidth, screenHeight);
        wave = new Wave("Wave 1", 1000, 3000, 2, 4, 6, emitter);

    }

    public void spaceshipFire() {

        double rot = 90 - spaceship.getRotation();
        Point p = Utility.rotateAboutPoint(spaceship.getNosePoint(), spaceship.getPivotPoint(), Math.toRadians(spaceship.getRotation()));

        emitter.addParticle(new Particle(p.x, p.y, (int)(15*Math.cos(Math.toRadians(rot))), -(int)(15*Math.sin(Math.toRadians(rot)))));

    }

    public void handleTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (leftCircle.contains(event.getX(), event.getY())) {
                    spaceship.rotateLeft();
                    leftCircle.active();
                }
                else if (rightCircle.contains(event.getX(), event.getY())) {
                    spaceship.rotateRight();
                    rightCircle.active();
                }
                else {
                    spaceshipFire();
                }
                break;
            case MotionEvent.ACTION_UP:
                if (leftCircle.contains(event.getX(), event.getY())) {
                    spaceship.rotateStop();
                    leftCircle.inactive();
                }
                else if (rightCircle.contains(event.getX(), event.getY())) {
                    spaceship.rotateStop();
                    rightCircle.inactive();
                }
                break;
        }
    }

    public void updateGameObjects() {

        spaceship.update();
        leftCircle.update();
        rightCircle.update();
        emitter.update();

    }

    public void drawGameObjects(Canvas canvas) {

        spaceship.draw(canvas);
        leftCircle.draw(canvas);
        rightCircle.draw(canvas);
        emitter.draw(canvas);

    }

}
