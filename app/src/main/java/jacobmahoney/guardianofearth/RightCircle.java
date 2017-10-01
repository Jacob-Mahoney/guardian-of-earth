package jacobmahoney.guardianofearth;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.Log;

public class RightCircle implements GameObject {

    private Path path;
    private Paint paint;
    private double left;
    private double top;
    private double right;
    private double bottom;

    private int screenWidth;
    private int screenHeight;

    private boolean active;

    public RightCircle(int screenWidth, int screenHeight) {
        active = false;
        path = new Path();
        paint = new Paint();
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        left = 0.75*screenWidth;
        top = 0;
        right = screenWidth + (0.25*screenWidth);
        bottom = screenHeight;
    }

    public void active() {
        active = true;
    }

    public void inactive() {
        active = false;
    }

    public boolean contains(float x, float y) {

        double left = Math.pow(x - screenWidth, 2) + Math.pow(y - 0.5*screenHeight, 2);
        double right = Math.pow(0.25*screenWidth, 2);

        return (left < right);

    }

    public void update(boolean active) {

    }

    public void draw(Canvas canvas) {

        if (active) {

            RectF oval = new RectF((float)left, (float)top, (float)right, (float)bottom);
            path.arcTo(oval, 90, 180, true);

            paint.setColor(Color.GREEN);
            paint.setStyle(Paint.Style.FILL);

            canvas.drawPath(path, paint);

        }

    }

}
