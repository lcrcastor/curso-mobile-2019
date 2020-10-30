package ar.com.santander.rio.mbanking.app.ui.activities;

import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.view.CustomSpinner;
import butterknife.ButterKnife.Finder;

public class SeleccionarFondoActivity$$ViewInjector {
    public static void inject(Finder finder, SeleccionarFondoActivity seleccionarFondoActivity, Object obj) {
        seleccionarFondoActivity.lstFondos = (RecyclerView) finder.findRequiredView(obj, R.id.F24_02_LST_FONDOS, "field 'lstFondos'");
        seleccionarFondoActivity.lblTitle = (TextView) finder.findRequiredView(obj, R.id.F24_02_LBL_TITLE, "field 'lblTitle'");
        seleccionarFondoActivity.vgSelectorAccount = (CustomSpinner) finder.findRequiredView(obj, R.id.F24_02_vgSelectorAccount, "field 'vgSelectorAccount'");
        seleccionarFondoActivity.lblCuentaSeleccionada = (TextView) finder.findRequiredView(obj, R.id.F24_02_lbl_cuentaSeleccionada, "field 'lblCuentaSeleccionada'");
    }

    public static void reset(SeleccionarFondoActivity seleccionarFondoActivity) {
        seleccionarFondoActivity.lstFondos = null;
        seleccionarFondoActivity.lblTitle = null;
        seleccionarFondoActivity.vgSelectorAccount = null;
        seleccionarFondoActivity.lblCuentaSeleccionada = null;
    }
}
