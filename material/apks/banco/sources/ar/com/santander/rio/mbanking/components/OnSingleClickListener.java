package ar.com.santander.rio.mbanking.components;

import android.os.SystemClock;
import android.view.View;
import android.view.View.OnClickListener;

public abstract class OnSingleClickListener implements OnClickListener {
    protected static final long MIN_CLICK_INTERVAL = 5000;
    protected long mLastClickTime;

    public abstract void onSingleClick(View view);

    public final void onClick(View view) {
        long uptimeMillis = SystemClock.uptimeMillis();
        long j = uptimeMillis - this.mLastClickTime;
        this.mLastClickTime = uptimeMillis;
        if (j > 5000) {
            this.mLastClickTime = 0;
            onSingleClick(view);
        }
    }
}
