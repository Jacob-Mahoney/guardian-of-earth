package jacobmahoney.guardianofearth.particles;

import android.graphics.RectF;

import jacobmahoney.guardianofearth.interfaces.DrawableGameObject;

public abstract class Particle implements DrawableGameObject {

    private float dx, dy;
    protected int width, height;
    protected RectF rect;

    public Particle(int x, int y, float dx, float dy, int width, int height) {

        this.dx = dx;
        this.dy = dy;
        this.width = width;
        this.height = height;

        rect = new RectF();

        // centering particle around point
        rect.left = x - width/2;
        rect.top = y - height/2;
        rect.right = x + width/2;
        rect.bottom = y + height/2;

    }

    public int getX() {
        return (int)(rect.left + width/2);
    }

    public int getY() {
        return (int)(rect.top + height/2);
    }

    public void update() {
        rect.left += dx;
        rect.right += dx;
        rect.top += dy;
        rect.bottom += dy;
    }

    public abstract boolean offscreen(int screenWidth, int screenHeight);

}
