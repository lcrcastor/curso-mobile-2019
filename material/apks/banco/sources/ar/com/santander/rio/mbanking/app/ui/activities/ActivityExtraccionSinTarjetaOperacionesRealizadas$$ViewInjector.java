package ar.com.santander.rio.mbanking.app.ui.activities;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import butterknife.ButterKnife.Finder;
import butterknife.internal.DebouncingOnClickListener;

public class ActivityExtraccionSinTarjetaOperacionesRealizadas$$ViewInjector {
    public static void inject(Finder finder, final ActivityExtraccionSinTarjetaOperacionesRealizadas activityExtraccionSinTarjetaOperacionesRealizadas, Object obj) {
        activityExtraccionSinTarjetaOperacionesRealizadas.rll_listadoOperaciones = (LinearLayout) finder.findRequiredView(obj, R.id.F19_07_lnl_listadoOperaciones, "field 'rll_listadoOperaciones'");
        activityExtraccionSinTarjetaOperacionesRealizadas.rll_listadoOperacionesVacio = (LinearLayout) finder.findRequiredView(obj, R.id.F19_07_lnl_listadoOperacionesVacio, "field 'rll_listadoOperacionesVacio'");
        activityExtraccionSinTarjetaOperacionesRealizadas.messageEmptyList = (TextView) finder.findRequiredView(obj, R.id.F19_07_lbl_listadoVacio, "field 'messageEmptyList'");
        View findRequiredView = finder.findRequiredView(obj, R.id.F19_07_lbl_buscar, "field 'lbl_buscar' and method 'opcionesDeBusqueda'");
        activityExtraccionSinTarjetaOperacionesRealizadas.lbl_buscar = (TextView) findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                activityExtraccionSinTarjetaOperacionesRealizadas.b();
            }
        });
        View findRequiredView2 = finder.findRequiredView(obj, R.id.F19_07_img_buscarFlecha, "field 'img_buscarFlecha' and method 'opcionesDeBusqueda'");
        activityExtraccionSinTarjetaOperacionesRealizadas.img_buscarFlecha = (ImageView) findRequiredView2;
        findRequiredView2.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                activityExtraccionSinTarjetaOperacionesRealizadas.b();
            }
        });
    }

    public static void reset(ActivityExtraccionSinTarjetaOperacionesRealizadas activityExtraccionSinTarjetaOperacionesRealizadas) {
        activityExtraccionSinTarjetaOperacionesRealizadas.rll_listadoOperaciones = null;
        activityExtraccionSinTarjetaOperacionesRealizadas.rll_listadoOperacionesVacio = null;
        activityExtraccionSinTarjetaOperacionesRealizadas.messageEmptyList = null;
        activityExtraccionSinTarjetaOperacionesRealizadas.lbl_buscar = null;
        activityExtraccionSinTarjetaOperacionesRealizadas.img_buscarFlecha = null;
    }
}
