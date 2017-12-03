package jacobmahoney.guardianofearth.basic_game_objects;

import android.graphics.Canvas;

import jacobmahoney.guardianofearth.canvas.CanvasText;
import jacobmahoney.guardianofearth.interfaces.DrawableGameObject;

// text to be drawn to the canvas at a specific location for a specified amount of time
public class PopupText implements DrawableGameObject {

    private CanvasText canvasText;

    public PopupText(String text, int x, int y, float textSize) {

        canvasText = new CanvasText(text, CanvasText.HorizontalAlignment.CENTER, CanvasText.VerticalAlignment.CENTER);

        canvasText.setTextSize(textSize);
        canvasText.setX(x);
        canvasText.setY(y);

    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawText(canvasText.getText(), canvasText.getX(), canvasText.getY(), canvasText.getPaint());
    }

}
