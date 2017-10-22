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

    public void fire(ParticleEmitter emitter) {

        double rot = 90 - rotation;
        Point p = rotateAboutPoint(nose, pivot, Math.toRadians(rotation));

        emitter.addParticle(new Particle(p.x, p.y, (int)(15*Math.cos(Math.toRadians(rot))), -(int)(15*Math.sin(Math.toRadians(rot)))));

    }

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

    private Point rotateAboutPoint(Point point, Point pivot, double angle) {

        Point p = new Point();

        double x = Math.cos(Math.toRadians(rotation)) * (point.x - pivot.x) - Math.sin(Math.toRadians(rotation)) * (point.y - pivot.y) + pivot.x;
        double y = Math.sin(Math.toRadians(rotation)) * (point.x - pivot.x) + Math.cos(Math.toRadians(rotation)) * (point.y - pivot.y) + pivot.y;

        p.x = (int)x;
        p.y = (int)y;

        return p;

    }

    @Override
    public void draw(Canvas canvas) {

        canvas.save();
        canvas.rotate(rotation, pivot.x, pivot.y);
        canvas.drawPath(triangle, paint);
        canvas.restore();

    }

}
