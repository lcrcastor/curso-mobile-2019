package ar.com.santander.rio.mbanking.app.ui.activities;

import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import butterknife.ButterKnife.Finder;

public class ConfirmNoInvoicePaymentNuevoPagoActivity$$ViewInjector {
    public static void inject(Finder finder, ConfirmNoInvoicePaymentNuevoPagoActivity confirmNoInvoicePaymentNuevoPagoActivity, Object obj) {
        ConfirmPaymentNuevoPagoActivity$$ViewInjector.inject(finder, confirmNoInvoicePaymentNuevoPagoActivity, obj);
        confirmNoInvoicePaymentNuevoPagoActivity.txtInformacionAdicional = (TextView) finder.findRequiredView(obj, R.id.F06_03_LBL_DATA_INF_ADICIONAL, "field 'txtInformacionAdicional'");
        confirmNoInvoicePaymentNuevoPagoActivity.txtIdentificador = (TextView) finder.findRequiredView(obj, R.id.F06_03_LBL_DATA_IDENTIFICADOR, "field 'txtIdentificador'");
        confirmNoInvoicePaymentNuevoPagoActivity.txtImporte = (TextView) finder.findRequiredView(obj, R.id.F06_03_LBL_DATA_IMPORTE, "field 'txtImporte'");
        confirmNoInvoicePaymentNuevoPagoActivity.txtVencimiento = (TextView) finder.findRequiredView(obj, R.id.F06_03_LBL_DATA_VENCIMIENTO, "field 'txtVencimiento'");
        confirmNoInvoicePaymentNuevoPagoActivity.txtFactura = (TextView) finder.findRequiredView(obj, R.id.F06_03_LBL_DATA_FACTURA, "field 'txtFactura'");
        confirmNoInvoicePaymentNuevoPagoActivity.lblDatosAdicionales = (TextView) finder.findRequiredView(obj, R.id.F06_03_LBL_DATOS_ADICIONALES, "field 'lblDatosAdicionales'");
        confirmNoInvoicePaymentNuevoPagoActivity.txtDatosAdicionales = (TextView) finder.findRequiredView(obj, R.id.F06_03_LBL_DATA_DATOS_ADICIONALES, "field 'txtDatosAdicionales'");
    }

    public static void reset(ConfirmNoInvoicePaymentNuevoPagoActivity confirmNoInvoicePaymentNuevoPagoActivity) {
        ConfirmPaymentNuevoPagoActivity$$ViewInjector.reset(confirmNoInvoicePaymentNuevoPagoActivity);
        confirmNoInvoicePaymentNuevoPagoActivity.txtInformacionAdicional = null;
        confirmNoInvoicePaymentNuevoPagoActivity.txtIdentificador = null;
        confirmNoInvoicePaymentNuevoPagoActivity.txtImporte = null;
        confirmNoInvoicePaymentNuevoPagoActivity.txtVencimiento = null;
        confirmNoInvoicePaymentNuevoPagoActivity.txtFactura = null;
        confirmNoInvoicePaymentNuevoPagoActivity.lblDatosAdicionales = null;
        confirmNoInvoicePaymentNuevoPagoActivity.txtDatosAdicionales = null;
    }
}
