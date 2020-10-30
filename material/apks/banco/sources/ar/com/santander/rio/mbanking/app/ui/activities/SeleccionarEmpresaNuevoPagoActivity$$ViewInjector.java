package ar.com.santander.rio.mbanking.app.ui.activities;

import android.support.v7.widget.RecyclerView;
import ar.com.santander.rio.mbanking.R;
import butterknife.ButterKnife.Finder;

public class SeleccionarEmpresaNuevoPagoActivity$$ViewInjector {
    public static void inject(Finder finder, SeleccionarEmpresaNuevoPagoActivity seleccionarEmpresaNuevoPagoActivity, Object obj) {
        seleccionarEmpresaNuevoPagoActivity.lstCompanies = (RecyclerView) finder.findRequiredView(obj, R.id.F06_09_LST_COMPANIAS, "field 'lstCompanies'");
    }

    public static void reset(SeleccionarEmpresaNuevoPagoActivity seleccionarEmpresaNuevoPagoActivity) {
        seleccionarEmpresaNuevoPagoActivity.lstCompanies = null;
    }
}
