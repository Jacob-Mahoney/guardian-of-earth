package jacobmahoney.guardianofearth;

import android.graphics.Point;

public class Utility {

    public static Point rotateAboutPoint(Point point, Point pivot, double angle) {

        Point p = new Point();

        double x = Math.cos(angle) * (point.x - pivot.x) - Math.sin(angle) * (point.y - pivot.y) + pivot.x;
        double y = Math.sin(angle) * (point.x - pivot.x) + Math.cos(angle) * (point.y - pivot.y) + pivot.y;

        p.x = (int)x;
        p.y = (int)y;

        return p;

    }

}
