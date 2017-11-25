package jacobmahoney.guardianofearth.particles;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Laser extends Particle {

    private Paint paint;

    public Laser(int x, int y, float dx, float dy) {

        super(x, y, dx, dy, 12, 12);

        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);

    }

    @Override
    public boolean offscreen(int screenWidth, int screenHeight) {
        return (rect.right < 0 || rect.left > screenWidth || rect.bottom < 0);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawRect(rect, paint);
    }

}
