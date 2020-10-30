package ar.com.santander.rio.mbanking.app.ui.activities;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import butterknife.ButterKnife.Finder;
import butterknife.internal.DebouncingOnClickListener;

public class ActivityEnvioDineroOperacionesRealizadas$$ViewInjector {
    public static void inject(Finder finder, final ActivityEnvioDineroOperacionesRealizadas activityEnvioDineroOperacionesRealizadas, Object obj) {
        activityEnvioDineroOperacionesRealizadas.rll_listadoOperaciones = (LinearLayout) finder.findRequiredView(obj, R.id.F12_07_lnl_listadoOperaciones, "field 'rll_listadoOperaciones'");
        activityEnvioDineroOperacionesRealizadas.rll_listadoOperacionesVacio = (LinearLayout) finder.findRequiredView(obj, R.id.F12_07_lnl_listadoOperacionesVacio, "field 'rll_listadoOperacionesVacio'");
        activityEnvioDineroOperacionesRealizadas.messageEmptyList = (TextView) finder.findRequiredView(obj, R.id.F12_07_lbl_listadoVacio, "field 'messageEmptyList'");
        View findRequiredView = finder.findRequiredView(obj, R.id.F12_07_lbl_buscar, "field 'lblBuscar' and method 'opcionesDeBusqueda'");
        activityEnvioDineroOperacionesRealizadas.lblBuscar = (TextView) findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                activityEnvioDineroOperacionesRealizadas.b();
            }
        });
        activityEnvioDineroOperacionesRealizadas.lblVto = (TextView) finder.findRequiredView(obj, R.id.F12_07_lbl_operRealizadasAlta, "field 'lblVto'");
        View findRequiredView2 = finder.findRequiredView(obj, R.id.F12_07_img_buscarFlecha, "field 'imgBusccar' and method 'opcionesDeBusqueda'");
        activityEnvioDineroOperacionesRealizadas.imgBusccar = (ImageView) findRequiredView2;
        findRequiredView2.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                activityEnvioDineroOperacionesRealizadas.b();
            }
        });
    }

    public static void reset(ActivityEnvioDineroOperacionesRealizadas activityEnvioDineroOperacionesRealizadas) {
        activityEnvioDineroOperacionesRealizadas.rll_listadoOperaciones = null;
        activityEnvioDineroOperacionesRealizadas.rll_listadoOperacionesVacio = null;
        activityEnvioDineroOperacionesRealizadas.messageEmptyList = null;
        activityEnvioDineroOperacionesRealizadas.lblBuscar = null;
        activityEnvioDineroOperacionesRealizadas.lblVto = null;
        activityEnvioDineroOperacionesRealizadas.imgBusccar = null;
    }
}
