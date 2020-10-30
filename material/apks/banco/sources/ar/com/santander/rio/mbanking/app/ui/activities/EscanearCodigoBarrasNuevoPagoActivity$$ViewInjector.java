package ar.com.santander.rio.mbanking.app.ui.activities;

import android.widget.FrameLayout;
import ar.com.santander.rio.mbanking.R;
import butterknife.ButterKnife.Finder;

public class EscanearCodigoBarrasNuevoPagoActivity$$ViewInjector {
    public static void inject(Finder finder, EscanearCodigoBarrasNuevoPagoActivity escanearCodigoBarrasNuevoPagoActivity, Object obj) {
        escanearCodigoBarrasNuevoPagoActivity.preview = (FrameLayout) finder.findRequiredView(obj, R.id.cameraPreview, "field 'preview'");
    }

    public static void reset(EscanearCodigoBarrasNuevoPagoActivity escanearCodigoBarrasNuevoPagoActivity) {
        escanearCodigoBarrasNuevoPagoActivity.preview = null;
    }
}
