package jacobmahoney.guardianofearth.game;

import android.graphics.Canvas;

import java.util.LinkedList;
import java.util.List;

import jacobmahoney.guardianofearth.interfaces.DrawableGameObject;
import jacobmahoney.guardianofearth.interfaces.UpdateableGameObject;

public class ScreenDrawer {

    private List<UpdateableGameObject> updateableGameObjects = new LinkedList<>();
    private List<DrawableGameObject> drawableGameObjects = new LinkedList<>();
    private int screenWidth, screenHeight;

    public ScreenDrawer(int screenWidth, int screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }

    public void unRegisterAll() {
        updateableGameObjects.clear();
        drawableGameObjects.clear();
    }

    public void unRegisterDrawableGameObject(DrawableGameObject drawableGameObject) {
        drawableGameObjects.remove(drawableGameObject);
    }

    public void registerUpdateableGameObject(UpdateableGameObject updateableGameObject) {
        updateableGameObjects.add(updateableGameObject);
    }

    public void registerDrawableGameObject(DrawableGameObject drawableGameObject) {
        drawableGameObjects.add(drawableGameObject);
    }

    public void updateGameObjects() {
        for (int i = 0; i < updateableGameObjects.size(); i++) {
            updateableGameObjects.get(i).update(screenWidth, screenHeight);
        }
    }

    public void drawGameObjects(Canvas canvas) {
        for (int i = 0; i < drawableGameObjects.size(); i++) {
            drawableGameObjects.get(i).draw(canvas);
        }
    }

}
