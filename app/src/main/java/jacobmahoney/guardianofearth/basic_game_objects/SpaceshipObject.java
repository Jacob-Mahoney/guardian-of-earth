package jacobmahoney.guardianofearth.basic_game_objects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;

import jacobmahoney.guardianofearth.activities.GameActivity;
import jacobmahoney.guardianofearth.interfaces.DrawableGameObject;
import jacobmahoney.guardianofearth.interfaces.UpdateableGameObject;

public class SpaceshipObject implements UpdateableGameObject, DrawableGameObject {

    private Bitmap bitmap;
    private Point position, nose, pivot;
    private int rotation;
    private enum Status { ROTATING_LEFT, NOT_ROTATING, ROTATING_RIGHT }
    private Status status;

    public SpaceshipObject() {

        bitmap = GameActivity.SPACESHIP_BITMAP;
        rotation = 0;
        status = Status.NOT_ROTATING;

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

        position.set(middleX - bitmap.getWidth()/2, screenHeight - 20 - bitmap.getHeight());
        nose.set(middleX, screenHeight - 20 - bitmap.getHeight());
        pivot.set(middleX, screenHeight - 20);

        // need to make rotation based on constant speed
        // right now if fps is lower, then spaceship will rotate slower

        if (status == Status.ROTATING_LEFT) {
            if ((rotation-3) > -60) {
                rotation -= 3;
            }
        } else if (status == Status.ROTATING_RIGHT) {
            if ((rotation+3) < 60) {
                rotation += 3;
            }
        }

    }

    @Override
    public void draw(Canvas canvas) {

        canvas.save();
        canvas.rotate(rotation, pivot.x, pivot.y);
        canvas.drawBitmap(GameActivity.SPACESHIP_BITMAP, position.x, position.y, null);
        canvas.restore();

    }

}
