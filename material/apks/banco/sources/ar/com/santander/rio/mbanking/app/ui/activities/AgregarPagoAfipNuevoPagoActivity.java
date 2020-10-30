package ar.com.santander.rio.mbanking.app.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.text.Html;
import android.text.InputFilter;
import android.text.InputFilter.LengthFilter;
import android.text.TextUtils;
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

public class AgregarPagoAfipNuevoPagoActivity extends AgregarPagoNuevoPagoActivity {
    public void showSelectPayment(List<CuentaDebitoBean> list, List<DatosDeudaBean> list2, String str) {
    }

    public void initialize() {
        super.initialize();
        this.inpAfipIdentificacion1.addTextChangedListener(this.mHabilitadorBotonContinuar);
        this.inpAfipIdentificacion2.addTextChangedListener(this.mHabilitadorBotonContinuar);
    }

    public void configureLayout() {
        this.groupPagoConFactura.setVisibility(8);
        this.groupRecargaCelular.setVisibility(8);
        this.groupPagoAfip.setVisibility(0);
        if (this.mEmpresa != null) {
            if (this.mEmpresa.identificacion1 == null || this.mEmpresa.identificacion1.isEmpty()) {
                this.groupAfipIdentificacion1.setVisibility(8);
            } else {
                configureIdentificacion1();
            }
            if (this.mEmpresa.identificacion2 == null || this.mEmpresa.identificacion2.isEmpty()) {
                this.groupAfipIdentificacion2.setVisibility(8);
            } else {
                configureIdentificacion2();
            }
        }
    }

    /* access modifiers changed from: protected */
    public void configureIdentificacion1() {
        int i = 1;
        try {
            this.inpAfipIdentificacion1.setFilters(new InputFilter[]{new LengthFilter(Integer.parseInt(this.mDeuda.validaciones.longitud))});
            this.inpAfipIdentificacion1.setSingleLine(true);
            this.inpAfipIdentificacion1.setLines(2);
            this.inpAfipIdentificacion1.setHorizontallyScrolling(false);
            this.inpAfipIdentificacion1.setImeOptions(6);
            EditText editText = this.inpAfipIdentificacion1;
            if (this.mDeuda.validaciones.tipoId.equalsIgnoreCase("1")) {
                i = 2;
            }
            editText.setInputType(i);
        } catch (Exception e) {
            Log.w("LMB", "Excepción en configureIdentificacion1()");
            e.printStackTrace();
        }
    }

    /* access modifiers changed from: protected */
    public void configureIdentificacion2() {
        int i = 1;
        try {
            this.inpAfipIdentificacion2.setFilters(new InputFilter[]{new LengthFilter(Integer.parseInt(this.mDeuda.validaciones.longitud))});
            this.inpAfipIdentificacion2.setSingleLine(true);
            this.inpAfipIdentificacion2.setLines(2);
            this.inpAfipIdentificacion2.setHorizontallyScrolling(false);
            this.inpAfipIdentificacion2.setImeOptions(6);
            EditText editText = this.inpAfipIdentificacion2;
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
        if (this.groupAfipIdentificacion1.getVisibility() == 0) {
            valueOf = Boolean.valueOf(valueOf.booleanValue() && !TextUtils.isEmpty(this.inpAfipIdentificacion1.getText().toString()));
        }
        if (this.groupAfipIdentificacion2.getVisibility() != 0) {
            return valueOf;
        }
        if (!valueOf.booleanValue() || TextUtils.isEmpty(this.inpAfipIdentificacion2.getText().toString())) {
            z = false;
        }
        return Boolean.valueOf(z);
    }

    /* access modifiers changed from: protected */
    public Boolean isDataValid() {
        this.mInvalidInputs.clear();
        if (this.groupAfipIdentificacion1.getVisibility() == 0 && this.mDeuda.validaciones.tipoId.equalsIgnoreCase("1") && !CUIT.isValid(this.inpAfipIdentificacion1.getText().toString(), this.mDeuda.validaciones.longitud, this.mDeuda.validaciones.prefijosCuit).booleanValue()) {
            this.mInvalidInputs.put(Integer.valueOf(R.id.F06_06_LBL_DATA_IDENTIFICACION1_AFIP), getString(R.string.IDxxxx_F06_XX_MSG_CUIT_INVALIDO));
        }
        if (this.groupAfipIdentificacion2.getVisibility() == 0 && this.mDeuda.validaciones.tipoId.equalsIgnoreCase("1") && !CUIT.isValid(this.inpAfipIdentificacion2.getText().toString(), this.mDeuda.validaciones.longitud, this.mDeuda.validaciones.prefijosCuit).booleanValue()) {
            this.mInvalidInputs.put(Integer.valueOf(R.id.F06_06_LBL_DATA_IDENTIFICACION2_AFIP), getString(R.string.IDxxxx_F06_XX_MSG_CUIT_INVALIDO));
        }
        return Boolean.valueOf(this.mInvalidInputs.isEmpty());
    }

    public void showAddPayment() {
        super.showAddPayment();
        if (this.groupAfipIdentificacion1.getVisibility() == 0) {
            this.lblAfipIdentificacion1.setText(Html.fromHtml(this.mEmpresa.identificacion1));
        }
        if (this.groupAfipIdentificacion2.getVisibility() == 0) {
            this.lblAfipIdentificacion2.setText(Html.fromHtml(this.mEmpresa.identificacion2));
        }
        if (!TextUtils.isEmpty(this.mEmpresa.gifFactura)) {
            this.lblAyudaAfip.setText(Html.fromHtml(this.mEmpresa.gifFactura));
        }
    }

    public void showSelectPayment(List<CuentaDebitoBean> list, DatosDeudaBean datosDeudaBean) {
        Intent intent = new Intent(this, PrepareAfipPaymentNuevoPagoActivity.class);
        intent.putExtra(NuevoPagoServiciosConstants.EXTRA_ORIGEN, NuevoPagoServiciosConstants.ORIGEN_MANUAL);
        intent.putExtra(NuevoPagoServiciosConstants.EXTRA_CUENTAS, (ArrayList) list);
        intent.putExtra(NuevoPagoServiciosConstants.EXTRA_DEUDA, datosDeudaBean);
        startActivityForResult(intent, 0);
    }

    public void onClick(View view) {
        String str;
        super.onClick(view);
        if (view.getId() == R.id.F06_06_btn_continue) {
            if (isDataValid().booleanValue()) {
                if (!this.inpAfipIdentificacion2.getText().toString().isEmpty()) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(this.inpAfipIdentificacion1.getText().toString());
                    sb.append(this.inpAfipIdentificacion2.getText().toString().substring(0, this.inpAfipIdentificacion2.getText().toString().length() - 1).substring(2));
                    str = sb.toString();
                } else {
                    str = this.inpAfipIdentificacion1.getText().toString();
                }
                if (this.mEmpresa.tipoPago.equalsIgnoreCase("2")) {
                    this.mDeuda.datosEmpresa = this.mEmpresa;
                    this.mDeuda.identificacion = str;
                    this.mDeuda.cuit = this.inpAfipIdentificacion1.getText().toString();
                    this.mDeuda.cuitEmpleador = this.inpAfipIdentificacion2.getText().toString();
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
    }
}
