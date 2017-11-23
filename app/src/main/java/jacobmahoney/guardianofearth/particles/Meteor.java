package jacobmahoney.guardianofearth.particles;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

public class Meteor extends Particle {

    private Bitmap bitmap;

    public Meteor(int x, int y, int dx, int dy, Bitmap bitmap, float scale) {

        super(x, y, dx, dy, Math.round(bitmap.getWidth()*scale), Math.round(bitmap.getHeight()*scale));
        this.bitmap = bitmap;

        Log.d("Meteor", Math.round(bitmap.getWidth()*scale) + " x " + Math.round(bitmap.getHeight()*scale));

    }

    public boolean intersect(Laser laser) {
        return Rect.intersects(rect, laser.rect);
    }

    @Override
    public boolean offscreen(int screenWidth, int screenHeight) {
        return (rect.top > screenHeight);
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, null, rect, null);
    }

}
