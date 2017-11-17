package jacobmahoney.guardianofearth;

import android.app.Application;
import android.content.Context;

public class MyApp extends Application {

    private static Object mContext;

    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }

    public static Context getAppContext() {
        return (Context) mContext;
    }

}
