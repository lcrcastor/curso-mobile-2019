package ar.com.santander.rio.mbanking.app.ui.activities;

import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.utils.Utils.Documento.CUIT;

public class ComprobanteConFacturaNuevoPagoActivity extends ComprobanteNuevoPagoActivity {
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0038, code lost:
        if (r0.equals(ar.com.santander.rio.mbanking.app.ui.constants.NuevoPagoServiciosConstants.ORIGEN_AGENDA) == false) goto L_0x004f;
     */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x0054  */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0073  */
    /* JADX WARNING: Removed duplicated region for block: B:27:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void configureLayout() {
        /*
            r6 = this;
            android.widget.LinearLayout r0 = r6.rowCUR
            r1 = 8
            r0.setVisibility(r1)
            android.widget.LinearLayout r0 = r6.rowFactura
            ar.com.santander.rio.mbanking.services.soap.beans.body.PagoServiciosBodyResponseBean r2 = r6.mPago
            java.lang.String r2 = r2.factura
            boolean r2 = cz.msebera.android.httpclient.util.TextUtils.isEmpty(r2)
            r3 = 0
            if (r2 == 0) goto L_0x0017
            r2 = 8
            goto L_0x0018
        L_0x0017:
            r2 = 0
        L_0x0018:
            r0.setVisibility(r2)
            java.lang.String r0 = r6.mOrigen
            r2 = -1
            int r4 = r0.hashCode()
            r5 = -1997548570(0xffffffff88efd3e6, float:-1.4434106E-33)
            if (r4 == r5) goto L_0x0045
            r5 = 16943946(0x1028b4a, float:2.3977164E-38)
            if (r4 == r5) goto L_0x003b
            r5 = 1959135276(0x74c6082c, float:1.2551764E32)
            if (r4 == r5) goto L_0x0032
            goto L_0x004f
        L_0x0032:
            java.lang.String r4 = "Agenda"
            boolean r0 = r0.equals(r4)
            if (r0 == 0) goto L_0x004f
            goto L_0x0050
        L_0x003b:
            java.lang.String r3 = "CodigoBarras"
            boolean r0 = r0.equals(r3)
            if (r0 == 0) goto L_0x004f
            r3 = 2
            goto L_0x0050
        L_0x0045:
            java.lang.String r3 = "Manual"
            boolean r0 = r0.equals(r3)
            if (r0 == 0) goto L_0x004f
            r3 = 1
            goto L_0x0050
        L_0x004f:
            r3 = -1
        L_0x0050:
            switch(r3) {
                case 0: goto L_0x0073;
                case 1: goto L_0x0073;
                case 2: goto L_0x0054;
                default: goto L_0x0053;
            }
        L_0x0053:
            goto L_0x00a8
        L_0x0054:
            android.widget.LinearLayout r0 = r6.rowCUR
            r0.setVisibility(r1)
            android.widget.LinearLayout r0 = r6.rowVEP
            r0.setVisibility(r1)
            android.widget.LinearLayout r0 = r6.rowICS
            r0.setVisibility(r1)
            android.widget.LinearLayout r0 = r6.rowCUIT
            r0.setVisibility(r1)
            android.widget.LinearLayout r0 = r6.rowPeriodo
            r0.setVisibility(r1)
            android.widget.LinearLayout r0 = r6.rowAnticipoCuota
            r0.setVisibility(r1)
            goto L_0x00a8
        L_0x0073:
            android.widget.LinearLayout r0 = r6.rowCUR
            r0.setVisibility(r1)
            android.widget.LinearLayout r0 = r6.rowICS
            r0.setVisibility(r1)
            ar.com.santander.rio.mbanking.services.soap.beans.body.DatosDeudaBean r0 = r6.mDeuda
            ar.com.santander.rio.mbanking.services.soap.beans.body.CnsEmpresaDatosEmpresa r0 = r0.datosEmpresa
            java.lang.String r0 = r0.tipoEmpresa
            java.lang.String r0 = java.lang.String.valueOf(r0)
            java.lang.String r2 = "F"
            boolean r0 = r0.equalsIgnoreCase(r2)
            if (r0 != 0) goto L_0x00a8
            android.widget.LinearLayout r0 = r6.rowVEP
            r0.setVisibility(r1)
            android.widget.LinearLayout r0 = r6.rowCUR
            r0.setVisibility(r1)
            android.widget.LinearLayout r0 = r6.rowCUIT
            r0.setVisibility(r1)
            android.widget.LinearLayout r0 = r6.rowPeriodo
            r0.setVisibility(r1)
            android.widget.LinearLayout r0 = r6.rowAnticipoCuota
            r0.setVisibility(r1)
        L_0x00a8:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: ar.com.santander.rio.mbanking.app.ui.activities.ComprobanteConFacturaNuevoPagoActivity.configureLayout():void");
    }

    public void showComprobantePago() {
        super.showComprobantePago();
        if (this.rowVEP.getVisibility() == 0) {
            this.txtVEP.setText(this.mPago.numVep);
        }
        if (this.rowICS.getVisibility() == 0) {
            this.txtICS.setText(this.mPago.infoICS);
        }
        if (this.rowCUIT.getVisibility() == 0) {
            this.txtCUIT.setText(CUIT.format(this.mPago.cuit));
        }
        if (this.rowPeriodo.getVisibility() == 0) {
            this.txtPeriodo.setText(this.mPago.periodo);
            try {
                this.txtPeriodo.setContentDescription(CAccessibility.getInstance(getApplicationContext()).applyFilterDate(this.txtPeriodo.getText().toString()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (this.rowAnticipoCuota.getVisibility() == 0) {
            this.txtAnticipoCuota.setText(this.mPago.anticipoCuota);
        }
    }
}
