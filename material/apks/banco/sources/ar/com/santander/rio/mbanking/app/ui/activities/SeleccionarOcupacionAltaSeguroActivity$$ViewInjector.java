package ar.com.santander.rio.mbanking.app.ui.activities;

import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.view.ClearableEditText;
import butterknife.ButterKnife.Finder;

public class SeleccionarOcupacionAltaSeguroActivity$$ViewInjector {
    public static void inject(Finder finder, SeleccionarOcupacionAltaSeguroActivity seleccionarOcupacionAltaSeguroActivity, Object obj) {
        seleccionarOcupacionAltaSeguroActivity.inpSearch = (ClearableEditText) finder.findRequiredView(obj, R.id.F27_12_INP_CUADRO_BUSQUEDA, "field 'inpSearch'");
        seleccionarOcupacionAltaSeguroActivity.txtDescription = (TextView) finder.findRequiredView(obj, R.id.F27_12_LBL_DESCRIPCION, "field 'txtDescription'");
        seleccionarOcupacionAltaSeguroActivity.lstOcupaciones = (RecyclerView) finder.findRequiredView(obj, R.id.F27_12_LST_COMPANIAS, "field 'lstOcupaciones'");
    }

    public static void reset(SeleccionarOcupacionAltaSeguroActivity seleccionarOcupacionAltaSeguroActivity) {
        seleccionarOcupacionAltaSeguroActivity.inpSearch = null;
        seleccionarOcupacionAltaSeguroActivity.txtDescription = null;
        seleccionarOcupacionAltaSeguroActivity.lstOcupaciones = null;
    }
}
