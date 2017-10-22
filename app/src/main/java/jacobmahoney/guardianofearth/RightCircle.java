package jacobmahoney.guardianofearth;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;

public class RightCircle implements GameObject {

    private Path path;
    private Paint paint;
    private float left;
    private float top;
    private float right;
    private float bottom;

    private int screenWidth;
    private int screenHeight;

    private boolean active;

    public RightCircle(int screenWidth, int screenHeight) {

        active = false;
        path = new Path();
        paint = new Paint();
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        left = (float)0.75*screenWidth;
        top = 0;
        right = (float)(screenWidth + (0.25*screenWidth));
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

    @Override
    public void update() {

        RectF oval = new RectF(left, top, right, bottom);
        path.reset();
        path.arcTo(oval, 90, 180, true);
        path.close();

        paint.setColor(Color.WHITE);

        if (active) {
            paint.setAlpha(50);
        } else {
            paint.setAlpha(0);
        }

        paint.setStyle(Paint.Style.FILL);

    }

    @Override
    public void draw(Canvas canvas) {

        canvas.drawPath(path, paint);

    }

}
