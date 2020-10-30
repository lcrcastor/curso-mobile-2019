package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import butterknife.ButterKnife.Finder;

public class NuevoPagoFragment$$ViewInjector {
    public static void inject(Finder finder, NuevoPagoFragment nuevoPagoFragment, Object obj) {
        nuevoPagoFragment.homeScreen = (LinearLayout) finder.findRequiredView(obj, R.id.homeScreen, "field 'homeScreen'");
        nuevoPagoFragment.activityTitle = (TextView) finder.findRequiredView(obj, R.id.vTitle, "field 'activityTitle'");
        nuevoPagoFragment.columna = (LinearLayout) finder.findRequiredView(obj, R.id.F06_00_rll_columna_pago, "field 'columna'");
        nuevoPagoFragment.lstDeudas = (RecyclerView) finder.findRequiredView(obj, R.id.F06_00_RCV_lista_home, "field 'lstDeudas'");
        nuevoPagoFragment.btnNuevoPago = (TextView) finder.findRequiredView(obj, R.id.F06_00_btn_nuevo_pago, "field 'btnNuevoPago'");
        nuevoPagoFragment.relativeSinDeudas = (RelativeLayout) finder.findRequiredView(obj, R.id.F06_00_RLL_SIN_DEUDAS, "field 'relativeSinDeudas'");
    }

    public static void reset(NuevoPagoFragment nuevoPagoFragment) {
        nuevoPagoFragment.homeScreen = null;
        nuevoPagoFragment.activityTitle = null;
        nuevoPagoFragment.columna = null;
        nuevoPagoFragment.lstDeudas = null;
        nuevoPagoFragment.btnNuevoPago = null;
        nuevoPagoFragment.relativeSinDeudas = null;
    }
}
