package jacobmahoney.guardianofearth;

import android.graphics.Canvas;
import java.util.Random;

public class MeteorContainer implements GameObject {

    ParticleEmitter emitter;
    int screenWidth;
    int screenHeight;
    long time;

    MeteorContainer(int screenWidth, int screenHeight, ParticleEmitter emitter) {

        this.emitter = emitter;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        time = System.currentTimeMillis();

    }

    public void update() {

        // RUNS 56 TIMES A SECOND

        if (System.currentTimeMillis() > time) {

            //Log.d("MeteorContainer", "here");

            double x = Math.random() * screenWidth;
            double y = -50;

            Random r = new Random();
            int min = 1000;
            int max = 3000;
            int rand = r.nextInt(max-min) + min;

            emitter.addParticle(new Particle((int)x, (int)y, 0, 10));

            time += rand;

        }

    }

    @Override
    public void draw(Canvas canvas) {

    }

}
