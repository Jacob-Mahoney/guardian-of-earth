package jacobmahoney.guardianofearth.basic_game_objects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.util.Log;

import jacobmahoney.guardianofearth.activities.GameActivity;
import jacobmahoney.guardianofearth.interfaces.DrawableGameObject;
import jacobmahoney.guardianofearth.interfaces.UpdateableGameObject;

public class SpaceshipObject implements UpdateableGameObject, DrawableGameObject {

    private Bitmap bitmap;
    private Point position, nose, pivot;
    private int rotation;
    private enum Status { ROTATING_LEFT, NOT_ROTATING, ROTATING_RIGHT }
    private Status status;

    private long timeUntilNextLeftRotate, timeUntilNextRightRotate;

    public SpaceshipObject() {

        bitmap = GameActivity.SPACESHIP_BITMAP;
        rotation = 0;
        status = Status.NOT_ROTATING;
        timeUntilNextLeftRotate = System.currentTimeMillis();
        timeUntilNextRightRotate = System.currentTimeMillis();

        position = new Point();
        nose = new Point();
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

        position.set(middleX - bitmap.getWidth()/2, screenHeight - 130 - bitmap.getHeight());
        nose.set(middleX, screenHeight - 130 - bitmap.getHeight());
        pivot.set(middleX, screenHeight - 130);

        if (status == Status.ROTATING_LEFT) {
            if (System.currentTimeMillis() >= timeUntilNextLeftRotate) {
                if ((rotation-2.5) > -90) {
                    rotation -= 2.5;
                }
                timeUntilNextLeftRotate = System.currentTimeMillis() + 5;
            }
        } else if (status == Status.ROTATING_RIGHT) {
            if (System.currentTimeMillis() >= timeUntilNextRightRotate) {
                if ((rotation+2.5) < 90) {
                    rotation += 2.5;
                }
                timeUntilNextRightRotate = System.currentTimeMillis() + 5;
            }
        }

    }

    @Override
    public void draw(Canvas canvas) {

        canvas.save();
        canvas.rotate(rotation, pivot.x, pivot.y);
        canvas.drawBitmap(bitmap, position.x, position.y, null);
        canvas.restore();

    }

}
