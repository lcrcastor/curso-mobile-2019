package ar.com.santander.rio.mbanking.components;

import android.os.SystemClock;
import android.view.View;
import android.view.View.OnClickListener;

public abstract class OnSafeClickListener implements OnClickListener {
    private long a = 0;

    public abstract void onSafeClick(View view);

    public void onClick(View view) {
        if (SystemClock.elapsedRealtime() - this.a >= ((long) 1000)) {
            this.a = SystemClock.elapsedRealtime();
            onSafeClick(view);
        }
    }
}
