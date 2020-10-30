package butterknife.internal;

import android.view.View;
import android.view.View.OnClickListener;

public abstract class DebouncingOnClickListener implements OnClickListener {
    /* access modifiers changed from: private */
    public static boolean a = true;
    private static final Runnable b = new Runnable() {
        public void run() {
            DebouncingOnClickListener.a = true;
        }
    };

    public abstract void doClick(View view);

    public final void onClick(View view) {
        if (a) {
            a = false;
            view.post(b);
            doClick(view);
        }
    }
}
