package jacobmahoney.guardianofearth;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;

public class RightCircle implements GameObject {

    private Path path;
    private Paint paint;

    private boolean active;

    public RightCircle() {

        active = false;
        path = new Path();
        paint = new Paint();

    }

    public void active() {
        active = true;
    }

    public void inactive() {
        active = false;
    }

    public boolean contains(int x, int y) {

        return Utility.getRegionFromPath(path).contains(x, y);

    }

    @Override
    public void update(int screenWidth, int screenHeight) {

        float left = (float)0.75*screenWidth;
        float top = 0;
        float right = (float)(screenWidth + (0.25*screenWidth));
        float bottom = screenHeight;

        RectF oval = new RectF(left, top, right, bottom);
        path.reset();
        path.arcTo(oval, 90, 180, true);
        path.close();

        paint.setColor(Color.WHITE);

        if (active) {
            paint.setAlpha(30);
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
