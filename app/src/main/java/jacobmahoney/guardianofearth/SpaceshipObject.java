package jacobmahoney.guardianofearth;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;

public class SpaceshipObject implements UpdateableGameObject, DrawableGameObject {

    private Path triangle;
    private Point nose, bottomLeft, bottomRight, pivot;
    private Paint paint;
    private int rotation;
    private enum Status { ROTATING_LEFT, NOT_ROTATING, ROTATING_RIGHT }
    private Status status;

    SpaceshipObject() {

        triangle = new Path();
        paint = new Paint();
        rotation = 0;
        status = Status.NOT_ROTATING;

        bottomLeft = new Point();
        nose = new Point();
        bottomRight = new Point();
        pivot = new Point();

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
    public void update(int screenWidth, int screenHeight) {

        int middleX = screenWidth / 2;

        bottomLeft.set(middleX-30, screenHeight-20);
        nose.set(middleX, screenHeight-100);
        bottomRight.set(middleX+30, screenHeight-20);
        pivot.set(middleX, screenHeight-20);

        if (status == Status.ROTATING_LEFT) {
            if ((rotation-3) > -60) {
                rotation -= 3;
            }
        } else if (status == Status.ROTATING_RIGHT) {
            if ((rotation+3) < 60) {
                rotation += 3;
            }
        }

        // finding out the spaceship's equivalent rotated points
        Point noseR = Utility.rotateAboutPoint(nose, pivot, Math.toRadians(rotation));
        Point bottomLeftR = Utility.rotateAboutPoint(bottomLeft, pivot, Math.toRadians(rotation));
        Point bottomRightR = Utility.rotateAboutPoint(bottomRight, pivot, Math.toRadians(rotation));

        triangle.reset();
        triangle.moveTo(bottomLeftR.x, bottomLeftR.y);
        triangle.lineTo(noseR.x, noseR.y);
        triangle.lineTo(bottomRightR.x, bottomRightR.y);
        triangle.lineTo(bottomLeftR.x, bottomLeftR.y);
        triangle.close();

        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);

    }

    @Override
    public void draw(Canvas canvas) {

        canvas.drawPath(triangle, paint);

    }

}
