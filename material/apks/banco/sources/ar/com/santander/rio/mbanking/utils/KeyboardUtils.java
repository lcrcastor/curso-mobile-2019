package ar.com.santander.rio.mbanking.utils;

import android.app.Activity;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;

public class KeyboardUtils {

    public interface KeyboardVisibilityListener {
        void onKeyboardVisibilityChanged(boolean z);
    }

    public static void setKeyboardVisibilityListener(Activity activity, final KeyboardVisibilityListener keyboardVisibilityListener) {
        final View findViewById = activity.findViewById(16908290);
        findViewById.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            private int c;

            public void onGlobalLayout() {
                int height = findViewById.getHeight();
                if (this.c != 0) {
                    if (this.c > height) {
                        keyboardVisibilityListener.onKeyboardVisibilityChanged(true);
                    } else if (this.c < height) {
                        keyboardVisibilityListener.onKeyboardVisibilityChanged(false);
                    }
                }
                this.c = height;
            }
        });
    }
}
