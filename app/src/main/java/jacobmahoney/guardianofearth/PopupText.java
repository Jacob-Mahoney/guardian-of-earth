package jacobmahoney.guardianofearth;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;

public class PopupText implements UpdateableGameObject, DrawableGameObject {

    private String text;
    private long stopTime;
    private Paint paint;
    private Point textPos;

    public PopupText(String text, int length) {

        this.text = text;
        paint = new Paint();
        textPos = new Point();
        stopTime = System.currentTimeMillis() + length;

    }

    @Override
    public void update(int screenWidth, int screenHeight) {

        if (System.currentTimeMillis() <= stopTime) {

            paint.setColor(Color.WHITE);
            paint.setTextSize(0.04f * screenWidth);
            paint.setTextAlign(Paint.Align.CENTER);
            paint.setTypeface(LoadingActivity.getFont());
            textPos = Utility.getCenteredTextPosition(screenWidth, screenHeight, paint);

        }

    }

    @Override
    public void draw(Canvas canvas) {
        if (System.currentTimeMillis() <= stopTime) {
            canvas.drawText(text, textPos.x, textPos.y, paint);
        }
    }

}
