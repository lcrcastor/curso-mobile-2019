package android.support.v4.app;

import android.view.View;
import android.view.View.OnAttachStateChangeListener;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnPreDrawListener;

class OneShotPreDrawListener implements OnAttachStateChangeListener, OnPreDrawListener {
    private final View a;
    private ViewTreeObserver b;
    private final Runnable c;

    private OneShotPreDrawListener(View view, Runnable runnable) {
        this.a = view;
        this.b = view.getViewTreeObserver();
        this.c = runnable;
    }

    public static OneShotPreDrawListener a(View view, Runnable runnable) {
        OneShotPreDrawListener oneShotPreDrawListener = new OneShotPreDrawListener(view, runnable);
        view.getViewTreeObserver().addOnPreDrawListener(oneShotPreDrawListener);
        view.addOnAttachStateChangeListener(oneShotPreDrawListener);
        return oneShotPreDrawListener;
    }

    public boolean onPreDraw() {
        a();
        this.c.run();
        return true;
    }

    public void a() {
        if (this.b.isAlive()) {
            this.b.removeOnPreDrawListener(this);
        } else {
            this.a.getViewTreeObserver().removeOnPreDrawListener(this);
        }
        this.a.removeOnAttachStateChangeListener(this);
    }

    public void onViewAttachedToWindow(View view) {
        this.b = view.getViewTreeObserver();
    }

    public void onViewDetachedFromWindow(View view) {
        a();
    }
}
