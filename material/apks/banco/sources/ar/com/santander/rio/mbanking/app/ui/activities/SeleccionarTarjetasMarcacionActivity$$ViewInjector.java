package ar.com.santander.rio.mbanking.app.ui.activities;

import android.widget.Button;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.view.AnimatedExpandableListView;
import butterknife.ButterKnife.Finder;

public class SeleccionarTarjetasMarcacionActivity$$ViewInjector {
    public static void inject(Finder finder, SeleccionarTarjetasMarcacionActivity seleccionarTarjetasMarcacionActivity, Object obj) {
        seleccionarTarjetasMarcacionActivity.listaTarjetas = (AnimatedExpandableListView) finder.findRequiredView(obj, R.id.F26_04_EXP_LST_TARJETAS, "field 'listaTarjetas'");
        seleccionarTarjetasMarcacionActivity.btnGuardar = (Button) finder.findRequiredView(obj, R.id.F26_04_BTN_GUARDAR, "field 'btnGuardar'");
        seleccionarTarjetasMarcacionActivity.lblTitulo = (TextView) finder.findRequiredView(obj, R.id.F26_04_LBL_TITLE, "field 'lblTitulo'");
    }

    public static void reset(SeleccionarTarjetasMarcacionActivity seleccionarTarjetasMarcacionActivity) {
        seleccionarTarjetasMarcacionActivity.listaTarjetas = null;
        seleccionarTarjetasMarcacionActivity.btnGuardar = null;
        seleccionarTarjetasMarcacionActivity.lblTitulo = null;
    }
}
