package jacobmahoney.guardianofearth;

import android.graphics.Canvas;

// text to be drawn to the canvas at a specific location for a specified amount of time
public class PopupText implements DrawableGameObject {

    private CanvasText canvasText;
    private long stopTime;

    public PopupText(String text, int x, int y, float textSize, int length) {

        canvasText = new CanvasText(text, CanvasText.HorizontalAlignment.CENTER, CanvasText.VerticleAlignment.CENTER);
        stopTime = System.currentTimeMillis() + length;

        canvasText.setTextSize(textSize);
        canvasText.setX(x);
        canvasText.setY(y);

    }

    @Override
    public void draw(Canvas canvas) {
        if (System.currentTimeMillis() <= stopTime) {
            canvas.drawText(canvasText.getText(), canvasText.getX(), canvasText.getY(), canvasText.getPaint());
        }
    }

}
