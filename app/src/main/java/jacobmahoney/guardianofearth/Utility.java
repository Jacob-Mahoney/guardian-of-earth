package jacobmahoney.guardianofearth;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.Region;

import java.io.IOException;
import java.io.InputStream;

public class Utility {

    private Utility() {

    }

    public static Point rotateAboutPoint(Point point, Point pivot, double angle) {

        Point p = new Point();

        double x = Math.cos(angle) * (point.x - pivot.x) - Math.sin(angle) * (point.y - pivot.y) + pivot.x;
        double y = Math.sin(angle) * (point.x - pivot.x) + Math.cos(angle) * (point.y - pivot.y) + pivot.y;

        p.x = (int)x;
        p.y = (int)y;

        return p;

    }

    public static Region getRegionFromPath(Path path) {

        RectF rectF = new RectF();
        path.computeBounds(rectF, true);
        Region region = new Region();
        region.setPath(path, new Region((int) rectF.left, (int) rectF.top, (int) rectF.right, (int) rectF.bottom));

        return region;

    }

    public static Bitmap getBitmapFromAsset(Context context, String filePath) {

        AssetManager assetManager = context.getAssets();

        InputStream istr;
        Bitmap bitmap = null;
        try {
            istr = assetManager.open(filePath);
            bitmap = BitmapFactory.decodeStream(istr);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bitmap;

    }

}
