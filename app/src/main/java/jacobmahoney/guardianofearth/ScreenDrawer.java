package jacobmahoney.guardianofearth;

import android.graphics.Canvas;

import java.util.LinkedList;
import java.util.List;

public class ScreenDrawer {

    private List<GameObject> gameObjects = new LinkedList<>();
    private static ScreenDrawer instance = null;
    private int screenWidth, screenHeight;

    protected ScreenDrawer() {

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

    public void registerGameObject(GameObject gameObject) {
        gameObjects.add(gameObject);
    }

    public static ScreenDrawer getInstance() {
        if (instance == null) {
            instance = new ScreenDrawer();
        }
        return instance;
    }

    public void updateGameObjects() {
        for (int i = 0; i < gameObjects.size(); i++) {
            gameObjects.get(i).update(screenWidth, screenHeight);
        }
    }

    public void drawGameObjects(Canvas canvas) {
        for (int i = 0; i < gameObjects.size(); i++) {
            gameObjects.get(i).draw(canvas);
        }
    }

}
