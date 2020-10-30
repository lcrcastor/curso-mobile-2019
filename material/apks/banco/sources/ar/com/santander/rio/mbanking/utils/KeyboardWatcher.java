package ar.com.santander.rio.mbanking.utils;

import android.app.Activity;
import android.os.Build.VERSION;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import java.lang.ref.WeakReference;

public class KeyboardWatcher {
    private WeakReference<Activity> a;
    /* access modifiers changed from: private */
    public WeakReference<View> b;
    /* access modifiers changed from: private */
    public WeakReference<OnKeyboardToggleListener> c;
    private OnGlobalLayoutListener d;

    class GlobalLayoutListener implements OnGlobalLayoutListener {
        int a;
        boolean b;
        boolean c;

        private GlobalLayoutListener() {
        }

        public void onGlobalLayout() {
            if (this.a == 0) {
                this.a = ((View) KeyboardWatcher.this.b.get()).getHeight();
                return;
            }
            if (this.a > ((View) KeyboardWatcher.this.b.get()).getHeight()) {
                if (KeyboardWatcher.this.c.get() != null && (!this.b || !this.c)) {
                    this.c = true;
                    ((OnKeyboardToggleListener) KeyboardWatcher.this.c.get()).onKeyboardShown(this.a - ((View) KeyboardWatcher.this.b.get()).getHeight());
                }
            } else if (!this.b || this.c) {
                this.c = false;
                ((View) KeyboardWatcher.this.b.get()).post(new Runnable() {
                    public void run() {
                        if (KeyboardWatcher.this.c.get() != null) {
                            ((OnKeyboardToggleListener) KeyboardWatcher.this.c.get()).onKeyboardClosed();
                        }
                    }
                });
            }
            this.b = true;
        }
    }

    public interface OnKeyboardToggleListener {
        void onKeyboardClosed();

        void onKeyboardShown(int i);
    }

    public KeyboardWatcher(Activity activity) {
        this.a = new WeakReference<>(activity);
        a();
    }

    public void setListener(OnKeyboardToggleListener onKeyboardToggleListener) {
        this.c = new WeakReference<>(onKeyboardToggleListener);
    }

    public void destroy() {
        if (this.b.get() == null) {
            return;
        }
        if (VERSION.SDK_INT >= 16) {
            ((View) this.b.get()).getViewTreeObserver().removeOnGlobalLayoutListener(this.d);
        } else {
            ((View) this.b.get()).getViewTreeObserver().removeGlobalOnLayoutListener(this.d);
        }
    }

    private void a() {
        if (b()) {
            this.d = new GlobalLayoutListener();
            this.b = new WeakReference<>(((Activity) this.a.get()).findViewById(16908290));
            ((View) this.b.get()).getViewTreeObserver().addOnGlobalLayoutListener(this.d);
            return;
        }
        throw new IllegalArgumentException(String.format("Activity %s should have windowSoftInputMode=\"adjustResize\"to make KeyboardWatcher working. You can set it in AndroidManifest.xml", new Object[]{((Activity) this.a.get()).getClass().getSimpleName()}));
    }

    private boolean b() {
        return (((Activity) this.a.get()).getWindow().getAttributes().softInputMode & 16) != 0;
    }
}
