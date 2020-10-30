package ar.com.santander.rio.mbanking.app.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.text.Html;
import android.text.InputFilter;
import android.text.InputFilter.LengthFilter;
import android.text.TextUtils;
import android.text.method.DigitsKeyListener;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.ui.constants.NuevoPagoServiciosConstants;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CuentaDebitoBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.DatosDeudaBean;
import ar.com.santander.rio.mbanking.utils.Utils.Documento.CUIT;
import butterknife.ButterKnife;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

public class AgregarPagoElectronicoNuevoPagoActivity extends AgregarPagoNuevoPagoActivity {
    public void initialize() {
        super.initialize();
        this.inpIdentificacion1.addTextChangedListener(this.mHabilitadorBotonContinuar);
        this.inpIdentificacion2.addTextChangedListener(this.mHabilitadorBotonContinuar);
    }

    public void configureLayout() {
        this.groupPagoConFactura.setVisibility(0);
        if (this.mEmpresa != null) {
            if (this.mEmpresa.identificacion1 == null || this.mEmpresa.identificacion1.isEmpty()) {
                this.groupIdentificacion1.setVisibility(8);
            } else {
                configureIdentificacion1();
            }
            if (this.mEmpresa.identificacion2 == null || this.mEmpresa.identificacion2.isEmpty()) {
                this.groupIdentificacion2.setVisibility(8);
            } else {
                configureIdentificacion2();
            }
        }
        this.groupRecargaCelular.setVisibility(8);
        this.groupPagoAfip.setVisibility(8);
    }

    /* access modifiers changed from: protected */
    public void configureIdentificacion1() {
        try {
            InputFilter[] inputFilterArr = new InputFilter[1];
            inputFilterArr[0] = new LengthFilter(this.mDeuda.validaciones.tipoId.equalsIgnoreCase("1") ? 11 : 19);
            this.inpIdentificacion1.setFilters(inputFilterArr);
            this.inpIdentificacion1.setSingleLine(true);
            this.inpIdentificacion1.setLines(2);
            this.inpIdentificacion1.setHorizontallyScrolling(false);
            this.inpIdentificacion1.setImeOptions(6);
            if (this.mDeuda.validaciones.tipoId.equalsIgnoreCase("1")) {
                this.inpIdentificacion1.setRawInputType(0);
                this.inpIdentificacion1.setKeyListener(DigitsKeyListener.getInstance("1234567890"));
            }
        } catch (Exception e) {
            Log.w("LMB", "Excepción en configureIdentificacion1()");
            e.printStackTrace();
        }
    }

    /* access modifiers changed from: protected */
    public void configureIdentificacion2() {
        int i = 1;
        try {
            InputFilter[] inputFilterArr = new InputFilter[1];
            inputFilterArr[0] = new LengthFilter(this.mDeuda.validaciones.tipoId.equalsIgnoreCase("1") ? 11 : 19);
            this.inpIdentificacion2.setFilters(inputFilterArr);
            this.inpIdentificacion2.setInputType(16385);
            this.inpIdentificacion2.setSingleLine(true);
            this.inpIdentificacion2.setLines(2);
            this.inpIdentificacion2.setHorizontallyScrolling(false);
            this.inpIdentificacion2.setImeOptions(6);
            EditText editText = this.inpIdentificacion2;
            if (this.mDeuda.validaciones.tipoId.equalsIgnoreCase("1")) {
                i = 2;
            }
            editText.setInputType(i);
        } catch (Exception e) {
            Log.w("LMB", "Excepción en configureIdentificacion2()");
            e.printStackTrace();
        }
    }

    /* access modifiers changed from: protected */
    public Boolean isDataFilled() {
        boolean z = true;
        Boolean valueOf = Boolean.valueOf(true);
        if (this.groupIdentificacion1.getVisibility() == 0) {
            valueOf = Boolean.valueOf(valueOf.booleanValue() && !TextUtils.isEmpty(this.inpIdentificacion1.getText().toString()));
        }
        if (this.groupIdentificacion2.getVisibility() != 0) {
            return valueOf;
        }
        if (!valueOf.booleanValue() || TextUtils.isEmpty(this.inpIdentificacion2.getText().toString())) {
            z = false;
        }
        return Boolean.valueOf(z);
    }

    /* access modifiers changed from: protected */
    public Boolean isDataValid() {
        this.mInvalidInputs.clear();
        if (this.groupIdentificacion1.getVisibility() == 0 && String.valueOf(this.mEmpresa.tipoEmpresa).equalsIgnoreCase("F") && String.valueOf(this.mDeuda.validaciones.tipoId).equalsIgnoreCase("1") && !CUIT.isValid(this.inpIdentificacion1.getText().toString(), this.mDeuda.validaciones.longitud, this.mDeuda.validaciones.prefijosCuit).booleanValue()) {
            this.mInvalidInputs.put(Integer.valueOf(R.id.F06_06_LBL_DATA_IDENTIFICACION1_CON_FACTURA), getString(R.string.IDxxxx_F06_XX_MSG_CUIT_INVALIDO));
        }
        if (this.groupIdentificacion2.getVisibility() == 0 && String.valueOf(this.mEmpresa.tipoEmpresa).equalsIgnoreCase("F") && String.valueOf(this.mDeuda.validaciones.tipoId).equalsIgnoreCase("1") && !CUIT.isValid(this.inpIdentificacion2.getText().toString(), this.mDeuda.validaciones.longitud, this.mDeuda.validaciones.prefijosCuit).booleanValue()) {
            this.mInvalidInputs.put(Integer.valueOf(R.id.F06_06_LBL_DATA_IDENTIFICACION2_CON_FACTURA), getString(R.string.IDxxxx_F06_XX_MSG_CUIT_INVALIDO));
        }
        return Boolean.valueOf(this.mInvalidInputs.isEmpty());
    }

