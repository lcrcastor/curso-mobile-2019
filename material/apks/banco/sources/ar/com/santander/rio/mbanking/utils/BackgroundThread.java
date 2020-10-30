package ar.com.santander.rio.mbanking.utils;

import android.os.Handler;
import android.os.HandlerThread;

public class BackgroundThread extends HandlerThread {
    private Handler a;

    public BackgroundThread(String str) {
        super(str);
    }

    public void postTask(Runnable runnable) {
        this.a.removeCallbacks(runnable);
        this.a.post(runnable);
    }

    public void prepareHandler() {
        this.a = new Handler(getLooper());
    }
}
