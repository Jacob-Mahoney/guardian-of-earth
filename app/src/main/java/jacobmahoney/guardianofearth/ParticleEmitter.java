package jacobmahoney.guardianofearth;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import java.util.LinkedList;
import java.util.List;

public class ParticleEmitter implements GameObject {

    private List<Particle> particles = new LinkedList<>();
    private Paint paint;
    private static ParticleEmitter instance = null;

    protected ParticleEmitter() {

        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);

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

    public void update(int screenWidth, int screenHeight) {

        for (int i = 0; i < particles.size(); i++) {
            particles.get(i).update();
            if (particles.get(i).offscreen(screenWidth, screenHeight)) {
                particles.remove(particles.get(i));
            }
        }

    }

    public void draw(Canvas canvas) {

        for (int i = 0; i < particles.size(); i++) {
            canvas.drawRect(particles.get(i), paint);
        }

    }

}
