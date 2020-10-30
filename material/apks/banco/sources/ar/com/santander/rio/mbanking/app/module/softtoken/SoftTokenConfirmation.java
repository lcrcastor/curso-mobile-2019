package ar.com.santander.rio.mbanking.app.module.softtoken;

import android.widget.ViewFlipper;

public class SoftTokenConfirmation extends SoftTokenPage implements ISoftTokenConfirmation {
    private SoftTokenView a;

    public SoftTokenConfirmation(SoftTokenView softTokenView, ViewFlipper viewFlipper) {
        super(viewFlipper);
        this.a = softTokenView;
    }

    public void onCreatePage() {
        this.a.onCreatePageTokenConfirmation();
    }

    public void onFinishPage() {
        this.a.onFinishPageTokenConfirmation();
    }

    public void onVerToken() {
        this.a.onVerToken();
    }

    public void onVolverAlInicio() {
        this.a.onVolverAlInicio();
    }
}
