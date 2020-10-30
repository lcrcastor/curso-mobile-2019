package ar.com.santander.rio.mbanking.app.ui.activities;

import android.content.Intent;
import android.text.TextUtils;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.commons.CAmountIU;
import ar.com.santander.rio.mbanking.app.commons.CConsDescripciones;
import ar.com.santander.rio.mbanking.app.commons.CNuevoPago;
import ar.com.santander.rio.mbanking.app.ui.constants.NuevoPagoServiciosConstants;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CnsEmpresaDatosEmpresa;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CnsEmpresaValidaciones;
import ar.com.santander.rio.mbanking.services.soap.beans.body.DatosAdicionalesDeudaBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.DatosDeudaBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ListGroupBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ListTableBean;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PrepareNoInvoicePaymentNuevoPagoActivity extends PrepararPagoServicioActivity {
    ArrayList<String> t;

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
                    if ((CNuevoPago.isAmountEditable(this.mDeuda.datosEmpresa.tipoImporte, this.mDeuda.importe).booleanValue() || this.mOrigen.equalsIgnoreCase(NuevoPagoServiciosConstants.ORIGEN_MANUAL)) && !this.mDeuda.datosEmpresa.empServ.equalsIgnoreCase("SUBE")) {
                        this.txtImporte.setVisibility(8);
                    } else {
                        this.inpImporte.setVisibility(8);
                    }
                    if (TextUtils.isEmpty(this.mDeuda.datosAdicionales.leyenda) || TextUtils.isEmpty(this.mDeuda.datosAdicionales.ejemplo)) {
                        this.rowDatosAdicionales.setVisibility(8);
                        break;
                    }
                    break;
                case 1:
                    this.rowInformacionAdicional.setVisibility(8);
                    if (this.mDeuda.validaciones.tipoId.equalsIgnoreCase("2") || this.mDeuda.datosEmpresa.empServ.equalsIgnoreCase("SUBE")) {
                        this.inpImporte.setVisibility(8);
                    } else {
                        if (!CNuevoPago.isAmountEditable(this.mDeuda.datosEmpresa.tipoImporte, this.mDeuda.importe).booleanValue()) {
                            if (!this.mOrigen.equalsIgnoreCase(NuevoPagoServiciosConstants.ORIGEN_MANUAL)) {
                                this.inpImporte.setVisibility(8);
                            }
                        }
                        this.txtImporte.setVisibility(8);
                    }
                    this.rowDatosAdicionales.setVisibility(8);
                    break;
            }
            if (TextUtils.isEmpty(this.mDeuda.factura)) {
                this.rowFactura.setVisibility(8);
            }
        } catch (Exception unused) {
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:13:0x0050  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x00f5  */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x0177  */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x01d0  */
    /* JADX WARNING: Removed duplicated region for block: B:53:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void showPrepararPago() {
        /*
            r8 = this;
            android.widget.TextView r0 = r8.txtEmpresa
            ar.com.santander.rio.mbanking.services.soap.beans.body.DatosDeudaBean r1 = r8.mDeuda
            ar.com.santander.rio.mbanking.services.soap.beans.body.CnsEmpresaDatosEmpresa r1 = r1.datosEmpresa
            java.lang.String r1 = r1.empDescr
            r0.setText(r1)
            android.widget.EditText r0 = r8.inpInfoAdicional
            ar.com.santander.rio.mbanking.services.soap.beans.body.DatosDeudaBean r1 = r8.mDeuda
            java.lang.String r1 = r1.infoAdicional
            r0.setText(r1)
            android.widget.TextView r0 = r8.txtIdentificador
            ar.com.santander.rio.mbanking.services.soap.beans.body.DatosDeudaBean r1 = r8.mDeuda
            java.lang.String r1 = r1.identificacion
            r0.setText(r1)
            java.lang.String r0 = r8.mOrigen
            int r1 = r0.hashCode()
            r2 = -1997548570(0xffffffff88efd3e6, float:-1.4434106E-33)
            r3 = 1
            r4 = 0
            if (r1 == r2) goto L_0x003a
            r2 = 1959135276(0x74c6082c, float:1.2551764E32)
            if (r1 == r2) goto L_0x0030
            goto L_0x0044
        L_0x0030:
            java.lang.String r1 = "Agenda"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0044
            r0 = 0
            goto L_0x0045
        L_0x003a:
            java.lang.String r1 = "Manual"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0044
            r0 = 1
            goto L_0x0045
        L_0x0044:
            r0 = -1
        L_0x0045:
            r1 = 2131756515(0x7f1005e3, float:1.914394E38)
            r2 = 2
            r5 = 0
            switch(r0) {
                case 0: goto L_0x00f5;
                case 1: goto L_0x0050;
                default: goto L_0x004e;
            }
        L_0x004e:
            goto L_0x0173
        L_0x0050:
            ar.com.santander.rio.mbanking.services.soap.beans.body.DatosDeudaBean r0 = r8.mDeuda
            ar.com.santander.rio.mbanking.services.soap.beans.body.CnsEmpresaValidaciones r0 = r0.validaciones
            java.lang.String r0 = r0.tipoId
            java.lang.String r7 = "2"
            boolean r0 = r0.equalsIgnoreCase(r7)
            if (r0 != 0) goto L_0x00c5
            ar.com.santander.rio.mbanking.services.soap.beans.body.DatosDeudaBean r0 = r8.mDeuda
            ar.com.santander.rio.mbanking.services.soap.beans.body.CnsEmpresaDatosEmpresa r0 = r0.datosEmpresa
            java.lang.String r0 = r0.empServ
            java.lang.String r7 = "SUBE"
            boolean r0 = r0.equalsIgnoreCase(r7)
            if (r0 != 0) goto L_0x00c5
            ar.com.santander.rio.mbanking.services.soap.beans.body.DatosDeudaBean r0 = r8.mDeuda
            ar.com.santander.rio.mbanking.services.soap.beans.body.CnsEmpresaDatosEmpresa r0 = r0.datosEmpresa
            java.lang.String r0 = r0.tipoImporte
            ar.com.santander.rio.mbanking.services.soap.beans.body.DatosDeudaBean r1 = r8.mDeuda
            java.lang.String r1 = r1.importe
            java.lang.Boolean r0 = ar.com.santander.rio.mbanking.app.commons.CNuevoPago.isAmountEditable(r0, r1)
            boolean r0 = r0.booleanValue()
            if (r0 == 0) goto L_0x00a2
            android.widget.EditText r0 = r8.inpImporte
            java.lang.String r1 = "0,00"
            r0.setHint(r1)
            ar.com.santander.rio.mbanking.services.soap.beans.body.DatosDeudaBean r0 = r8.mDeuda
            java.lang.String r0 = r0.importe
            double r0 = ar.com.santander.rio.mbanking.utils.UtilAmount.getAmount(r0)
            int r2 = (r0 > r5 ? 1 : (r0 == r5 ? 0 : -1))
            if (r2 <= 0) goto L_0x0173
            android.widget.EditText r0 = r8.inpImporte
            ar.com.santander.rio.mbanking.services.soap.beans.body.DatosDeudaBean r1 = r8.mDeuda
            java.lang.String r1 = r1.importe
            java.lang.String r1 = ar.com.santander.rio.mbanking.utils.UtilCurrency.getFormattedAmountInArsFromString(r1)
            r0.setText(r1)
            goto L_0x0173
        L_0x00a2:
            android.widget.TextView r0 = r8.txtImporte
            java.lang.String r1 = "%s%s"
            java.lang.Object[] r2 = new java.lang.Object[r2]
            ar.com.santander.rio.mbanking.services.soap.beans.body.DatosDeudaBean r5 = r8.mDeuda
            java.lang.String r5 = r5.moneda
            java.lang.String r5 = ar.com.santander.rio.mbanking.utils.UtilCurrency.getSimbolCurrencyFromString(r5)
            r2[r4] = r5
            ar.com.santander.rio.mbanking.services.soap.beans.body.DatosDeudaBean r4 = r8.mDeuda
            java.lang.String r4 = r4.importe
            java.lang.String r4 = ar.com.santander.rio.mbanking.utils.UtilCurrency.getFormattedAmountInArsFromString(r4)
            r2[r3] = r4
            java.lang.String r1 = java.lang.String.format(r1, r2)
            r0.setText(r1)
            goto L_0x0173
        L_0x00c5:
            ar.com.santander.rio.mbanking.services.soap.beans.body.DatosDeudaBean r0 = r8.mDeuda
            ar.com.santander.rio.mbanking.services.soap.beans.body.CnsEmpresaDatosEmpresa r0 = r0.datosEmpresa
            java.lang.String r0 = r0.empServ
            java.lang.String r2 = "SUBE"
            boolean r0 = r0.equalsIgnoreCase(r2)
            if (r0 != 0) goto L_0x00da
            java.util.ArrayList r0 = r8.b()
            r8.t = r0
            goto L_0x00e0
        L_0x00da:
            java.util.ArrayList r0 = r8.c()
            r8.t = r0
        L_0x00e0:
            android.widget.TextView r0 = r8.txtImporte
            java.lang.String r1 = r8.getString(r1)
            r0.setText(r1)
            android.widget.TextView r0 = r8.txtImporte
            ar.com.santander.rio.mbanking.app.ui.activities.PrepareNoInvoicePaymentNuevoPagoActivity$2 r1 = new ar.com.santander.rio.mbanking.app.ui.activities.PrepareNoInvoicePaymentNuevoPagoActivity$2
            r1.<init>()
            r0.setOnClickListener(r1)
            goto L_0x0173
        L_0x00f5:
            ar.com.santander.rio.mbanking.services.soap.beans.body.DatosDeudaBean r0 = r8.mDeuda
            ar.com.santander.rio.mbanking.services.soap.beans.body.CnsEmpresaDatosEmpresa r0 = r0.datosEmpresa
            java.lang.String r0 = r0.empServ
            java.lang.String r7 = "SUBE"
            boolean r0 = r0.equalsIgnoreCase(r7)
            if (r0 == 0) goto L_0x011d
            java.util.ArrayList r0 = r8.c()
            r8.t = r0
            android.widget.TextView r0 = r8.txtImporte
            java.lang.String r1 = r8.getString(r1)
            r0.setText(r1)
            android.widget.TextView r0 = r8.txtImporte
            ar.com.santander.rio.mbanking.app.ui.activities.PrepareNoInvoicePaymentNuevoPagoActivity$1 r1 = new ar.com.santander.rio.mbanking.app.ui.activities.PrepareNoInvoicePaymentNuevoPagoActivity$1
            r1.<init>()
            r0.setOnClickListener(r1)
            goto L_0x0173
        L_0x011d:
            ar.com.santander.rio.mbanking.services.soap.beans.body.DatosDeudaBean r0 = r8.mDeuda
            ar.com.santander.rio.mbanking.services.soap.beans.body.CnsEmpresaDatosEmpresa r0 = r0.datosEmpresa
            java.lang.String r0 = r0.tipoImporte
            ar.com.santander.rio.mbanking.services.soap.beans.body.DatosDeudaBean r1 = r8.mDeuda
            java.lang.String r1 = r1.importe
            java.lang.Boolean r0 = ar.com.santander.rio.mbanking.app.commons.CNuevoPago.isAmountEditable(r0, r1)
            boolean r0 = r0.booleanValue()
            if (r0 == 0) goto L_0x0152
            android.widget.EditText r0 = r8.inpImporte
            java.lang.String r1 = "0,00"
            r0.setHint(r1)
            ar.com.santander.rio.mbanking.services.soap.beans.body.DatosDeudaBean r0 = r8.mDeuda
            java.lang.String r0 = r0.importe
            double r0 = ar.com.santander.rio.mbanking.utils.UtilAmount.getAmount(r0)
            int r2 = (r0 > r5 ? 1 : (r0 == r5 ? 0 : -1))
            if (r2 <= 0) goto L_0x0173
            android.widget.EditText r0 = r8.inpImporte
            ar.com.santander.rio.mbanking.services.soap.beans.body.DatosDeudaBean r1 = r8.mDeuda
            java.lang.String r1 = r1.importe
            java.lang.String r1 = ar.com.santander.rio.mbanking.utils.UtilCurrency.getFormattedAmountInArsFromString(r1)
            r0.setText(r1)
            goto L_0x0173
        L_0x0152:
            android.widget.TextView r0 = r8.txtImporte
            java.lang.String r1 = "%s%s"
            java.lang.Object[] r2 = new java.lang.Object[r2]
            ar.com.santander.rio.mbanking.services.soap.beans.body.DatosDeudaBean r5 = r8.mDeuda
            java.lang.String r5 = r5.moneda
            java.lang.String r5 = ar.com.santander.rio.mbanking.utils.UtilCurrency.getSimbolCurrencyFromString(r5)
            r2[r4] = r5
            ar.com.santander.rio.mbanking.services.soap.beans.body.DatosDeudaBean r4 = r8.mDeuda
            java.lang.String r4 = r4.importe
            java.lang.String r4 = ar.com.santander.rio.mbanking.utils.UtilCurrency.getFormattedAmountInArsFromString(r4)
            r2[r3] = r4
            java.lang.String r1 = java.lang.String.format(r1, r2)
            r0.setText(r1)
        L_0x0173:
            ar.com.santander.rio.mbanking.services.soap.beans.body.CuentaDebitoBean r0 = r8.mCuentaSeleccionada
            if (r0 == 0) goto L_0x019a
            android.widget.TextView r0 = r8.txtMedioPago
            ar.com.santander.rio.mbanking.services.soap.beans.body.CuentaDebitoBean r1 = r8.mCuentaSeleccionada
            java.lang.String r1 = r1.getDescCtaDebito()
            java.lang.String r1 = r8.formatMedioPago(r1)
            r0.setText(r1)
            android.widget.TextView r0 = r8.txtMedioPago     // Catch:{ Exception -> 0x0196 }
            ar.com.santander.rio.mbanking.services.soap.beans.body.CuentaDebitoBean r1 = r8.mCuentaSeleccionada     // Catch:{ Exception -> 0x0196 }
            java.lang.String r1 = r1.getDescCtaDebito()     // Catch:{ Exception -> 0x0196 }
            java.lang.String r1 = r8.formatAccessibilityMedioPago(r1)     // Catch:{ Exception -> 0x0196 }
            r0.setContentDescription(r1)     // Catch:{ Exception -> 0x0196 }
            goto L_0x019a
        L_0x0196:
            r0 = move-exception
            r0.printStackTrace()
        L_0x019a:
            android.widget.TextView r0 = r8.txtVencimiento
            ar.com.santander.rio.mbanking.services.soap.beans.body.DatosDeudaBean r1 = r8.mDeuda
            java.lang.String r1 = r1.vencimiento
            r0.setText(r1)
            android.widget.TextView r0 = r8.txtFactura
            ar.com.santander.rio.mbanking.services.soap.beans.body.DatosDeudaBean r1 = r8.mDeuda
            java.lang.String r1 = r1.factura
            r0.setText(r1)
            android.widget.TextView r0 = r8.txtFactura     // Catch:{ Exception -> 0x01c4 }
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r1 = ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility.getInstance(r8)     // Catch:{ Exception -> 0x01c4 }
            android.widget.TextView r2 = r8.txtFactura     // Catch:{ Exception -> 0x01c4 }
            java.lang.CharSequence r2 = r2.getText()     // Catch:{ Exception -> 0x01c4 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x01c4 }
            java.lang.String r1 = r1.applyFilterCharacterToCharacter(r2)     // Catch:{ Exception -> 0x01c4 }
            r0.setContentDescription(r1)     // Catch:{ Exception -> 0x01c4 }
            goto L_0x01c8
        L_0x01c4:
            r0 = move-exception
            r0.printStackTrace()
        L_0x01c8:
            android.widget.LinearLayout r0 = r8.rowDatosAdicionales
            int r0 = r0.getVisibility()
            if (r0 != 0) goto L_0x01ee
            android.widget.TextView r0 = r8.lblDatosAdicionales
            ar.com.santander.rio.mbanking.services.soap.beans.body.DatosDeudaBean r1 = r8.mDeuda
            ar.com.santander.rio.mbanking.services.soap.beans.body.DatosAdicionalesDeudaBean r1 = r1.datosAdicionales
            java.lang.String r1 = r1.leyenda
            android.text.Spanned r1 = android.text.Html.fromHtml(r1)
            r0.setText(r1)
            android.widget.TextView r0 = r8.txtDatosAdicionales
            ar.com.santander.rio.mbanking.services.soap.beans.body.DatosDeudaBean r1 = r8.mDeuda
            ar.com.santander.rio.mbanking.services.soap.beans.body.DatosAdicionalesDeudaBean r1 = r1.datosAdicionales
            java.lang.String r1 = r1.ejemplo
            android.text.Spanned r1 = android.text.Html.fromHtml(r1)
            r0.setText(r1)
        L_0x01ee:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: ar.com.santander.rio.mbanking.app.ui.activities.PrepareNoInvoicePaymentNuevoPagoActivity.showPrepararPago():void");
    }

    private ArrayList<String> b() {
        ArrayList<String> arrayList = new ArrayList<>();
        ListTableBean consDescripcionRECARG_MONTO = CConsDescripciones.getConsDescripcionRECARG_MONTO(this.p);
        if (consDescripcionRECARG_MONTO != null) {
            List<ListGroupBean> list = consDescripcionRECARG_MONTO.listGroupBeans;
            if (list != null) {
                for (ListGroupBean listGroupBean : list) {
                    if (!TextUtils.isEmpty(listGroupBean.getLabel()) && CAmountIU.getInstance().getDoubleFromInputUser(listGroupBean.getLabel()) != null && CAmountIU.getInstance().getDoubleFromInputUser(listGroupBean.getLabel()).doubleValue() > 0.0d) {
                        arrayList.add(listGroupBean.getLabel());
                    }
                }
                if (arrayList.size() > 0) {
                    Collections.sort(arrayList, new Comparator<String>() {
                        /* renamed from: a */
                        public int compare(String str, String str2) {
                            return Integer.valueOf(str).compareTo(Integer.valueOf(str2));
                        }
                    });
                }
            }
        }
        return arrayList;
    }

    private ArrayList<String> c() {
        ArrayList<String> arrayList = new ArrayList<>();
        ListTableBean consDescripcionRECARG_SUBE = CConsDescripciones.getConsDescripcionRECARG_SUBE(this.p);
        if (consDescripcionRECARG_SUBE != null) {
            List<ListGroupBean> list = consDescripcionRECARG_SUBE.listGroupBeans;
            if (list != null) {
                for (ListGroupBean listGroupBean : list) {
                    if (!TextUtils.isEmpty(listGroupBean.getLabel()) && CAmountIU.getInstance().getDoubleFromInputUser(listGroupBean.getLabel()) != null && CAmountIU.getInstance().getDoubleFromInputUser(listGroupBean.getLabel()).doubleValue() > 0.0d) {
                        arrayList.add(listGroupBean.getLabel());
                    }
                }
            }
        }
        return arrayList;
    }

    /* access modifiers changed from: protected */
    public int isDataValid() {
        if (this.mDeuda.validaciones.tipoId.equalsIgnoreCase("2") || this.mDeuda.datosEmpresa.empServ.equalsIgnoreCase("SUBE")) {
            if (this.txtImporte.getText().toString().equalsIgnoreCase(getString(R.string.IDX_ALERT_LBL_TITLE_SELECCIONAR))) {
                return 1;
            }
        } else if (CNuevoPago.isAmountEditable(this.mDeuda.datosEmpresa.tipoImporte, this.mDeuda.importe).booleanValue()) {
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
        String obj = this.inpInfoAdicional.getText().toString();
        String str = (this.mDeuda.validaciones.tipoId.equalsIgnoreCase("2") || this.mDeuda.datosEmpresa.empServ.equalsIgnoreCase("SUBE")) ? this.txtImporte.getText().toString() : CNuevoPago.isAmountEditable(this.mDeuda.datosEmpresa.tipoImporte, this.mDeuda.importe).booleanValue() ? this.inpImporte.getText().toString() : this.txtImporte.getText().toString();
        Intent intent = new Intent(this, ConfirmNoInvoicePaymentNuevoPagoActivity.class);
        intent.putExtra(NuevoPagoServiciosConstants.EXTRA_ORIGEN, this.mOrigen);
        intent.putExtra(NuevoPagoServiciosConstants.EXTRA_CUENTA, this.mCuentaSeleccionada);
        String str2 = NuevoPagoServiciosConstants.EXTRA_DEUDA;
        String str3 = this.mDeuda.empServ;
        String str4 = this.mDeuda.empDescr;
        String str5 = this.mDeuda.identificacion;
        String str6 = this.mDeuda.tipoImporte;
        String valueOf = String.valueOf(CAmountIU.getInstance().getDoubleFromInputUser(str));
        String str7 = this.mDeuda.moneda;
        String str8 = this.mDeuda.factura;
        String str9 = this.mDeuda.vencimiento;
        String str10 = this.mDeuda.cur;
        String str11 = this.mDeuda.numVep;
        String str12 = this.mDeuda.cuit;
        String str13 = this.mDeuda.periodo;
        String str14 = this.mDeuda.anticipoCuota;
        String str15 = this.mDeuda.datosAdic;
        CnsEmpresaDatosEmpresa cnsEmpresaDatosEmpresa = this.mDeuda.datosEmpresa;
        DatosAdicionalesDeudaBean datosAdicionalesDeudaBean = this.mDeuda.datosAdicionales;
        CnsEmpresaValidaciones cnsEmpresaValidaciones = this.mDeuda.validaciones;
        String str16 = str11;
        String str17 = str2;
        DatosDeudaBean datosDeudaBean = r2;
        Intent intent2 = intent;
        DatosDeudaBean datosDeudaBean2 = new DatosDeudaBean(str3, str4, str5, str6, valueOf, str7, str8, str9, obj, str10, str16, str12, str13, str14, str15, cnsEmpresaDatosEmpresa, datosAdicionalesDeudaBean, cnsEmpresaValidaciones, this.mDeuda.datAdicionales);
        intent2.putExtra(str17, datosDeudaBean);
        startActivityForResult(intent2, 1);
    }
}