    public void showAddPayment() {
        super.showAddPayment();
        if (this.groupIdentificacion1.getVisibility() == 0) {
            this.lblIdentificacion1.setText(Html.fromHtml(this.mEmpresa.identificacion1));
        }
        if (this.groupIdentificacion2.getVisibility() == 0) {
            this.lblIdentificacion2.setText(Html.fromHtml(this.mEmpresa.identificacion2));
        }
        if (!TextUtils.isEmpty(this.mEmpresa.gifFactura)) {
            this.txtAyudaIdentificacion.setText(Html.fromHtml(this.mEmpresa.gifFactura));
        }
    }

    public void showSelectPayment(List<CuentaDebitoBean> list, List<DatosDeudaBean> list2, String str) {
        Intent intent = new Intent(this, SeleccionarPagoNuevoPagoActivity.class);
        intent.putExtra(NuevoPagoServiciosConstants.EXTRA_EMPRESA, this.mEmpresa);
        String obj = this.inpIdentificacion1.getText().toString();
        if (String.valueOf(this.mEmpresa.tipoEmpresa).equalsIgnoreCase("F") && String.valueOf(this.mDeuda.validaciones.tipoId).equalsIgnoreCase("1")) {
            StringBuilder sb = new StringBuilder();
            sb.append(obj);
            sb.append(this.inpIdentificacion2.getText().toString().substring(0, this.inpIdentificacion2.getText().toString().length() - 1).substring(2));
            obj = sb.toString();
        }
        intent.putExtra(NuevoPagoServiciosConstants.EXTRA_PAGO_ELECTRONICO, obj);
        intent.putParcelableArrayListExtra(NuevoPagoServiciosConstants.EXTRA_CUENTAS, (ArrayList) list);
        intent.putParcelableArrayListExtra(NuevoPagoServiciosConstants.EXTRA_DEUDAS, (ArrayList) list2);
        intent.putExtra(NuevoPagoServiciosConstants.EXTRA_AYUDA, str);
        startActivityForResult(intent, 5);
    }

    public void showSelectPayment(List<CuentaDebitoBean> list, DatosDeudaBean datosDeudaBean) {
        Intent intent = new Intent(this, PrepareInvoicePaymentNuevoPagoActivity.class);
        intent.putExtra(NuevoPagoServiciosConstants.EXTRA_ORIGEN, NuevoPagoServiciosConstants.ORIGEN_MANUAL);
        intent.putExtra(NuevoPagoServiciosConstants.EXTRA_CUENTAS, (ArrayList) list);
        intent.putExtra(NuevoPagoServiciosConstants.EXTRA_DEUDA, datosDeudaBean);
        startActivityForResult(intent, 0);
    }

    public void onClick(View view) {
        String str;
        super.onClick(view);
        try {
            if (view.getId() == R.id.F06_06_btn_continue) {
                if (isDataValid().booleanValue()) {
                    if (!String.valueOf(this.mEmpresa.tipoEmpresa).equalsIgnoreCase("F") || !String.valueOf(this.mDeuda.validaciones.tipoId).equalsIgnoreCase("1")) {
                        str = this.inpIdentificacion1.getText().toString();
                    } else {
                        StringBuilder sb = new StringBuilder();
                        sb.append(this.inpIdentificacion1.getText().toString());
                        sb.append(this.inpIdentificacion2.getText().toString().substring(0, this.inpIdentificacion2.getText().toString().length() - 1).substring(2));
                        str = sb.toString();
                    }
                    if (this.mEmpresa.tipoPago.equalsIgnoreCase("1")) {
                        this.mDeuda.datosEmpresa = this.mEmpresa;
                        this.mDeuda.identificacion = str;
                        this.presenter.onAddPayment(this.mCuentas, this.mDeuda);
                        return;
                    }
                    this.presenter.onAddPayment(this.mEmpresa.empServ, str);
                    return;
                }
                Iterator it = this.mInvalidInputs.entrySet().iterator();
                if (it.hasNext()) {
                    Entry entry = (Entry) it.next();
                    ButterKnife.findById((Activity) this, ((Integer) entry.getKey()).intValue()).requestFocus();
                    showInvalidDataAlert((String) entry.getValue());
                }
            }
        } catch (Exception unused) {
        }
    }
}
