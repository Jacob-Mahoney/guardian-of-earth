package jacobmahoney.guardianofearth.particles;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.RectF;

public class Meteor extends Particle {

    private Bitmap bitmap;
    private int pointWorth;

    public Meteor(int x, int y, float speed, float dx, float dy, Bitmap bitmap, float scale) {

        super(x, y, dx, dy, Math.round(bitmap.getWidth()*scale), Math.round(bitmap.getHeight()*scale));
        this.bitmap = bitmap;
        pointWorth = Math.round(speed * 5) + Math.round(1/scale * 10);

    }

    public int getPointWorth() {
        return pointWorth;
    }

    public Point getLocation() {
        int x = (int)rect.left + width/2;
        int y = (int)rect.top + height/2;
        return new Point(x, y);
    }

    public boolean intersect(Laser laser) {
        return RectF.intersects(rect, laser.rect);
    }

    @Override
    public boolean offscreen(int screenWidth, int screenHeight) {
        return (rect.top > screenHeight);
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, null, rect, null);
    }

}
