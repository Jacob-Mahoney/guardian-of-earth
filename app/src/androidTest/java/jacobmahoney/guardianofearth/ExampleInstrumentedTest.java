package jacobmahoney.guardianofearth;

import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    @Test
    public void getRegionFromPath() throws Exception {

        Path path = new Path();

        RectF rectF = new RectF();
        path.computeBounds(rectF, true);
        Region region = new Region();
        region.setPath(path, new Region((int) rectF.left, (int) rectF.top, (int) rectF.right, (int) rectF.bottom));

        System.out.println(region.getBoundaryPath());

        assertTrue(region.isEmpty());

    }

}
