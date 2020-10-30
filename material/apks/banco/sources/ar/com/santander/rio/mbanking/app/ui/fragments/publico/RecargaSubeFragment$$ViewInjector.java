package ar.com.santander.rio.mbanking.app.ui.fragments.publico;

import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import butterknife.ButterKnife.Finder;

public class RecargaSubeFragment$$ViewInjector {
    public static void inject(Finder finder, RecargaSubeFragment recargaSubeFragment, Object obj) {
        recargaSubeFragment.viewSpinnerPago = finder.findRequiredView(obj, R.id.spinner_pago, "field 'viewSpinnerPago'");
        recargaSubeFragment.btnAgregarTarjeta = (TextView) finder.findRequiredView(obj, R.id.tvAgregarTarjeta, "field 'btnAgregarTarjeta'");
        recargaSubeFragment.btn_continuar = (Button) finder.findRequiredView(obj, R.id.sube_next_button, "field 'btn_continuar'");
        recargaSubeFragment.actionBarLayout = (RelativeLayout) finder.findRequiredView(obj, R.id.recarga_sube_actionbar, "field 'actionBarLayout'");
    }

    public static void reset(RecargaSubeFragment recargaSubeFragment) {
        recargaSubeFragment.viewSpinnerPago = null;
        recargaSubeFragment.btnAgregarTarjeta = null;
        recargaSubeFragment.btn_continuar = null;
        recargaSubeFragment.actionBarLayout = null;
    }
}
