package jacobmahoney.guardianofearth;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;

public class SpaceshipObject implements GameObject {

    private Path triangle;
    private Point a, b, c;
    private Paint paint;

    public SpaceshipObject(int screenWidth, int screenHeight) {

        triangle = new Path();
        paint = new Paint();

        int middleX = screenWidth / 2;
        a = new Point(middleX-30, screenHeight-20);
        b = new Point(middleX, screenHeight-100);
        c = new Point(middleX+30, screenHeight-20);

    }

    @Override
    public void draw(Canvas canvas) {

        triangle.moveTo(a.x, a.y);
        triangle.lineTo(b.x, b.y);
        triangle.lineTo(c.x, c.y);
        triangle.lineTo(a.x, a.y);

        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);

        canvas.save();
        canvas.rotate(45, b.x, a.y);
        canvas.drawPath(triangle, paint);
        canvas.restore();

        //canvas.drawPath(triangle, paint);

    }

}
