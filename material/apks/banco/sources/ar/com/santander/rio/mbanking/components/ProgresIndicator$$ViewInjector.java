package ar.com.santander.rio.mbanking.components;

import android.widget.Button;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import butterknife.ButterKnife.Finder;

public class ProgresIndicator$$ViewInjector {
    public static void inject(Finder finder, ProgresIndicator progresIndicator, Object obj) {
        progresIndicator.txtMensaje = (TextView) finder.findRequiredView(obj, R.id.txtMensaje, "field 'txtMensaje'");
        progresIndicator.btnVolver = (Button) finder.findRequiredView(obj, R.id.btnVolver, "field 'btnVolver'");
    }

    public static void reset(ProgresIndicator progresIndicator) {
        progresIndicator.txtMensaje = null;
        progresIndicator.btnVolver = null;
    }
}
