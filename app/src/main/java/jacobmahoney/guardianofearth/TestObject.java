package jacobmahoney.guardianofearth;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class TestObject implements GameObject {

    private Rect rectangle;
    private int color;

    public TestObject(Rect rectangle, int color) {
        this.rectangle = rectangle;
        this.color = color;
    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(color);
        canvas.drawRect(rectangle, paint);
    }

    public void update(int w, int h) {
        rectangle.set(w/2 - rectangle.width()/2, h/2 - rectangle.height()/2, w/2 + rectangle.width()/2, h/2 + rectangle.height()/2);
    }

}
