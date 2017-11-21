package jacobmahoney.guardianofearth;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.CopyOnWriteArrayList;

public class ParticleHandler extends Observable implements UpdateableGameObject, DrawableGameObject, Observer {

    private List<Particle> particles;
    private List<Meteor> meteors;
    private Paint paint;
    private Point lastDestroyedMeteorLocation;

    public ParticleHandler() {

        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
        lastDestroyedMeteorLocation = null;

        particles = new CopyOnWriteArrayList<>();
        meteors = new CopyOnWriteArrayList<>();

    }

    public void addParticle(Particle p) {
        particles.add(p);
        if (p instanceof Meteor) { // if particle is a meteor, also add it to meteors list
            meteors.add((Meteor) p);
        }
    }

    public Point getLastDestroyedMeteorLocation() {
        return lastDestroyedMeteorLocation;
    }

    public void setLastDestroyedMeteorLocation(int x, int y) {
        if (lastDestroyedMeteorLocation == null) {
            lastDestroyedMeteorLocation = new Point();
        }
        lastDestroyedMeteorLocation.set(x, y);
    }

    private Meteor checkCollisions(Particle laser) {

        Iterator<Meteor> iter = meteors.iterator();
        Meteor m;
        while (iter.hasNext()) { // looping through all meteors
            m = iter.next();
            if (m.intersect(laser)) {
                setLastDestroyedMeteorLocation(m.getX(), m.getY());
                setChanged();
                notifyObservers(GameController.Event.METEOR_DESTROYED);
                return m;
            }
        }

        return null;

    }

    @Override
    public void update(int screenWidth, int screenHeight) {

        List<Particle> particlesToRemove = new LinkedList<>();
        List<Meteor> meteorsToRemove = new LinkedList<>();

        Iterator<Particle> iter = particles.iterator();
        Particle p;
        while (iter.hasNext()) {
            p = iter.next();
            p.update();
            if (p instanceof Laser) {
                Meteor m = checkCollisions(p); // returns the collided-with meteor if there was a collision, null if not
                if (m != null) {
                    particlesToRemove.add(p);
                    particlesToRemove.add(m);
                    meteorsToRemove.add(m);
                } else if (p.offscreen(screenWidth, screenHeight)) { // no collision with any meteors... still need to check if offscreen
                    particlesToRemove.add(p);
                }
            } else {
                if (p.offscreen(screenWidth, screenHeight)) { // if meteor went off screen, it meant it hit the earth, so need to alert GameController
                    setChanged();
                    notifyObservers(GameController.Event.METEOR_HIT_EARTH);
                    particlesToRemove.add(p);
                    meteorsToRemove.add((Meteor) p);
                }
            }
        }

        particles.removeAll(particlesToRemove);
        meteors.removeAll(meteorsToRemove);

        if (meteors.size() == 0) {
            setChanged();
            notifyObservers(GameController.Event.NO_METEORS_ON_SCREEN);
        }

    }

    @Override
    public void draw(Canvas canvas) {
        for (int i = 0; i < particles.size(); i++) {
            canvas.drawRect(particles.get(i), paint);
        }
    }

    // observes signals from Wave class, basically every time this function is called, a meteor needs to be spawned
    @Override
    public void update(Observable observable, Object arg) {
        if (arg instanceof Particle) {
            addParticle((Particle) arg);
        }
    }

}
