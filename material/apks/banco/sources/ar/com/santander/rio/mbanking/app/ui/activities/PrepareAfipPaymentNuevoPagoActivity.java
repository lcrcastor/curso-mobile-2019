package ar.com.santander.rio.mbanking.app.ui.activities;

import android.content.Intent;
import android.text.Editable;
import android.text.Html;
import android.text.InputFilter;
import android.text.InputFilter.LengthFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import ar.com.santander.rio.mbanking.app.commons.CAmountIU;
import ar.com.santander.rio.mbanking.app.commons.CNuevoPago;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.app.ui.constants.NuevoPagoServiciosConstants;
import ar.com.santander.rio.mbanking.app.ui.constants.TarjetasConstants;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CnsEmpresaDatosEmpresa;
import ar.com.santander.rio.mbanking.services.soap.beans.body.DatosDeudaBean;
import ar.com.santander.rio.mbanking.utils.UtilAmount;
import ar.com.santander.rio.mbanking.utils.UtilCurrency;
import ar.com.santander.rio.mbanking.utils.Utils.Documento.CUIT;

public class PrepareAfipPaymentNuevoPagoActivity extends PrepararPagoServicioActivity {
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
                    if (CNuevoPago.isAmountEditable(this.mDeuda.datosEmpresa.tipoImporte, this.mDeuda.importe).booleanValue()) {
                        this.txtImporte.setVisibility(8);
                    } else {
                        this.inpImporte.setVisibility(8);
                    }
                    if (!TextUtils.isEmpty(this.mDeuda.cuit)) {
                        this.txtCuitEmpleador.setVisibility(8);
                        this.inpCuitEmpleador.setVisibility(0);
                        break;
                    } else {
                        this.rowCUITempleado.setVisibility(8);
                        this.rowCUITempleador.setVisibility(8);
                        break;
                    }
                case 1:
                    this.rowInformacionAdicional.setVisibility(8);
                    this.txtImporte.setVisibility(8);
                    if (TextUtils.isEmpty(this.mDeuda.datosEmpresa.tipoEmpresa) || !this.mDeuda.datosEmpresa.tipoEmpresa.equalsIgnoreCase("M")) {
                        this.rowCUR.setVisibility(8);
                    } else {
                        this.rowCUR.setVisibility(0);
                    }
                    if (TextUtils.isEmpty(this.mDeuda.identificacion)) {
                        this.rowCUITempleado.setVisibility(8);
                        this.rowCUITempleador.setVisibility(8);
                    } else {
                        this.txtCuitEmpleador.setVisibility(0);
                        this.inpCuitEmpleador.setVisibility(8);
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
        } catch (Exception unused) {
        }
    }

    public void showPrepararPago() {
        this.txtEmpresa.setText(this.mDeuda.datosEmpresa.empDescr);
        try {
            this.txtEmpresa.setContentDescription(CAccessibility.getInstance(this).applyFilterGeneral(this.txtEmpresa.getText().toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.txtIdentificador.setText(this.mDeuda.identificacion);
        try {
            this.txtIdentificador.setContentDescription(CAccessibility.getInstance(this).applyFilterCharacterToCharacter(this.txtIdentificador.getText().toString()));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        String str = this.mOrigen;
        char c = 65535;
        int hashCode = str.hashCode();
        if (hashCode != -1997548570) {
            if (hashCode == 1959135276 && str.equals(NuevoPagoServiciosConstants.ORIGEN_AGENDA)) {
                c = 0;
            }
        } else if (str.equals(NuevoPagoServiciosConstants.ORIGEN_MANUAL)) {
            c = 1;
        }
        if (c == 0) {
            this.inpInfoAdicional.setText(this.mDeuda.infoAdicional);
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
        this.txtVencimiento.setText(this.mDeuda.vencimiento);
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
        this.inpCUR.setText(this.mDeuda.cur);
        this.txtCuitEmpleado.setText(CUIT.format(this.mDeuda.cuit));
        if (this.mDeuda.datosEmpresa != null) {
            if (!TextUtils.isEmpty(this.mDeuda.datosEmpresa.identificacion1)) {
                this.lblCUITEmpleado.setText(this.mDeuda.datosEmpresa.identificacion1);
            }
            if (!TextUtils.isEmpty(this.mDeuda.datosEmpresa.identificacion2)) {
                this.lblCUITEmpleador.setText(this.mDeuda.datosEmpresa.identificacion2);
            }
        }
        try {
            if (this.mOrigen.equals(NuevoPagoServiciosConstants.ORIGEN_AGENDA)) {
                if (this.rowCUITempleador.getVisibility() == 0) {
                    this.inpCuitEmpleador.setFilters(new InputFilter[]{new LengthFilter(Integer.valueOf(this.mDeuda.validaciones.longitud).intValue())});
                }
            } else if (this.rowCUITempleador.getVisibility() == 0) {
                this.txtCuitEmpleador.setText(CUIT.format(this.mDeuda.cuitEmpleador));
            }
        } catch (Exception unused) {
        }
        if (this.rowDinamico1.getVisibility() == 0) {
            this.lblDinamico1.setText(Html.fromHtml(this.mDeuda.datosAdicionales.leyenda));
            this.inpDinamico1.setHint(Html.fromHtml(this.mDeuda.datosAdicionales.ejemplo));
            this.inpDinamico1.setFilters(new InputFilter[]{new LengthFilter(7)});
            if (this.mDeuda.datosEmpresa.tipoEmpresa.equalsIgnoreCase("M") || (!this.mDeuda.datosEmpresa.tipoEmpresa.equalsIgnoreCase("M") && (this.mDeuda.datosEmpresa.codDatAdic.equalsIgnoreCase("2") || this.mDeuda.datosEmpresa.codDatAdic.equalsIgnoreCase("3")))) {
                this.inpDinamico1.setRawInputType(0);
                this.inpDinamico1.setKeyListener(DigitsKeyListener.getInstance("1234567890-"));
                this.inpDinamico1.addTextChangedListener(new TextWatcher() {
                    int a = 0;
                    CharSequence b = "";

                    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                    }

                    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                        this.b = PrepareAfipPaymentNuevoPagoActivity.this.inpDinamico1.getText().toString().replace("-", "");
                    }

                    public void afterTextChanged(Editable editable) {
                        PrepareAfipPaymentNuevoPagoActivity.this.inpDinamico1.removeTextChangedListener(this);
                        int selectionStart = PrepareAfipPaymentNuevoPagoActivity.this.inpDinamico1.getSelectionStart();
                        String replace = editable.toString().replace("-", "");
                        if (replace.length() > 2) {
                            StringBuilder sb = new StringBuilder();
                            sb.append(replace.substring(0, 2));
                            sb.append("-");
                            sb.append(replace.substring(2));
                            replace = sb.toString();
                            if (this.b.length() < replace.length() && selectionStart == 3) {
                                selectionStart = 4;
                            }
                        } else if (replace.length() == 2) {
                            selectionStart = this.b.length() < replace.length() ? 3 : 2;
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append(replace);
                            sb2.append("-");
                            replace = sb2.toString();
                        }
                        PrepareAfipPaymentNuevoPagoActivity.this.inpDinamico1.setText(replace);
                        PrepareAfipPaymentNuevoPagoActivity.this.inpDinamico1.setSelection(selectionStart);
                        PrepareAfipPaymentNuevoPagoActivity.this.inpDinamico1.addTextChangedListener(this);
                    }
                });
                return;
            }
            this.inpDinamico1.setRawInputType(0);
            this.inpDinamico1.setKeyListener(DigitsKeyListener.getInstance("1234567890"));
        }
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
        if (this.mOrigen.equals(NuevoPagoServiciosConstants.ORIGEN_AGENDA)) {
            if (!TextUtils.isEmpty(this.mDeuda.cur) && !isCurValid(this.inpCUR.getText().toString()).booleanValue()) {
                return 4;
            }
        } else if (this.mOrigen.equals(NuevoPagoServiciosConstants.ORIGEN_MANUAL) && this.mDeuda.datosEmpresa.tipoEmpresa != null && this.mDeuda.datosEmpresa.tipoEmpresa.equalsIgnoreCase("M") && !String.valueOf(this.mDeuda.cur).equals(String.valueOf(this.inpCUR.getText().toString())) && !isCurValid(this.inpCUR.getText().toString()).booleanValue()) {
            return 4;
        }
        if (this.mOrigen.equals(NuevoPagoServiciosConstants.ORIGEN_AGENDA) && !TextUtils.isEmpty(this.mDeuda.cuit) && !String.valueOf(this.mDeuda.cuitEmpleador).equals(String.valueOf(this.inpCuitEmpleador.getText().toString())) && !CUIT.isValid(this.inpCuitEmpleador.getText().toString(), this.mDeuda.validaciones.longitud, this.mDeuda.validaciones.prefijosCuit).booleanValue()) {
            return 3;
        }
        if (this.rowDinamico1.getVisibility() == 0) {
            if (this.mDeuda.datosEmpresa.tipoEmpresa == null) {
                this.mDeuda.datosEmpresa.tipoEmpresa = "";
            }
            if (!isDinamicFieldValid(this.inpDinamico1.getText().toString(), this.mDeuda.datosEmpresa.tipoEmpresa, this.mDeuda.datosEmpresa.codDatAdic, this.mDeuda.datosAdicionales.rangoDesde, this.mDeuda.datosAdicionales.rangoHasta, this.mDeuda.datosAdicionales.fechaDesde, this.mDeuda.datosAdicionales.fechaHasta).booleanValue()) {
                return 5;
            }
        }
        return this.mCuentaSeleccionada == null ? 6 : 0;
    }

    public void showConfirmarPago() {
        String obj = CNuevoPago.isAmountEditable(this.mDeuda.datosEmpresa.tipoImporte, this.mDeuda.importe).booleanValue() ? this.inpImporte.getText().toString() : this.txtImporte.getText().toString();
        String valueOf = String.valueOf(this.inpCuitEmpleador.getText());
        String obj2 = this.inpDinamico1.getText().toString();
        String obj3 = this.inpCUR.getText().toString();
        String str = this.mOrigen.equalsIgnoreCase(NuevoPagoServiciosConstants.ORIGEN_AGENDA) ? this.inpInfoAdicional.getText().toString() : TextUtils.isEmpty(this.mDeuda.infoAdicional) ? "" : this.mDeuda.infoAdicional;
        String str2 = str;
        Intent intent = new Intent(this, ConfirmAfipPaymentNuevoPagoActivity.class);
        intent.putExtra(NuevoPagoServiciosConstants.EXTRA_ORIGEN, this.mOrigen);
        intent.putExtra(NuevoPagoServiciosConstants.EXTRA_CUENTA, this.mCuentaSeleccionada);
        String str3 = NuevoPagoServiciosConstants.EXTRA_DEUDA;
        String str4 = this.mDeuda.empServ;
        String str5 = this.mDeuda.empDescr;
        String str6 = this.mDeuda.identificacion;
        String str7 = this.mDeuda.tipoImporte;
        String valueOf2 = String.valueOf(CAmountIU.getInstance().getDoubleFromInputUser(obj));
        String str8 = this.mDeuda.moneda;
        String str9 = this.mDeuda.factura;
        String str10 = this.mDeuda.vencimiento;
        if (obj3.isEmpty()) {
            obj3 = this.mDeuda.cur;
        }
        String str11 = this.mDeuda.numVep;
        String str12 = this.mDeuda.cuit;
        if (valueOf.isEmpty()) {
            valueOf = this.mDeuda.cuitEmpleador;
        }
        String str13 = valueOf;
        String str14 = this.mDeuda.periodo;
        String str15 = str12;
        String str16 = this.mDeuda.anticipoCuota;
        String str17 = this.mDeuda.datosAdic;
        CnsEmpresaDatosEmpresa cnsEmpresaDatosEmpresa = this.mDeuda.datosEmpresa;
        DatosDeudaBean datosDeudaBean = r4;
        String str18 = str10;
        String str19 = str3;
        String str20 = obj3;
        Intent intent2 = intent;
        DatosDeudaBean datosDeudaBean2 = new DatosDeudaBean(str4, str5, str6, str7, valueOf2, str8, str9, str18, str2, str20, str11, str15, str13, str14, str16, str17, cnsEmpresaDatosEmpresa, this.mDeuda.datosAdicionales, this.mDeuda.validaciones, obj2);
        intent2.putExtra(str19, datosDeudaBean);
        intent2.putExtra("EMPLEADOR", this.mDeuda.cuitEmpleador);
        startActivityForResult(intent2, 1);
    }
}
