package jacobmahoney.guardianofearth;

import android.graphics.RectF;

public abstract class Particle extends RectF {

    private int dx, dy;

    public Particle(int x, int y, int dx, int dy, int width, int height) {

        this.dx = dx;
        this.dy = dy;

        // centering particle around point
        this.left = x - width/2;
        this.top = y - height/2;
        this.right = x + width/2;
        this.bottom = y + height/2;

    }

    public void update() {
        this.left += dx;
        this.right += dx;
        this.top += dy;
        this.bottom += dy;
    }

    public abstract boolean offscreen(int screenWidth, int screenHeight);

}
