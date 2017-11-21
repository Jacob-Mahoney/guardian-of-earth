package jacobmahoney.guardianofearth.particles;

public class Laser extends Particle {
    
    public Laser(int x, int y, int dx, int dy) {

        super(x, y, dx, dy, 12, 12);

    }

    @Override
    public boolean offscreen(int screenWidth, int screenHeight) {

        return (right < 0 || left > screenWidth || bottom < 0);

    }

}
