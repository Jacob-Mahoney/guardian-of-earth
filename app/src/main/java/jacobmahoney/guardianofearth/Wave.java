package jacobmahoney.guardianofearth;

import android.graphics.Canvas;

public class Wave implements GameObject {

    private String name;
    private int minRate, maxRate, minSpeed, maxSpeed, numberOfMeteors;
    private MeteorShower shower;
    private ParticleEmitter emitter;

    Wave(String name, int minRate, int maxRate, int minSpeed, int maxSpeed, int numberOfMeteors, ParticleEmitter emitter) {

        this.name = name;
        this.minRate = minRate;
        this.maxRate = maxRate;
        this.minSpeed = minSpeed;
        this.maxSpeed = maxSpeed;
        this.numberOfMeteors = numberOfMeteors;
        this.emitter = emitter;
        shower = new MeteorShower();

    }

    public void start() {



    }

    @Override
    public void draw(Canvas canvas) {



    }

    @Override
    public void update() {

    }

}
