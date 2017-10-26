package jacobmahoney.guardianofearth;

import android.graphics.Canvas;

public class Wave implements GameObject {

    private String name;
    private int minRate, maxRate, minSpeed, maxSpeed, numberOfMeteors;
    private MeteorShower shower;

    Wave(String name, int minRate, int maxRate, int minSpeed, int maxSpeed, int numberOfMeteors) {

        this.name = name;
        this.minRate = minRate;
        this.maxRate = maxRate;
        this.minSpeed = minSpeed;
        this.maxSpeed = maxSpeed;
        this.numberOfMeteors = numberOfMeteors;

    }

    public void start() {

        //shower = new MeteorShower(screenWidth, screenHeight);

    }

    @Override
    public void update(int screenWidth, int screenHeight) {

        //shower.update();

    }

    @Override
    public void draw(Canvas canvas) {



    }

}
