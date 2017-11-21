package jacobmahoney.guardianofearth.system_game_objects;

import android.graphics.Canvas;

import jacobmahoney.guardianofearth.canvas.CanvasText;
import jacobmahoney.guardianofearth.game.GameController;
import jacobmahoney.guardianofearth.interfaces.DrawableGameObject;
import jacobmahoney.guardianofearth.interfaces.UpdateableGameObject;

public class HUD implements UpdateableGameObject, DrawableGameObject {

    private CanvasText score;
    private CanvasText lives;

    public HUD() {

        score = new CanvasText("", CanvasText.HorizontalAlignment.LEFT, CanvasText.VerticalAlignment.TOP);
        lives = new CanvasText("", CanvasText.HorizontalAlignment.RIGHT, CanvasText.VerticalAlignment.TOP);

    }

    @Override
    public void update(int screenWidth, int screenHeight) {

        score.setText("score: " + GameController.getScore());
        lives.setText("lives: " + GameController.getLives());

        score.setTextSize(0.025f * screenWidth);
        score.setX(40);
        score.setY(40);

        lives.setTextSize(0.025f * screenWidth);
        lives.setX(screenWidth-40);
        lives.setY(40);

    }

    @Override
    public void draw(Canvas canvas) {

        canvas.drawText(score.getText(), score.getX(), score.getY(), score.getPaint());
        canvas.drawText(lives.getText(), lives.getX(), lives.getY(), lives.getPaint());

    }

}
