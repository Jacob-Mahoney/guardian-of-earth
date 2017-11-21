package jacobmahoney.guardianofearth.basic_game_objects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;

import jacobmahoney.guardianofearth.utility.Utility;
import jacobmahoney.guardianofearth.interfaces.DrawableGameObject;
import jacobmahoney.guardianofearth.interfaces.UpdateableGameObject;

public class SideButton implements UpdateableGameObject, DrawableGameObject {

    private Path path;
    private Paint paint;

    private boolean active;

    public enum Side {LEFT_SIDE, RIGHT_SIDE}
    private Side side;

    public SideButton(Side side) {

        this.side = side;
        active = false;
        path = new Path();
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);

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

        float top = 0;
        float bottom = screenHeight;
        float left, right;
        if (side == Side.LEFT_SIDE) {
            left = -(float)(0.25*screenWidth);
            right = (float)0.25*screenWidth;
        } else {
            left = (float)0.75*screenWidth;
            right = (float)(screenWidth + (0.25*screenWidth));
        }

        RectF oval = new RectF(left, top, right, bottom);
        path.reset();
        if (side == Side.LEFT_SIDE) {
            path.arcTo(oval, -90, 180, true);
        } else {
            path.arcTo(oval, 90, 180, true);
        }
        path.close();

        if (active) {
            paint.setAlpha(30);
        } else {
            paint.setAlpha(0);
        }

    }

    @Override
    public void draw(Canvas canvas) {

        canvas.drawPath(path, paint);

    }

}