package ar.com.santander.rio.mbanking.app.ui.activities;

import android.widget.ImageView;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import butterknife.ButterKnife.Finder;

public class ActivityEnvioDineroPrepararEnvio$$ViewInjector {
    public static void inject(Finder finder, ActivityEnvioDineroPrepararEnvio activityEnvioDineroPrepararEnvio, Object obj) {
        activityEnvioDineroPrepararEnvio.cuentaDebitoEnvio = (TextView) finder.findRequiredView(obj, R.id.F12_17_lbl_data_cuentaDebito, "field 'cuentaDebitoEnvio'");
        activityEnvioDineroPrepararEnvio.importeEnvio = (TextView) finder.findRequiredView(obj, R.id.F12_17_lbl_data_importe, "field 'importeEnvio'");
        activityEnvioDineroPrepararEnvio.seleccionarDestinatarioEnvio = (TextView) finder.findRequiredView(obj, R.id.F12_17_lbl_seleccionarDestinatario, "field 'seleccionarDestinatarioEnvio'");
        activityEnvioDineroPrepararEnvio.image_seleccionarDestinatarioEnvio = (ImageView) finder.findRequiredView(obj, R.id.F12_17_img_seleccionarDestinatario, "field 'image_seleccionarDestinatarioEnvio'");
    }

    public static void reset(ActivityEnvioDineroPrepararEnvio activityEnvioDineroPrepararEnvio) {
        activityEnvioDineroPrepararEnvio.cuentaDebitoEnvio = null;
        activityEnvioDineroPrepararEnvio.importeEnvio = null;
        activityEnvioDineroPrepararEnvio.seleccionarDestinatarioEnvio = null;
        activityEnvioDineroPrepararEnvio.image_seleccionarDestinatarioEnvio = null;
    }
}
