package jacobmahoney.guardianofearth.basic_game_objects;

import android.graphics.Canvas;

import jacobmahoney.guardianofearth.canvas.CanvasText;
import jacobmahoney.guardianofearth.interfaces.DrawableGameObject;

// text to be drawn to the canvas at a specific location for a specified or unlimited amount of time
public class PopupText implements DrawableGameObject {

    private CanvasText canvasText;
    private long stopTime;

    public PopupText(String text, int x, int y, float textSize, int timeLength) {

        canvasText = new CanvasText(text, CanvasText.HorizontalAlignment.CENTER, CanvasText.VerticalAlignment.CENTER);

        canvasText.setTextSize(textSize);
        canvasText.setX(x);
        canvasText.setY(y);

        if (timeLength != -1) {
            stopTime = System.currentTimeMillis() + timeLength;
        } else {
            stopTime = Long.MAX_VALUE;
        }

    }

    @Override
    public void draw(Canvas canvas) {
        if (System.currentTimeMillis() < stopTime) {
            canvas.drawText(canvasText.getText(), canvasText.getX(), canvasText.getY(), canvasText.getPaint());
        }
    }

}
