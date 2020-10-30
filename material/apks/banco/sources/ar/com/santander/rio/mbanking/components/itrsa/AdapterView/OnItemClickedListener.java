package ar.com.santander.rio.mbanking.components.itrsa.AdapterView;

import android.os.SystemClock;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class OnItemClickedListener implements OnItemClickListener {
    private long a;
    private OneItemClicked b;

    public interface OneItemClicked {
        void onItemClicked(AdapterView<?> adapterView, View view, int i, long j);
    }

    public OnItemClickedListener(OneItemClicked oneItemClicked) {
        this.b = oneItemClicked;
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        long uptimeMillis = SystemClock.uptimeMillis();
        long j2 = uptimeMillis - this.a;
        this.a = uptimeMillis;
        if (j2 > 500) {
            this.b.onItemClicked(adapterView, view, i, j);
        }
    }
}
