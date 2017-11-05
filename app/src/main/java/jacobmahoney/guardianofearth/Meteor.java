package jacobmahoney.guardianofearth;

public class Meteor extends Particle {

    Meteor(int x, int y, int dx, int dy) {

        super(x, y, dx, dy, 30, 30);

    }

    @Override
    public boolean offscreen(int screenWidth, int screenHeight) {

        return (top > screenHeight);

    }

}
