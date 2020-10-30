package ar.com.santander.rio.mbanking.app.module.softtoken;

import android.widget.ViewFlipper;

public class SoftTokenTimer extends SoftTokenPage implements ISoftTokenTimer {
    private SoftTokenView a;

    public SoftTokenTimer(SoftTokenView softTokenView, ViewFlipper viewFlipper) {
        super(viewFlipper);
        this.a = softTokenView;
    }

    public void onCreatePage() {
        this.a.onCreatePageTokenTimer();
    }

    public void onFinishPage() {
        this.a.onFinishPageTokenTimer();
    }
}
