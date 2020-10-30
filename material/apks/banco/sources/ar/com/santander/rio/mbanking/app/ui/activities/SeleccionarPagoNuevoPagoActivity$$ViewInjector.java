package ar.com.santander.rio.mbanking.app.ui.activities;

import android.widget.LinearLayout;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import butterknife.ButterKnife.Finder;

public class SeleccionarPagoNuevoPagoActivity$$ViewInjector {
    public static void inject(Finder finder, SeleccionarPagoNuevoPagoActivity seleccionarPagoNuevoPagoActivity, Object obj) {
        seleccionarPagoNuevoPagoActivity.txtEmpresa = (TextView) finder.findRequiredView(obj, R.id.F06_07_lbl_data_empresa, "field 'txtEmpresa'");
        seleccionarPagoNuevoPagoActivity.lblCampoDinamicoIdentificacion = (TextView) finder.findRequiredView(obj, R.id.F06_07_lbl_campo_dinamico, "field 'lblCampoDinamicoIdentificacion'");
        seleccionarPagoNuevoPagoActivity.txtCampoDinamicoIdentificacion = (TextView) finder.findRequiredView(obj, R.id.F06_07_lbl_data_campo_dinamico, "field 'txtCampoDinamicoIdentificacion'");
        seleccionarPagoNuevoPagoActivity.lstFacturas = (LinearLayout) finder.findRequiredView(obj, R.id.F06_07_lnl_tableWrapper, "field 'lstFacturas'");
        seleccionarPagoNuevoPagoActivity.lblAyudaSeleccionarFactura = (TextView) finder.findRequiredView(obj, R.id.F06_07_lbl_ayudaSeleccionarFactura, "field 'lblAyudaSeleccionarFactura'");
    }

    public static void reset(SeleccionarPagoNuevoPagoActivity seleccionarPagoNuevoPagoActivity) {
        seleccionarPagoNuevoPagoActivity.txtEmpresa = null;
        seleccionarPagoNuevoPagoActivity.lblCampoDinamicoIdentificacion = null;
        seleccionarPagoNuevoPagoActivity.txtCampoDinamicoIdentificacion = null;
        seleccionarPagoNuevoPagoActivity.lstFacturas = null;
        seleccionarPagoNuevoPagoActivity.lblAyudaSeleccionarFactura = null;
    }
}
