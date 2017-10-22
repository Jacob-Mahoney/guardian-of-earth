package jacobmahoney.guardianofearth;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;

public class SpaceshipObject implements GameObject {

    private Path triangle;
    private Point nose, bottomLeft, bottomRight, pivot;
    private Paint paint;
    private int rotation;
    private enum Status { ROTATING_LEFT, NOT_ROTATING, ROTATING_RIGHT }
    private Status status;

    public SpaceshipObject(int screenWidth, int screenHeight) {

        triangle = new Path();
        paint = new Paint();

        rotation = 0;
        int middleX = screenWidth / 2;
        bottomLeft = new Point(middleX-30, screenHeight-20);
        nose = new Point(middleX, screenHeight-100);
        bottomRight = new Point(middleX+30, screenHeight-20);
        pivot = new Point(middleX, screenHeight-20);
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

    public int getRotation() {
        return rotation;
    }

    public Point getNosePoint() {
        return nose;
    }

    public Point getPivotPoint() {
        return pivot;
    }

    @Override
    public void update() {

        if (status == Status.ROTATING_LEFT) {
            if ((rotation-2.5) > -45) {
                rotation -= 2.5;
            }
        } else if (status == Status.ROTATING_RIGHT) {
            if ((rotation+2.5) < 45) {
                rotation += 2.5;
            }
        }

        triangle.reset();
        triangle.moveTo(bottomLeft.x, bottomLeft.y);
        triangle.lineTo(nose.x, nose.y);
        triangle.lineTo(bottomRight.x, bottomRight.y);
        triangle.lineTo(bottomLeft.x, bottomLeft.y);
        triangle.close();

        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);

    }

    @Override
    public void draw(Canvas canvas) {

        canvas.save();
        canvas.rotate(rotation, pivot.x, pivot.y);
        canvas.drawPath(triangle, paint);
        canvas.restore();

    }

}
