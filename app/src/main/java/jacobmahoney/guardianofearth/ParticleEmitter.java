package jacobmahoney.guardianofearth;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;

public class ParticleEmitter extends Observable implements GameObject {

    private List<Particle> particles;
    private List<Particle> meteors;
    private Paint paint;
    private static ParticleEmitter instance = null;

    protected ParticleEmitter() {

        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);

        particles = new LinkedList<>();
        meteors = new LinkedList<>();

    }

    public static ParticleEmitter getInstance() {
        if (instance == null) {
            instance = new ParticleEmitter();
        }
        return instance;
    }

    public void addParticle(Particle p) {
        particles.add(p);
        if (p instanceof Meteor) {
            meteors.add(p);
        }
    }

    public void checkCollisions(Particle laser) {

        for (int i = 0; i < meteors.size(); i++) {
            if (meteors.get(i).contains(laser)) {
                setChanged();
                notifyObservers("you hit a meteor!");
                break;
            }
        }

    }

    public void update(int screenWidth, int screenHeight) {

        int meteors = 0;

        Iterator<Particle> i = particles.iterator();
        Particle p;
        while (i.hasNext()) {
            p = i.next();
            p.update();
            if (p instanceof Meteor) {
                meteors++;
            } else {
                checkCollisions(p);
            }
            if (p.offscreen(screenWidth, screenHeight)) {
                i.remove();
                if (p instanceof Meteor) {
                    setChanged();
                    notifyObservers("meteor hit earth");
                }
            }
        }

        if (meteors == 0) {
            //setChanged();
            //notifyObservers("no meteors on screen");
        }

    }

    public void draw(Canvas canvas) {
        for (int i = 0; i < particles.size(); i++) {
            canvas.drawRect(particles.get(i), paint);
        }
    }

}
