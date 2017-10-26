package jacobmahoney.guardianofearth;

import android.graphics.Canvas;
import android.graphics.Point;

public interface GameObject {

    public void update(int screenWidth, int screenHeight);
    public void draw(Canvas canvas);

}
