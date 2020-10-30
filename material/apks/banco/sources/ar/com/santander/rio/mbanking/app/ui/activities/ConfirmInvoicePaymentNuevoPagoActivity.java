package ar.com.santander.rio.mbanking.app.ui.activities;

import android.content.Intent;
import android.text.TextUtils;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.commons.CConsDescripciones;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.app.ui.Constants;
import ar.com.santander.rio.mbanking.app.ui.constants.NuevoPagoServiciosConstants;
import ar.com.santander.rio.mbanking.services.soap.beans.body.PagoServiciosBodyResponseBean;
import ar.com.santander.rio.mbanking.utils.UtilAccount;
import ar.com.santander.rio.mbanking.utils.UtilCurrency;
import ar.com.santander.rio.mbanking.utils.UtilDate;
import ar.com.santander.rio.mbanking.utils.Utils.Documento.CUIT;

public class ConfirmInvoicePaymentNuevoPagoActivity extends ConfirmPaymentNuevoPagoActivity {
    public void configureLayout() {
        this.rowDinamico1.setVisibility(8);
        this.rowDinamico2.setVisibility(8);
        this.rowDinamico3.setVisibility(8);
        this.rowDinamico4.setVisibility(8);
        this.rowCUR.setVisibility(8);
        this.rowCUITempleador.setVisibility(8);
        if (TextUtils.isEmpty(this.mDeuda.cuit)) {
            this.rowCUITempleado.setVisibility(8);
            this.lblCUITempleado.setText(getString(R.string.IDX20_PAGO_SERVICIO_LBL_EMPLEADO));
        } else {
            this.rowCUITempleado.setVisibility(0);
            this.lblCUITempleado.setText(getString(R.string.IDX19_PAGO_SERVICIO_LBL_CUIT));
        }
        if (TextUtils.isEmpty(this.mDeuda.factura)) {
            this.rowFactura.setVisibility(8);
        }
        if (TextUtils.isEmpty(this.mDeuda.numVep)) {
            this.rowVEP.setVisibility(8);
        }
        if (TextUtils.isEmpty(this.mDeuda.cuit)) {
            this.rowCUITempleado.setVisibility(8);
        }
        if (TextUtils.isEmpty(this.mDeuda.periodo)) {
            this.rowPeriodo.setVisibility(8);
        }
        if (TextUtils.isEmpty(this.mDeuda.anticipoCuota)) {
            this.rowAnticipoCuota.setVisibility(8);
        }
        if (TextUtils.isEmpty(this.mDeuda.datosAdic)) {
            this.rowDatosAdicionales.setVisibility(8);
        }
        if (this.mOrigen.equalsIgnoreCase(NuevoPagoServiciosConstants.ORIGEN_AGENDA) && TextUtils.isEmpty(this.mDeuda.infoAdicional)) {
            this.rowInformacionAdicional.setVisibility(8);
        }
        if (this.mOrigen.equalsIgnoreCase(NuevoPagoServiciosConstants.ORIGEN_MANUAL)) {
            this.rowInformacionAdicional.setVisibility(8);
        }
        if (this.mOrigen.equalsIgnoreCase(NuevoPagoServiciosConstants.ORIGEN_CB)) {
            this.rowInformacionAdicional.setVisibility(8);
            this.rowVEP.setVisibility(8);
            this.rowCUITempleado.setVisibility(8);
            this.rowPeriodo.setVisibility(8);
            this.rowAnticipoCuota.setVisibility(8);
        }
    }

    public void showConfirmarPago() {
        this.txtEmpresa.setText(this.mDeuda.datosEmpresa.empDescr);
        try {
            this.txtEmpresa.setContentDescription(CAccessibility.getInstance(this).applyFilterGeneral(this.txtEmpresa.getText().toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (this.rowInformacionAdicional.getVisibility() == 0) {
            this.txtInformacionAdicional.setText(this.mDeuda.infoAdicional);
        }
        this.txtIdentificador.setText(this.mDeuda.identificacion);
        try {
            this.txtIdentificador.setContentDescription(CAccessibility.getInstance(this).applyFilterCharacterToCharacter(this.txtIdentificador.getText().toString()));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        this.txtImporte.setText(String.format("%s%s", new Object[]{UtilCurrency.getSimbolCurrencyFromString(this.mDeuda.moneda), UtilCurrency.getFormattedAmountInArsFromString(this.mDeuda.importe)}));
        try {
            this.txtImporte.setContentDescription(CAccessibility.getInstance(this).applyFilterAmount(this.txtImporte.getText().toString()));
        } catch (Exception e3) {
            e3.printStackTrace();
        }
        this.txtMedioPago.setText(UtilAccount.getAbreviatureAndAccountFormat(CConsDescripciones.getConsDescripcionTPOCTACORTA(this.p), this.mCuenta.getTipo(), this.mCuenta.getSucursal(), this.mCuenta.getNumero()));
        try {
            this.txtMedioPago.setContentDescription(CAccessibility.getInstance(this).applyFilterAccount(this.txtMedioPago.getText().toString()));
        } catch (Exception e4) {
            e4.printStackTrace();
        }
        this.txtVencimiento.setText(UtilDate.getDateFormat(this.mDeuda.vencimiento, Constants.FORMAT_DATE_WS_3, Constants.FORMAT_DATE_APP));
        try {
            this.txtVencimiento.setContentDescription(CAccessibility.getInstance(this).applyFilterDate(this.txtVencimiento.getText().toString()));
        } catch (Exception e5) {
            e5.printStackTrace();
        }
        this.txtFactura.setText(this.mDeuda.factura);
        try {
            this.txtFactura.setContentDescription(CAccessibility.getInstance(this).applyFilterCharacterToCharacter(this.txtFactura.getText().toString()));
        } catch (Exception e6) {
            e6.printStackTrace();
        }
        this.txtVEP.setText(this.mDeuda.numVep);
        try {
            this.txtVEP.setContentDescription(CAccessibility.getInstance(this).applyFilterCharacterToCharacter(this.txtVEP.getText().toString()));
        } catch (Exception e7) {
            e7.printStackTrace();
        }
        this.txtCUITempleado.setText(CUIT.format(this.mDeuda.cuit));
        this.txtPeriodo.setText(this.mDeuda.periodo);
        if (this.rowDatosAdicionales.getVisibility() == 0) {
            this.txtDatosAdicionales.setText(this.mDeuda.datosAdic);
        }
        this.txtAnticipoCuota.setText(this.mDeuda.anticipoCuota);
    }

    public void showComprobantePago(PagoServiciosBodyResponseBean pagoServiciosBodyResponseBean) {
        Intent intent = new Intent(this, ComprobanteConFacturaNuevoPagoActivity.class);
        intent.putExtra(NuevoPagoServiciosConstants.EXTRA_ORIGEN, this.mOrigen);
        intent.putExtra(NuevoPagoServiciosConstants.EXTRA_CUENTA, this.mCuenta);
        intent.putExtra(NuevoPagoServiciosConstants.EXTRA_DEUDA, this.mDeuda);
        intent.putExtra(NuevoPagoServiciosConstants.EXTRA_PAGO, pagoServiciosBodyResponseBean);
        startActivityForResult(intent, 2);
    }
}
