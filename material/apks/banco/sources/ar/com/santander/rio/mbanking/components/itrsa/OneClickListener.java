package ar.com.santander.rio.mbanking.components.itrsa;

import android.os.SystemClock;
import android.view.View;
import android.view.View.OnClickListener;

public class OneClickListener implements OnClickListener {
    private long a;
    private OneClicked b;

    public interface OneClicked {
        void onClicked(View view);
    }

    public OneClickListener(OneClicked oneClicked) {
        this.b = oneClicked;
    }

    public final void onClick(View view) {
        long uptimeMillis = SystemClock.uptimeMillis();
        long j = uptimeMillis - this.a;
        this.a = uptimeMillis;
        if (j > 500) {
            this.b.onClicked(view);
        }
    }
}
