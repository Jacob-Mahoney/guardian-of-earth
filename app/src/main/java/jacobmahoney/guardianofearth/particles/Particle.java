package jacobmahoney.guardianofearth.particles;

import android.graphics.Rect;

import jacobmahoney.guardianofearth.interfaces.DrawableGameObject;

public abstract class Particle implements DrawableGameObject {

    private int dx, dy, width, height;
    protected Rect rect;

    public Particle(int x, int y, int dx, int dy, int width, int height) {

        this.dx = dx;
        this.dy = dy;
        this.width = width;
        this.height = height;

        rect = new Rect();

        // centering particle around point
        rect.left = x - width/2;
        rect.top = y - height/2;
        rect.right = x + width/2;
        rect.bottom = y + height/2;

    }

    public int getX() {
        return (rect.left + width/2);
    }

    public int getY() {
        return (rect.top + height/2);
    }

    public void update() {
        rect.left += dx;
        rect.right += dx;
        rect.top += dy;
        rect.bottom += dy;
    }

    public abstract boolean offscreen(int screenWidth, int screenHeight);

}
