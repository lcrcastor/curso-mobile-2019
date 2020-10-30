package ar.com.santander.rio.mbanking.app.ui.activities;

import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.view.CustomSpinner;
import butterknife.ButterKnife.Finder;

public class CotizacionesFondoActivity$$ViewInjector {
    public static void inject(Finder finder, CotizacionesFondoActivity cotizacionesFondoActivity, Object obj) {
        cotizacionesFondoActivity.imgBtnSort = (ImageButton) finder.findRequiredView(obj, R.id.F24_13_BTN_SORT, "field 'imgBtnSort'");
        cotizacionesFondoActivity.lst_wrapper = (LinearLayout) finder.findRequiredView(obj, R.id.F24_13_LNL_LST_WRAPPER, "field 'lst_wrapper'");
        cotizacionesFondoActivity.selectorTipoFondo = (CustomSpinner) finder.findRequiredView(obj, R.id.F24_13_vgSelectorTipoFondo, "field 'selectorTipoFondo'");
        cotizacionesFondoActivity.selectorValoresFondo = (CustomSpinner) finder.findRequiredView(obj, R.id.F24_13_vgSelectorValoresFondo, "field 'selectorValoresFondo'");
        cotizacionesFondoActivity.lblLegales = (TextView) finder.findRequiredView(obj, R.id.F24_13_LBL_LEGALES, "field 'lblLegales'");
    }

    public static void reset(CotizacionesFondoActivity cotizacionesFondoActivity) {
        cotizacionesFondoActivity.imgBtnSort = null;
        cotizacionesFondoActivity.lst_wrapper = null;
        cotizacionesFondoActivity.selectorTipoFondo = null;
        cotizacionesFondoActivity.selectorValoresFondo = null;
        cotizacionesFondoActivity.lblLegales = null;
    }
}
