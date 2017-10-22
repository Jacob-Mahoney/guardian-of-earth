package jacobmahoney.guardianofearth;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {

    private MainThread thread;
    private int screenWidth;
    private int screenHeight;
    private SpaceshipObject spaceship;
    private LeftCircle leftCircle;
    private RightCircle rightCircle;
    private FireButton fireButton;
    private ParticleEmitter emitter;
    private MeteorContainer meteorContainer;

    public GamePanel(Context context) {

        super(context);
        getHolder().addCallback(this);
        setFocusable(true);
        getScreenSize(context);
        spaceship = new SpaceshipObject(screenWidth, screenHeight);
        leftCircle = new LeftCircle(screenWidth, screenHeight);
        rightCircle = new RightCircle(screenWidth, screenHeight);
        fireButton = new FireButton(screenWidth, screenHeight);
        emitter = new ParticleEmitter(screenWidth, screenHeight);
        meteorContainer = new MeteorContainer(screenWidth, screenHeight, emitter);

    }

    public void getScreenSize(Context context) {

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getRealSize(size);
        screenWidth = size.x;
        screenHeight = size.y;

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread = new MainThread(getHolder(), this);
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while (retry) {
            try {
                thread.setRunning(false);
                thread.join();
            } catch(Exception e) {
                e.printStackTrace();
            }
            retry = false;
        }
    }

    // make particle emitter and particle classes
    // particle emitter has function to add a particle to the emitter and the emitter will animate it with the update and draw functinos
    // particle emitter has a list or something of all the particles to loop through to draw and update

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (leftCircle.contains(event.getX(), event.getY())) {
                    spaceship.rotateLeft();
                    leftCircle.active();
                }
                if (rightCircle.contains(event.getX(), event.getY())) {
                    spaceship.rotateRight();
                    rightCircle.active();
                }
                if (fireButton.contains(event.getX(), event.getY())) {
                    fireButton.active();
                    spaceship.fire(emitter);
                }
                break;
            case MotionEvent.ACTION_UP:
                if (leftCircle.contains(event.getX(), event.getY())) {
                    spaceship.rotateStop();
                    leftCircle.inactive();
                }
                if (rightCircle.contains(event.getX(), event.getY())) {
                    spaceship.rotateStop();
                    rightCircle.inactive();
                }
                if (fireButton.contains(event.getX(), event.getY())) {
                    fireButton.inactive();
                }
                break;
        }
        return true;
        //return super.onTouchEvent(event);
    }

    public void update() {

        spaceship.update();
        leftCircle.update();
        rightCircle.update();
        fireButton.update();
        emitter.update();
        meteorContainer.update();

    }

    @Override
    public void draw(Canvas canvas) {

        super.draw(canvas);

        spaceship.draw(canvas);
        leftCircle.draw(canvas);
        rightCircle.draw(canvas);
        fireButton.draw(canvas);
        emitter.draw(canvas);

    }

}
