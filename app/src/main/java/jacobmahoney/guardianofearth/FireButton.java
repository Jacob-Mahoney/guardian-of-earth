package jacobmahoney.guardianofearth;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;

public class FireButton implements GameObject {

    private Path path;
    private Paint paint;
    private float left;
    private float top;
    private float right;
    private float bottom;

    private int screenWidth;
    private int screenHeight;

    private boolean active;

    public FireButton(int screenWidth, int screenHeight) {

        active = false;
        path = new Path();
        paint = new Paint();
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        left = screenWidth/4;
        top = 3*screenHeight/4;
        right = 3*screenWidth/4;
        bottom = screenHeight-50;

    }

    public void active() {
        active = true;
    }

    public void inactive() {
        active = false;
    }

    public boolean contains(float x, float y) {

        if (x > left && x < right && y > top && y < bottom) {
            return true;
        }

        return false;

    }

    public void update() {

        RectF oval = new RectF(left, top, right, bottom);
        path.reset();
        path.arcTo(oval, -90, 180, true);
        path.close();

        paint.setColor(Color.WHITE);

        if (active) {
            paint.setAlpha(25);
        } else {
            paint.setAlpha(0);
        }

        paint.setStyle(Paint.Style.FILL);

    }

    public void draw(Canvas canvas) {

        //canvas.drawPath(path, paint);
        canvas.drawRect(left, top, right, bottom, paint);

    }

}
