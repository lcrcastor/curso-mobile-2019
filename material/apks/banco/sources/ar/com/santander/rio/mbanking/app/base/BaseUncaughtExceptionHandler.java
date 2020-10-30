package ar.com.santander.rio.mbanking.app.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import ar.com.santander.rio.mbanking.app.exceptions.UncaughtException;
import ar.com.santander.rio.mbanking.app.ui.activities.SplashScreenActivity;
import com.crashlytics.android.Crashlytics;
import java.lang.Thread.UncaughtExceptionHandler;

public class BaseUncaughtExceptionHandler implements UncaughtExceptionHandler {
    private BaseApplication a;

    public BaseUncaughtExceptionHandler() {
    }

    public BaseUncaughtExceptionHandler(BaseApplication baseApplication) {
        this.a = baseApplication;
    }

    public void uncaughtException(Thread thread, Throwable th) {
        Log.e("@dev", "Crash:", th);
        UncaughtException uncaughtException = new UncaughtException(th);
        Crashlytics.setString("exception", "UNCAUGHT");
        Crashlytics.logException(uncaughtException);
        Context applicationContext = this.a.getApplicationContext();
        Intent intent = new Intent(applicationContext, SplashScreenActivity.class);
        intent.addFlags(268435456);
        applicationContext.startActivity(intent);
        if (applicationContext instanceof Activity) {
            ((Activity) applicationContext).finish();
        }
        Runtime.getRuntime().exit(0);
    }
}
