package jacobmahoney.guardianofearth.basic_game_objects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;

import jacobmahoney.guardianofearth.activities.GameActivity;
import jacobmahoney.guardianofearth.interfaces.DrawableGameObject;
import jacobmahoney.guardianofearth.interfaces.UpdateableGameObject;

public class Earth implements UpdateableGameObject, DrawableGameObject {

    private Bitmap bitmap;
    private Rect rect;
    private Point position;

    public Earth() {

        bitmap = GameActivity.EARTH_BITMAP;
        rect = new Rect();
        position = new Point();

    }

    @Override
    public void update(int screenWidth, int screenHeight) {

        double top = 0.65 * screenHeight;
        double factor = screenWidth*2 / bitmap.getWidth();
        double bottom = top + factor*bitmap.getHeight();
        double left = -screenWidth/2;
        double right = 3*screenWidth/2;

        rect.set((int)left, (int)top, (int)right, (int)bottom);

        position.set(0, (int)top);

    }

    @Override
    public void draw(Canvas canvas) {

        canvas.drawBitmap(bitmap, null, rect, null);

    }

}
