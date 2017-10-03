package jacobmahoney.guardianofearth;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;

public class SpaceshipObject implements GameObject {

    private Path triangle;
    private Point a, b, c;
    private Paint paint;
    private int rotation;
    private enum Status { ROTATING_LEFT, NOT_ROTATING, ROTATING_RIGHT }
    private Status status;

    public SpaceshipObject(int screenWidth, int screenHeight) {

        triangle = new Path();
        paint = new Paint();

        rotation = 0;
        int middleX = screenWidth / 2;
        a = new Point(middleX-30, screenHeight-20);
        b = new Point(middleX, screenHeight-100);
        c = new Point(middleX+30, screenHeight-20);
        status = Status.NOT_ROTATING;

    }

    public void rotateLeft() {
        status = Status.ROTATING_LEFT;
    }

    public void rotateRight() {
        status = Status.ROTATING_RIGHT;
    }

    public void rotateStop() {
        status = Status.NOT_ROTATING;
    }

    public void update() {

        if (status == Status.ROTATING_LEFT) {
            if ((rotation-1) > -45) {
                rotation--;
            }
        } else if (status == Status.ROTATING_RIGHT) {
            if ((rotation+1) < 45) {
                rotation++;
            }
        }

        triangle.reset();
        triangle.moveTo(a.x, a.y);
        triangle.lineTo(b.x, b.y);
        triangle.lineTo(c.x, c.y);
        triangle.lineTo(a.x, a.y);
        triangle.close();

        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);

    }

    @Override
    public void draw(Canvas canvas) {

        canvas.save();
        canvas.rotate(rotation, b.x, a.y);
        canvas.drawPath(triangle, paint);
        canvas.restore();

    }

}
