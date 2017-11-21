package jacobmahoney.guardianofearth.canvas;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

import jacobmahoney.guardianofearth.activities.LoadingActivity;

public class CanvasText {

    public enum HorizontalAlignment {LEFT, CENTER, RIGHT}
    public enum VerticalAlignment {TOP, CENTER, BOTTOM}

    private String text;
    private Point pos;
    private HorizontalAlignment ha;
    private VerticalAlignment va;
    private Paint paint;
    private Rect bounds;

    public CanvasText(String text, HorizontalAlignment ha, VerticalAlignment va) {

        this.text = text;
        this.ha = ha;
        this.va = va;

        bounds = new Rect();

        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTypeface(LoadingActivity.font);

        switch (ha) {
            case LEFT:
                paint.setTextAlign(Paint.Align.LEFT);
                break;
            case CENTER:
                paint.setTextAlign(Paint.Align.CENTER);
                break;
            case RIGHT:
                paint.setTextAlign(Paint.Align.RIGHT);
                break;
        }

        pos = new Point();

    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTextSize(float textSize) {
        paint.setTextSize(textSize);
        paint.getTextBounds(text, 0, text.length(), bounds);
    }

    public void setX(int x) {
        pos.x = x;
    }

    public void setY(int y) {
        pos.y = getActualY(y);
    }

    public String getText() {
        return text;
    }

    public int getX() {
        return pos.x;
    }

    public int getY() {
        return pos.y;
    }

    public Paint getPaint() {
        return paint;
    }

    public int getActualY(int y) {
        switch (va) {
            case TOP:
                return y + bounds.height();
            case CENTER:
                return y + bounds.height()/2;
            default:
                return y;
        }
    }

}
