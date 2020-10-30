package ar.com.santander.rio.mbanking.app.ui.activities;

import android.content.Intent;
import android.text.TextUtils;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.commons.CAmountIU;
import ar.com.santander.rio.mbanking.app.commons.CNuevoPago;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.app.ui.Constants;
import ar.com.santander.rio.mbanking.app.ui.constants.NuevoPagoServiciosConstants;
import ar.com.santander.rio.mbanking.app.ui.constants.TarjetasConstants;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CnsEmpresaDatosEmpresa;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CnsEmpresaValidaciones;
import ar.com.santander.rio.mbanking.services.soap.beans.body.DatosAdicionalesDeudaBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.DatosDeudaBean;
import ar.com.santander.rio.mbanking.utils.UtilAmount;
import ar.com.santander.rio.mbanking.utils.UtilCurrency;
import ar.com.santander.rio.mbanking.utils.UtilDate;
import ar.com.santander.rio.mbanking.utils.Utils.Documento.CUIT;

public class PrepareInvoicePaymentNuevoPagoActivity extends PrepararPagoServicioActivity {
    public void configureLayout() {
        this.rowDinamico1.setVisibility(8);
        this.rowDinamico2.setVisibility(8);
        this.rowDinamico3.setVisibility(8);
        this.rowDinamico4.setVisibility(8);
        this.rowCUR.setVisibility(8);
        this.rowCUITempleador.setVisibility(8);
        if (TextUtils.isEmpty(this.mDeuda.cuit)) {
            this.rowCUITempleado.setVisibility(8);
            this.lblCUITEmpleado.setText(getString(R.string.IDX20_PAGO_SERVICIO_LBL_EMPLEADO));
        } else {
            this.rowCUITempleado.setVisibility(0);
            this.lblCUITEmpleado.setText(getString(R.string.IDX19_PAGO_SERVICIO_LBL_CUIT));
        }
        if (TextUtils.isEmpty(this.mDeuda.factura)) {
            this.rowFactura.setVisibility(8);
        }
        if (CNuevoPago.isAmountEditable(this.mDeuda.datosEmpresa.tipoImporte, this.mDeuda.importe).booleanValue()) {
            this.txtImporte.setVisibility(8);
            this.inpImporte.setVisibility(0);
        } else {
            this.txtImporte.setVisibility(0);
            this.inpImporte.setVisibility(8);
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

    public void showPrepararPago() {
        this.txtEmpresa.setText(this.mDeuda.datosEmpresa.empDescr);
        try {
            this.txtEmpresa.setContentDescription(CAccessibility.getInstance(this).applyFilterGeneral(this.txtEmpresa.getText().toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (this.rowInformacionAdicional.getVisibility() == 0) {
            this.inpInfoAdicional.setText(this.mDeuda.infoAdicional);
        }
        this.txtIdentificador.setText(this.mDeuda.identificacion);
        try {
            this.txtIdentificador.setContentDescription(CAccessibility.getInstance(this).applyFilterCharacterToCharacter(this.txtIdentificador.getText().toString()));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        if (CNuevoPago.isAmountEditable(this.mDeuda.datosEmpresa.tipoImporte, this.mDeuda.importe).booleanValue()) {
            this.inpImporte.setHint(TarjetasConstants.FORMATTED_ZERO);
            if (UtilAmount.getAmount(this.mDeuda.importe) > 0.0d) {
                this.inpImporte.setText(UtilCurrency.getFormattedAmountInArsFromString(this.mDeuda.importe));
            }
        } else {
            this.txtImporte.setText(String.format("%s%s", new Object[]{UtilCurrency.getSimbolCurrencyFromString(this.mDeuda.moneda), UtilCurrency.getFormattedAmountInArsFromString(this.mDeuda.importe)}));
            try {
                this.txtImporte.setContentDescription(CAccessibility.getInstance(this).applyFilterAmount(this.txtImporte.getText().toString()));
            } catch (Exception e3) {
                e3.printStackTrace();
            }
        }
        if (this.mCuentaSeleccionada != null) {
            this.txtMedioPago.setText(formatMedioPago(this.mCuentaSeleccionada.getDescCtaDebito()));
            try {
                this.txtMedioPago.setContentDescription(formatAccessibilityMedioPago(this.mCuentaSeleccionada.getDescCtaDebito()));
            } catch (Exception e4) {
                e4.printStackTrace();
            }
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
        this.txtVep.setText(this.mDeuda.numVep);
        try {
            this.txtVep.setContentDescription(CAccessibility.getInstance(this).applyFilterCharacterToCharacter(this.txtVep.getText().toString()));
        } catch (Exception e7) {
            e7.printStackTrace();
        }
        this.txtCuitEmpleado.setText(CUIT.format(this.mDeuda.cuit));
        this.txtPeriodo.setText(this.mDeuda.periodo);
        if (this.rowDatosAdicionales.getVisibility() == 0) {
            this.txtDatosAdicionales.setText(this.mDeuda.datosAdic);
        }
        this.txtAnticipoCuota.setText(this.mDeuda.anticipoCuota);
    }

    /* access modifiers changed from: protected */
    public int isDataValid() {
        if (CNuevoPago.isAmountEditable(this.mDeuda.datosEmpresa.tipoImporte, this.mDeuda.importe).booleanValue()) {
            if (TextUtils.isEmpty(this.inpImporte.getText().toString()) || CAmountIU.getInstance().getDoubleFromInputUser(this.inpImporte.getText().toString()).doubleValue() <= 0.0d) {
                return 1;
            }
            if (Integer.parseInt(this.mDeuda.datosEmpresa.tipoImporte) == 1 && CAmountIU.getInstance().getDoubleFromInputUser(this.inpImporte.getText().toString()).doubleValue() < CAmountIU.getInstance().getDoubleFromInputUser(this.mDeuda.importe).doubleValue()) {
                return 2;
            }
        }
        return this.mCuentaSeleccionada == null ? 6 : 0;
    }

    public void showConfirmarPago() {
        String obj = this.rowInformacionAdicional.getVisibility() == 0 ? this.inpInfoAdicional.getText().toString() : "";
        String obj2 = CNuevoPago.isAmountEditable(this.mDeuda.datosEmpresa.tipoImporte, this.mDeuda.importe).booleanValue() ? this.inpImporte.getText().toString() : this.txtImporte.getText().toString();
        Intent intent = new Intent(this, ConfirmInvoicePaymentNuevoPagoActivity.class);
        intent.putExtra(NuevoPagoServiciosConstants.EXTRA_ORIGEN, this.mOrigen);
        intent.putExtra(NuevoPagoServiciosConstants.EXTRA_CUENTA, this.mCuentaSeleccionada);
        intent.putExtra(NuevoPagoServiciosConstants.EXTRA_DEUDA, this.mDeuda);
        intent.putExtra(NuevoPagoServiciosConstants.EXTRA_CUENTA, this.mCuentaSeleccionada);
        String str = NuevoPagoServiciosConstants.EXTRA_DEUDA;
        String str2 = this.mDeuda.empServ;
        String str3 = this.mDeuda.empDescr;
        String str4 = this.mDeuda.identificacion;
        String str5 = this.mDeuda.tipoImporte;
        String valueOf = String.valueOf(CAmountIU.getInstance().getDoubleFromInputUser(obj2));
        String str6 = this.mDeuda.moneda;
        String str7 = this.mDeuda.factura;
        String str8 = this.mDeuda.vencimiento;
        String str9 = this.mDeuda.cur;
        String str10 = this.mDeuda.numVep;
        String str11 = this.mDeuda.cuit;
        String str12 = this.mDeuda.periodo;
        String str13 = this.mDeuda.anticipoCuota;
        String str14 = this.mDeuda.datosAdic;
        CnsEmpresaDatosEmpresa cnsEmpresaDatosEmpresa = this.mDeuda.datosEmpresa;
        DatosAdicionalesDeudaBean datosAdicionalesDeudaBean = this.mDeuda.datosAdicionales;
        CnsEmpresaValidaciones cnsEmpresaValidaciones = this.mDeuda.validaciones;
        String str15 = str10;
        String str16 = str;
        DatosDeudaBean datosDeudaBean = r2;
        Intent intent2 = intent;
        DatosDeudaBean datosDeudaBean2 = new DatosDeudaBean(str2, str3, str4, str5, valueOf, str6, str7, str8, obj, str9, str15, str11, str12, str13, str14, cnsEmpresaDatosEmpresa, datosAdicionalesDeudaBean, cnsEmpresaValidaciones, this.mDeuda.datAdicionales);
        intent2.putExtra(str16, datosDeudaBean);
        startActivityForResult(intent2, 1);
    }
}
