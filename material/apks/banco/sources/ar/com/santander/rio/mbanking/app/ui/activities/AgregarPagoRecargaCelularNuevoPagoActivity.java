package ar.com.santander.rio.mbanking.app.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.text.Html;
import android.text.InputFilter;
import android.text.InputFilter.LengthFilter;
import android.text.TextUtils;
import android.view.View;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.ui.constants.NuevoPagoServiciosConstants;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CuentaDebitoBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.DatosDeudaBean;
import butterknife.ButterKnife;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

public class AgregarPagoRecargaCelularNuevoPagoActivity extends AgregarPagoNuevoPagoActivity {
    public void showSelectPayment(List<CuentaDebitoBean> list, List<DatosDeudaBean> list2, String str) {
    }

    public void initialize() {
        super.initialize();
        this.inpRecargaIdentificacion1.addTextChangedListener(this.mHabilitadorBotonContinuar);
        this.inpRecargaIdentificacion2.addTextChangedListener(this.mHabilitadorBotonContinuar);
    }

    public void configureLayout() {
        this.groupPagoConFactura.setVisibility(8);
        this.groupRecargaCelular.setVisibility(0);
        if (String.valueOf(this.mEmpresa.identificacion1).isEmpty() || TextUtils.isEmpty(this.mEmpresa.identificacion1)) {
            this.groupRecargaIdentificacion1.setVisibility(8);
        }
        if (String.valueOf(this.mEmpresa.identificacion2).isEmpty() || TextUtils.isEmpty(this.mEmpresa.identificacion2)) {
            this.groupRecargaIdentificacion2.setVisibility(8);
        }
        if (this.mEmpresa.empServ.equalsIgnoreCase("SUBE")) {
            this.inpRecargaIdentificacion1.setFilters(new InputFilter[]{new LengthFilter(Integer.valueOf(this.mDeuda.validaciones.longitud).intValue())});
            this.lblRecargaIdentificacion1Prefijo.setVisibility(8);
        }
        this.groupPagoAfip.setVisibility(8);
    }

    /* access modifiers changed from: protected */
    public Boolean isDataFilled() {
        boolean z = true;
        Boolean valueOf = Boolean.valueOf(true);
        if (this.groupRecargaIdentificacion1.getVisibility() == 0) {
            valueOf = Boolean.valueOf(valueOf.booleanValue() && !TextUtils.isEmpty(this.inpRecargaIdentificacion1.getText().toString()) && this.inpRecargaIdentificacion1.getText().toString().length() >= 2);
        }
        if (this.groupRecargaIdentificacion2.getVisibility() != 0) {
            return valueOf;
        }
        if (!valueOf.booleanValue() || TextUtils.isEmpty(this.inpRecargaIdentificacion2.getText().toString()) || this.inpRecargaIdentificacion2.getText().toString().length() < 7) {
            z = false;
        }
        return Boolean.valueOf(z);
    }

    /* access modifiers changed from: protected */
    public Boolean isDataValid() {
        this.mInvalidInputs.clear();
        return Boolean.valueOf(this.mInvalidInputs.isEmpty());
    }

    public void showAddPayment() {
        super.showAddPayment();
        if (this.groupRecargaIdentificacion1.getVisibility() == 0) {
            this.lblRecargaIdentificacion1.setText(Html.fromHtml(this.mEmpresa.identificacion1));
        }
        if (this.groupRecargaIdentificacion2.getVisibility() == 0) {
            this.lblRecargaIdentificacion2.setText(Html.fromHtml(this.mEmpresa.identificacion2));
        }
    }

    public void showSelectPayment(List<CuentaDebitoBean> list, DatosDeudaBean datosDeudaBean) {
        Intent intent = new Intent(this, PrepareNoInvoicePaymentNuevoPagoActivity.class);
        intent.putExtra(NuevoPagoServiciosConstants.EXTRA_ORIGEN, NuevoPagoServiciosConstants.ORIGEN_MANUAL);
        intent.putExtra(NuevoPagoServiciosConstants.EXTRA_CUENTAS, (ArrayList) list);
        intent.putExtra(NuevoPagoServiciosConstants.EXTRA_DEUDA, datosDeudaBean);
        startActivityForResult(intent, 0);
    }

    public void onClick(View view) {
        super.onClick(view);
        if (view.getId() == R.id.F06_06_btn_continue) {
            if (isDataValid().booleanValue()) {
                StringBuilder sb = new StringBuilder();
                sb.append(this.inpRecargaIdentificacion1.getText().toString());
                sb.append(this.inpRecargaIdentificacion2.getText().toString());
                String sb2 = sb.toString();
                this.mDeuda.datosEmpresa = this.mEmpresa;
                this.mDeuda.identificacion = sb2;
                this.presenter.onAddPayment(this.mCuentas, this.mDeuda);
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
