package jacobmahoney.guardianofearth;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.constraint.solver.widgets.Rectangle;

public class TestObject implements GameObject {

    private Rect rectangle;
    private Rect rectangleReset;
    private int color;
    private int speed;

    public TestObject(Rect rect, int speed) {
        this.rectangle = new Rect(rect);
        this.rectangleReset = new Rect(rect);
        this.color = Color.WHITE;
        this.speed = speed;
    }

    public void reset() {
        rectangle.set(rectangleReset);
    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(color);
        canvas.drawRect(rectangle, paint);
    }

    public void update(int screenWidth) {
        if (rectangle.right + speed > screenWidth) {
            reset();
        } else {
            rectangle.offset(speed, 0);
        }
    }

}
