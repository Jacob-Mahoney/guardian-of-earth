package jacobmahoney.guardianofearth;

import android.graphics.RectF;

public class Particle extends RectF {

    private int dx, dy;
    private final int WIDTH = 12;
    private final int HEIGHT = 12;

    public Particle(int x, int y, int dx, int dy) {

        this.dx = dx;
        this.dy = dy;

        // centering particle around point
        this.left = x - WIDTH/2;
        this.top = y - HEIGHT/2;
        this.right = x + WIDTH/2;
        this.bottom = y + HEIGHT/2;

    }

    public void update() {
        this.left += dx;
        this.right += dx;
        this.top += dy;
        this.bottom += dy;
    }

    public boolean offscreen(int screenWidth, int screenHeight) {

        /*if (right < 0 || left > screenWidth || bottom < 0 || top > screenHeight) {
            return true;
        }*/

        return false;

    }

}
