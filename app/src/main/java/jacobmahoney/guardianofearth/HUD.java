package jacobmahoney.guardianofearth;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.Pair;

import java.util.Observable;
import java.util.Observer;

public class HUD implements UpdateableGameObject, DrawableGameObject {

    private Paint paint;
    private Point scoreTextPos;
    private Point livesTextPos;

    public HUD() {

        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);

        scoreTextPos = new Point();
        livesTextPos = new Point();

    }

    @Override
    public void update(int screenWidth, int screenHeight) {

        paint.setColor(Color.WHITE);
        paint.setTextSize(0.025f * screenWidth);
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setTypeface(LoadingActivity.getFont());

        scoreTextPos.set(40, 70);
        livesTextPos.set(screenWidth - 200, 70);

    }

    @Override
    public void draw(Canvas canvas) {

        canvas.drawText("score: " + GameController.getScore(), scoreTextPos.x, scoreTextPos.y, paint);
        canvas.drawText("lives: " + GameController.getLives(), livesTextPos.x, livesTextPos.y, paint);

    }

}
