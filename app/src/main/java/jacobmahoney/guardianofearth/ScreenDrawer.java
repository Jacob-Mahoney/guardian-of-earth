package jacobmahoney.guardianofearth;

import android.graphics.Canvas;
import android.graphics.Typeface;
import java.util.LinkedList;
import java.util.List;

public class ScreenDrawer {

    private List<UpdateableGameObject> updateableGameObjects = new LinkedList<>();
    private List<DrawableGameObject> drawableGameObjects = new LinkedList<>();
    private static ScreenDrawer instance = null;
    private int screenWidth, screenHeight;

    private ScreenDrawer() {

    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public void setScreenWidth(int screenWidth) {
        this.screenWidth = screenWidth;
    }

    public void setScreenHeight(int screenHeight) {
        this.screenHeight = screenHeight;
    }

    public void unRegisterAll() {
        updateableGameObjects.clear();
        drawableGameObjects.clear();
    }

    public void registerUpdateableGameObject(UpdateableGameObject updateableGameObject) {
        updateableGameObjects.add(updateableGameObject);
    }

    public void registerDrawableGameObject(DrawableGameObject drawableGameObject) {
        drawableGameObjects.add(drawableGameObject);
    }

    public static ScreenDrawer getInstance() {
        if (instance == null) {
            instance = new ScreenDrawer();
        }
        return instance;
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
