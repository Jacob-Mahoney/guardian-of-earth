package jacobmahoney.guardianofearth;

import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;

import org.junit.Test;

import static org.junit.Assert.*;

public class ExampleUnitTest {

    @Test
    public void rotateAboutPoint() throws Exception {

        int pointX = Integer.MAX_VALUE, pointY = 1000;
        int pivotX = 960, pivotY = 1050;
        double angle = Math.toRadians(0);

        double x = Math.cos(angle) * (pointX - pivotX) - Math.sin(angle) * (pointY - pivotY) + pivotX;
        double y = Math.sin(angle) * (pointX - pivotX) + Math.cos(angle) * (pointY - pivotY) + pivotY;

        int px = (int)x;
        int py = (int)y;

        System.out.println(px + " " + py);

        assertEquals(pointX, px);
        assertEquals(pointY, py);

    }

}