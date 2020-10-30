package ar.com.santander.rio.mbanking.app.ui.activities;

import android.content.Intent;
import android.text.Html;
import android.text.TextUtils;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.app.commons.CConsDescripciones;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.app.ui.constants.NuevoPagoServiciosConstants;
import ar.com.santander.rio.mbanking.services.soap.beans.body.PagoServiciosBodyResponseBean;
import ar.com.santander.rio.mbanking.utils.UtilAccount;
import ar.com.santander.rio.mbanking.utils.UtilCurrency;
import butterknife.InjectView;

public class ConfirmNoInvoicePaymentNuevoPagoActivity extends ConfirmPaymentNuevoPagoActivity {
    @InjectView(2131361944)
    TextView lblDatosAdicionales;
    @InjectView(2131361930)
    TextView txtDatosAdicionales;
    @InjectView(2131361936)
    TextView txtFactura;
    @InjectView(2131361937)
    TextView txtIdentificador;
    @InjectView(2131361938)
    TextView txtImporte;
    @InjectView(2131361939)
    TextView txtInformacionAdicional;
    @InjectView(2131361942)
    TextView txtVencimiento;

    public void configureLayout() {
        try {
            this.rowVEP.setVisibility(8);
            this.rowCUITempleado.setVisibility(8);
            this.rowCUITempleador.setVisibility(8);
            this.rowPeriodo.setVisibility(8);
            this.rowAnticipoCuota.setVisibility(8);
            this.rowCUR.setVisibility(8);
            this.rowDinamico1.setVisibility(8);
            this.rowDinamico2.setVisibility(8);
            this.rowDinamico3.setVisibility(8);
            this.rowDinamico4.setVisibility(8);
            String str = this.mOrigen;
            char c = 65535;
            int hashCode = str.hashCode();
            if (hashCode != -1997548570) {
                if (hashCode == 1959135276) {
                    if (str.equals(NuevoPagoServiciosConstants.ORIGEN_AGENDA)) {
                        c = 0;
                    }
                }
            } else if (str.equals(NuevoPagoServiciosConstants.ORIGEN_MANUAL)) {
                c = 1;
            }
            switch (c) {
                case 0:
                    if (TextUtils.isEmpty(this.mDeuda.datosAdicionales.leyenda) || TextUtils.isEmpty(this.mDeuda.datosAdicionales.ejemplo)) {
                        this.rowDatosAdicionales.setVisibility(8);
                    }
                    if (TextUtils.isEmpty(this.mDeuda.infoAdicional)) {
                        this.rowInformacionAdicional.setVisibility(8);
                        break;
                    }
                    break;
                case 1:
                    this.rowInformacionAdicional.setVisibility(8);
                    this.rowDatosAdicionales.setVisibility(8);
                    break;
            }
            if (TextUtils.isEmpty(this.mDeuda.factura)) {
                this.rowFactura.setVisibility(8);
            }
        } catch (Exception unused) {
        }
    }

    public void showConfirmarPago() {
        this.txtEmpresa.setText(this.mDeuda.datosEmpresa.empDescr);
        try {
            this.txtEmpresa.setContentDescription(CAccessibility.getInstance(this).applyFilterGeneral(this.txtEmpresa.getText().toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.txtInformacionAdicional.setText(this.mDeuda.infoAdicional);
        this.txtIdentificador.setText(this.mDeuda.identificacion);
        this.txtImporte.setText(String.format("%s%s", new Object[]{UtilCurrency.getSimbolCurrencyFromString(this.mDeuda.moneda), UtilCurrency.getFormattedAmountInArsFromString(this.mDeuda.importe)}));
        this.txtMedioPago.setText(UtilAccount.getAbreviatureAndAccountFormat(CConsDescripciones.getConsDescripcionTPOCTACORTA(this.p), this.mCuenta.getTipo(), this.mCuenta.getSucursal(), this.mCuenta.getNumero()));
        try {
            this.txtMedioPago.setContentDescription(CAccessibility.getInstance(this).applyFilterAccount(this.txtMedioPago.getText().toString()));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        this.txtVencimiento.setText(this.mDeuda.vencimiento);
        this.txtFactura.setText(this.mDeuda.factura);
        try {
            this.txtFactura.setContentDescription(CAccessibility.getInstance(this).applyFilterCharacterToCharacter(this.txtFactura.getText().toString()));
        } catch (Exception e3) {
            e3.printStackTrace();
        }
        if (this.rowDatosAdicionales.getVisibility() == 0) {
            this.lblDatosAdicionales.setText(Html.fromHtml(this.mDeuda.datosAdicionales.leyenda));
            this.txtDatosAdicionales.setText(Html.fromHtml(this.mDeuda.datosAdicionales.ejemplo));
        }
    }

    public void showComprobantePago(PagoServiciosBodyResponseBean pagoServiciosBodyResponseBean) {
        Intent intent = new Intent(this, ComprobanteSinFacturaNuevoPagoActivity.class);
        intent.putExtra(NuevoPagoServiciosConstants.EXTRA_ORIGEN, this.mOrigen);
        intent.putExtra(NuevoPagoServiciosConstants.EXTRA_CUENTA, this.mCuenta);
        intent.putExtra(NuevoPagoServiciosConstants.EXTRA_DEUDA, this.mDeuda);
        intent.putExtra(NuevoPagoServiciosConstants.EXTRA_PAGO, pagoServiciosBodyResponseBean);
        startActivityForResult(intent, 2);
    }
}
