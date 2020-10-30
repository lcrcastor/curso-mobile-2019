package ar.com.santander.rio.mbanking.app.ui.activities;

import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.view.ClearableEditText;
import butterknife.ButterKnife.Finder;

public class SeleccionarInformacionFondoActivity$$ViewInjector {
    public static void inject(Finder finder, SeleccionarInformacionFondoActivity seleccionarInformacionFondoActivity, Object obj) {
        seleccionarInformacionFondoActivity.inpSearch = (ClearableEditText) finder.findRequiredView(obj, R.id.F24_10_INP_CUADRO_BUSQUEDA, "field 'inpSearch'");
        seleccionarInformacionFondoActivity.lblTitulo = (TextView) finder.findRequiredView(obj, R.id.F24_10_LBL_TITULO_INFORMACION_DE_FONDO, "field 'lblTitulo'");
    }

    public static void reset(SeleccionarInformacionFondoActivity seleccionarInformacionFondoActivity) {
        seleccionarInformacionFondoActivity.inpSearch = null;
        seleccionarInformacionFondoActivity.lblTitulo = null;
    }
}
