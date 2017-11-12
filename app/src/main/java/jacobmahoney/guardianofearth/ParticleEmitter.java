package jacobmahoney.guardianofearth;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;

public class ParticleEmitter extends Observable implements UpdateableGameObject, DrawableGameObject {

    private List<Particle> particles;
    private Paint paint;
    private static ParticleEmitter instance = null;

    private ParticleEmitter() {

        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);

        particles = new ArrayList<>();

    }

    public void removeAllParticles() {
        particles.clear();
    }

    public static ParticleEmitter getInstance() {
        if (instance == null) {
            instance = new ParticleEmitter();
        }
        return instance;
    }

    public void addParticle(Particle p) {
        particles.add(p);
    }

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

        // function called by game loop (many times a second)

        List<Particle> particlesToRemove = new LinkedList<>();

        int meteors = 0;

        Iterator<Particle> iter = particles.iterator();
        Particle p;
        while (iter.hasNext()) {
            p = iter.next();
            p.update();
            if (p instanceof Laser) {
                Particle m = checkCollisions(p); // returns the collided with meteor if there was a collision, null if not
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

}
