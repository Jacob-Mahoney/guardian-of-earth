package jacobmahoney.guardianofearth;

public class Laser extends Particle {
    
    Laser(int x, int y, int dx, int dy) {

        super(x, y, dx, dy, 12, 12);

    }

    @Override
    public boolean offscreen(int screenWidth, int screenHeight) {

        return (right < 0 || left > screenWidth || bottom < 0);

    }

}
