package jacobmahoney.guardianofearth;

import android.graphics.RectF;

public class Particle extends RectF {

    private int dx, dy;
    private final int WIDTH = 10;
    private final int HEIGHT = 10;

    public Particle(int x, int y, int dx, int dy) {

        this.dx = dx;
        this.dy = dy;
        this.left = x;
        this.top = y;
        this.right = x + WIDTH;
        this.bottom = y + HEIGHT;

    }

    public void update() {
        this.left += dx;
        this.right += dx;
        this.top += dy;
        this.bottom += dy;
    }

    public boolean offscreen(int screenWidth, int screenHeight) {

        if (right < 0 || left > screenWidth || bottom < 0 || top > screenHeight) {
            return true;
        }

        return false;

    }

}
