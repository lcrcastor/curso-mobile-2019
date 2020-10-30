package ar.com.santander.rio.mbanking.app.ui.activities;

import android.content.Intent;
import android.text.Html;
import android.text.TextUtils;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.commons.CConsDescripciones;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.app.ui.constants.NuevoPagoServiciosConstants;
import ar.com.santander.rio.mbanking.services.soap.beans.body.PagoServiciosBodyResponseBean;
import ar.com.santander.rio.mbanking.utils.UtilAccount;
import ar.com.santander.rio.mbanking.utils.UtilCurrency;
import ar.com.santander.rio.mbanking.utils.Utils.Documento.CUIT;
import butterknife.InjectView;

public class ConfirmAfipPaymentNuevoPagoActivity extends ConfirmPaymentNuevoPagoActivity {
    @InjectView(2131361945)
    TextView lblDinamico1;
    @InjectView(2131361946)
    TextView lblDinamico2;
    @InjectView(2131361947)
    TextView lblDinamico3;
    @InjectView(2131361948)
    TextView lblDinamico4;
    @InjectView(2131361929)
    TextView txtCUR;
    @InjectView(2131361931)
    TextView txtDinamico1;
    @InjectView(2131361932)
    TextView txtDinamico2;
    @InjectView(2131361933)
    TextView txtDinamico3;
    @InjectView(2131361934)
    TextView txtDinamico4;
    @InjectView(2131361936)
    TextView txtFactura;
    @InjectView(2131361938)
    TextView txtImporte;
    @InjectView(2131361939)
    TextView txtInformacionAdicional;
    @InjectView(2131361942)
    TextView txtVencimiento;

