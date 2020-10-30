package ar.com.santander.rio.mbanking.app.ui.activities;

import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.view.ClearableEditText;
import butterknife.ButterKnife.Finder;

public class BuscarEmpresaNuevoPagoActivity$$ViewInjector {
    public static void inject(Finder finder, BuscarEmpresaNuevoPagoActivity buscarEmpresaNuevoPagoActivity, Object obj) {
        buscarEmpresaNuevoPagoActivity.inpSearch = (ClearableEditText) finder.findRequiredView(obj, R.id.F06_05_INP_CUADRO_BUSQUEDA, "field 'inpSearch'");
        buscarEmpresaNuevoPagoActivity.txtDescription = (TextView) finder.findRequiredView(obj, R.id.F06_05_LBL_DESCRIPCION, "field 'txtDescription'");
        buscarEmpresaNuevoPagoActivity.lstCompanies = (RecyclerView) finder.findRequiredView(obj, R.id.F06_05_LST_COMPANIAS, "field 'lstCompanies'");
        buscarEmpresaNuevoPagoActivity.btnScanBarcode = (Button) finder.findRequiredView(obj, R.id.F06_05_BTN_ESCANEAR, "field 'btnScanBarcode'");
    }

    public static void reset(BuscarEmpresaNuevoPagoActivity buscarEmpresaNuevoPagoActivity) {
        buscarEmpresaNuevoPagoActivity.inpSearch = null;
        buscarEmpresaNuevoPagoActivity.txtDescription = null;
        buscarEmpresaNuevoPagoActivity.lstCompanies = null;
        buscarEmpresaNuevoPagoActivity.btnScanBarcode = null;
    }
}
