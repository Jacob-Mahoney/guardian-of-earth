package jacobmahoney.guardianofearth.particles;

public class Meteor extends Particle {

    public Meteor(int x, int y, int dx, int dy) {

        super(x, y, dx, dy, 50, 50);

    }

    @Override
    public boolean offscreen(int screenWidth, int screenHeight) {

        return (top > screenHeight);

    }

}