    public void configureLayout() {
        try {
            this.rowDatosAdicionales.setVisibility(8);
            this.rowVEP.setVisibility(8);
            this.rowPeriodo.setVisibility(8);
            this.rowAnticipoCuota.setVisibility(8);
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
                    if (TextUtils.isEmpty(this.mDeuda.cur)) {
                        this.rowCUR.setVisibility(8);
                    }
                    if (TextUtils.isEmpty(this.mDeuda.cuit)) {
                        this.rowCUITempleado.setVisibility(8);
                        this.rowCUITempleador.setVisibility(8);
                        break;
                    }
                    break;
                case 1:
                    this.rowInformacionAdicional.setVisibility(8);
                    if (TextUtils.isEmpty(this.mDeuda.datosEmpresa.tipoEmpresa) || !this.mDeuda.datosEmpresa.tipoEmpresa.equalsIgnoreCase("M")) {
                        this.rowCUR.setVisibility(8);
                    } else {
                        this.rowCUR.setVisibility(0);
                    }
                    if (TextUtils.isEmpty(this.mDeuda.identificacion)) {
                        this.rowCUITempleado.setVisibility(8);
                        this.rowCUITempleador.setVisibility(8);
                    }
                    if (TextUtils.isEmpty(this.mDeuda.cuitEmpleador)) {
                        this.rowCUITempleador.setVisibility(8);
                    }
                    if (this.mDeuda.identificacion.equalsIgnoreCase(this.mDeuda.cuit)) {
                        this.rowCUITempleado.setVisibility(8);
                        break;
                    }
                    break;
            }
            if (TextUtils.isEmpty(this.mDeuda.factura)) {
                this.rowFactura.setVisibility(8);
            }
            if (TextUtils.isEmpty(this.mDeuda.datosAdicionales.leyenda)) {
                this.rowDinamico1.setVisibility(8);
            }
            if (TextUtils.isEmpty(this.mDeuda.infoAdicional)) {
                this.rowInformacionAdicional.setVisibility(8);
            }
            if (TextUtils.isEmpty(this.mDeuda.identificacion)) {
                this.rowIdentificador.setVisibility(8);
            }
            if (TextUtils.isEmpty(this.mDeuda.cur)) {
                this.rowCUR.setVisibility(8);
            }
        } catch (Exception unused) {
        }
    }

    public void showConfirmarPago() {
        this.txtEmpresa.setText(TextUtils.isEmpty(this.mDeuda.empDescr) ? this.mDeuda.datosEmpresa.empDescr : this.mDeuda.empDescr);
        this.txtIdentificador.setText(this.mDeuda.identificacion);
        this.txtInformacionAdicional.setText(this.mDeuda.infoAdicional);
        this.txtImporte.setText(String.format("%s%s", new Object[]{UtilCurrency.getSimbolCurrencyFromString(this.mDeuda.moneda), UtilCurrency.getFormattedAmountInArsFromString(this.mDeuda.importe)}));
        this.txtMedioPago.setText(UtilAccount.getAbreviatureAndAccountFormat(CConsDescripciones.getConsDescripcionTPOCTACORTA(this.p), this.mCuenta.getTipo(), this.mCuenta.getSucursal(), this.mCuenta.getNumero()));
        this.txtVencimiento.setText(this.mDeuda.vencimiento);
        this.txtFactura.setText(this.mDeuda.factura);
        this.txtCUR.setText(this.mDeuda.cur);
        if (!TextUtils.isEmpty(this.mDeuda.datosAdicionales.leyenda)) {
            this.lblDinamico1.setText(Html.fromHtml(this.mDeuda.datosAdicionales.leyenda));
            this.txtDinamico1.setText(Html.fromHtml(this.mDeuda.datAdicionales));
        }
        this.txtCUITempleado.setText(CUIT.format(this.mDeuda.cuit));
        this.txtCUITempleador.setText(CUIT.format(this.mDeuda.cuitEmpleador));
        this.lblCUITempleado.setText(getString(R.string.IDX20_PAGO_SERVICIO_LBL_EMPLEADO));
        this.lblCUITempleador.setText(getString(R.string.IDX20_PAGO_SERVICIO_LBL_EMPLEADOR));
        if (this.mDeuda.datosEmpresa != null) {
            if (!TextUtils.isEmpty(this.mDeuda.datosEmpresa.identificacion1)) {
                this.lblCUITempleado.setText(this.mDeuda.datosEmpresa.identificacion1);
            }
            if (!TextUtils.isEmpty(this.mDeuda.datosEmpresa.identificacion2)) {
                this.lblCUITempleador.setText(this.mDeuda.datosEmpresa.identificacion2);
            }
        }
        try {
            this.txtEmpresa.setContentDescription(CAccessibility.getInstance(this).applyFilterGeneral(this.txtEmpresa.getText().toString()));
            this.txtIdentificador.setContentDescription(CAccessibility.getInstance(this).applyFilterCharacterToCharacter(this.txtIdentificador.getText().toString()));
            this.txtImporte.setContentDescription(CAccessibility.getInstance(this).applyFilterAmount(this.txtImporte.getText().toString()));
            this.txtMedioPago.setContentDescription(CAccessibility.getInstance(this).applyFilterAccount(this.txtMedioPago.getText().toString()));
            this.txtVencimiento.setContentDescription(CAccessibility.getInstance(this).applyFilterDate(this.txtVencimiento.getText().toString()));
            this.txtFactura.setContentDescription(CAccessibility.getInstance(this).applyFilterCharacterToCharacter(this.txtFactura.getText().toString()));
            this.txtCUITempleado.setContentDescription(CAccessibility.getInstance(this).applyFilterCharacterToCharacter(this.txtCUITempleado.getText().toString()));
            this.txtCUITempleador.setContentDescription(CAccessibility.getInstance(this).applyFilterCharacterToCharacter(this.txtCUITempleador.getText().toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showComprobantePago(PagoServiciosBodyResponseBean pagoServiciosBodyResponseBean) {
        Intent intent = new Intent(this, ComprobanteAfipNuevoPagoActivity.class);
        intent.putExtra(NuevoPagoServiciosConstants.EXTRA_ORIGEN, this.mOrigen);
        intent.putExtra(NuevoPagoServiciosConstants.EXTRA_CUENTA, this.mCuenta);
        intent.putExtra(NuevoPagoServiciosConstants.EXTRA_DEUDA, this.mDeuda);
        intent.putExtra(NuevoPagoServiciosConstants.EXTRA_PAGO, pagoServiciosBodyResponseBean);
        startActivityForResult(intent, 2);
    }
}
