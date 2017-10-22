package jacobmahoney.guardianofearth;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import java.util.LinkedList;
import java.util.List;

public class ParticleEmitter implements GameObject {

    List<Particle> list = new LinkedList<>();
    private Paint paint;
    private int screenWidth, screenHeight;

    public ParticleEmitter(int screenWidth, int screenHeight) {

        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

    }

    public void addParticle(Particle p) {
        list.add(p);
    }

    @Override
    public void update() {

        for (int i = 0; i < list.size(); i++) {
            list.get(i).update();
            if (list.get(i).offscreen(screenWidth, screenHeight)) {
                list.remove(list.get(i));
            }
        }

    }

    @Override
    public void draw(Canvas canvas) {

        for (int i = 0; i < list.size(); i++) {
            canvas.drawRect(list.get(i), paint);
        }

    }

}
