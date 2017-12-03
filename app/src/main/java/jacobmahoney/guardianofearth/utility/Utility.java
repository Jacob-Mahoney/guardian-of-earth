package jacobmahoney.guardianofearth.utility;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.Region;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import jacobmahoney.guardianofearth.app.MyApp;

public class Utility {

    private Utility() {

    }

    /**
     *
     * @param target Point the point of interest
     * @param pivot Point the point you would like target to rotate around
     * @param angle double the angle in radians
     * @return a new point rotated from the target about the pivot
     */
    public static Point rotateAboutPoint(Point target, Point pivot, double angle) {

        Point p = new Point();

        double x = Math.cos(angle) * (target.x - pivot.x) - Math.sin(angle) * (target.y - pivot.y) + pivot.x;
        double y = Math.sin(angle) * (target.x - pivot.x) + Math.cos(angle) * (target.y - pivot.y) + pivot.y;

        p.x = (int)x;
        p.y = (int)y;

        return p;

    }

    /**
     *
     * @param min int the lower bound
     * @param max int the upper bound
     * @return a random integer between the two bounds (inclusive)
     */
    public static int getRandomNumberBetweenTwoNumbers(int min, int max) {

        Random r = new Random();

        return r.nextInt(max-min+1) + min;

    }

    /**
     *
     * @param path Path the path of interest
     * @return a region generated from the path
     */
    public static Region getRegionFromPath(Path path) {

        RectF rectF = new RectF();
        path.computeBounds(rectF, true);
        Region region = new Region();
        region.setPath(path, new Region((int) rectF.left, (int) rectF.top, (int) rectF.right, (int) rectF.bottom));

        return region;

    }

    /**
     *
     * @param context Context context of the app
     * @param filePath String the path to the file in assets
     * @return a bitmap of the asset
     * @throws IOException if the asset is not found
     */
    public static Bitmap getBitmapFromAsset(Context context, String filePath) throws IOException {

        AssetManager assetManager = context.getAssets();

        InputStream istr = assetManager.open(filePath);
        return BitmapFactory.decodeStream(istr);

    }

}
