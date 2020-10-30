package ar.com.santander.rio.mbanking.utils.keyboardvisibilityevent;

import android.app.Activity;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import ar.com.santander.rio.mbanking.utils.keyboardvisibilityevent.util.UIUtil;

public class KeyboardVisibilityEvent {
    public static void setEventListener(final Activity activity, final KeyboardVisibilityEventListener keyboardVisibilityEventListener) {
        if (activity == null) {
            throw new NullPointerException("Parameter:activity must not be null");
        } else if (keyboardVisibilityEventListener == null) {
            throw new NullPointerException("Parameter:listener must not be null");
        } else {
            final View a = a(activity);
            a.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
                private final Rect d = new Rect();
                private final int e = Math.round(UIUtil.convertDpToPx(activity, 100.0f));
                private boolean f = false;

                public void onGlobalLayout() {
                    a.getWindowVisibleDisplayFrame(this.d);
                    boolean z = a.getRootView().getHeight() - this.d.height() > this.e;
                    if (z != this.f) {
                        this.f = z;
                        keyboardVisibilityEventListener.onVisibilityChanged(z);
                    }
                }
            });
        }
    }

    public static boolean isKeyboardVisible(Activity activity) {
        Rect rect = new Rect();
        View a = a(activity);
        int round = Math.round(UIUtil.convertDpToPx(activity, 100.0f));
        a.getWindowVisibleDisplayFrame(rect);
        return a.getRootView().getHeight() - rect.height() > round;
    }

    private static View a(Activity activity) {
        return ((ViewGroup) activity.findViewById(16908290)).getChildAt(0);
    }
}
