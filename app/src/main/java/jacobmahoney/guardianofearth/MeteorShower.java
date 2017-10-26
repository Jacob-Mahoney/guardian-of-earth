package jacobmahoney.guardianofearth;

import android.graphics.Canvas;
import java.util.Random;

public class MeteorShower implements GameObject {

    long time;

    MeteorShower() {

        time = System.currentTimeMillis();

    }

    @Override
    public void update(int screenWidth, int screenHeight) {

        if (System.currentTimeMillis() > time) {

            double x = Math.random() * screenWidth;
            double y = -50;

            Random r = new Random();
            int min = 1000;
            int max = 3000;
            int rand = r.nextInt(max-min) + min;

            ParticleEmitter.getInstance().addParticle(new Particle((int)x, (int)y, 0, 10));

            time += rand;

        }

    }

    @Override
    public void draw(Canvas canvas) {

    }

}
