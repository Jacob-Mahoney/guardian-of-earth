package jacobmahoney.guardianofearth;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.CopyOnWriteArrayList;

public class ParticleHandler extends Observable implements UpdateableGameObject, DrawableGameObject, Observer {

    private List<Particle> particles;
    private Paint paint;

    public ParticleHandler() {

        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);

        particles = new CopyOnWriteArrayList<>();

    }

    public void addParticle(Particle p) {
        particles.add(p);
    }

    // make function for getting the last meteor destroyed or the position of it
    // so when game controller gets that event, it can call the function
    // to display a popup text at that meteor's location

    // also could make function for getting the last meteor that hit earth
    // for the same reason

    private Particle checkCollisions(Particle laser) {

        Iterator<Particle> iter = particles.iterator();
        Particle p;
        while (iter.hasNext()) { // looping through all particles
            p = iter.next();
            if (p instanceof Meteor) { // if particle is a meteor, then see if it collides with the passed in laser
                if (p.intersect(laser)) {
                    setChanged();
                    notifyObservers(GameController.Event.METEOR_DESTROYED);
                    return p;
                }
            }
        }

        return null;

    }

    @Override
    public void update(int screenWidth, int screenHeight) {

        List<Particle> particlesToRemove = new LinkedList<>();

        int meteors = 0;

        Iterator<Particle> iter = particles.iterator();
        Particle p;
        while (iter.hasNext()) {
            p = iter.next();
            p.update();
            if (p instanceof Laser) {
                Particle m = checkCollisions(p); // returns the collided-with meteor if there was a collision, null if not
                if (m != null) {
                    particlesToRemove.add(p);
                    particlesToRemove.add(m);
                } else if (p.offscreen(screenWidth, screenHeight)) { // no collision with any meteors... still need to check if offscreen
                    particlesToRemove.add(p);
                }
            } else {
                meteors++;
                if (p.offscreen(screenWidth, screenHeight)) { // if meteor went off screen, it meant it hit the earth, so need to alert GameController
                    setChanged();
                    notifyObservers(GameController.Event.METEOR_HIT_EARTH);
                    particlesToRemove.add(p);
                }
            }
        }

        particles.removeAll(particlesToRemove);

        if (meteors == 0) {
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
