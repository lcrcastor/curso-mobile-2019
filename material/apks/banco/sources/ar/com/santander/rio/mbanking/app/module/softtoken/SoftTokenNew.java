package ar.com.santander.rio.mbanking.app.module.softtoken;

import android.widget.ViewFlipper;

public class SoftTokenNew extends SoftTokenPage implements ISoftTokenNew {
    private SoftTokenView a;

    public SoftTokenNew(SoftTokenView softTokenView, ViewFlipper viewFlipper) {
        super(viewFlipper);
        this.a = softTokenView;
    }

    public void onCreatePage() {
        this.a.onCreatePageTokenNew();
    }

    public void onFinishPage() {
        this.a.onFinishPageTokenNew();
    }

    public Boolean attempDoLogin() {
        return Boolean.valueOf(true);
    }
}
