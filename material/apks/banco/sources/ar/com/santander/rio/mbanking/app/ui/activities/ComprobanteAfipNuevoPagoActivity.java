package ar.com.santander.rio.mbanking.app.ui.activities;

import android.text.TextUtils;

public class ComprobanteAfipNuevoPagoActivity extends ComprobanteNuevoPagoActivity {
    public void configureLayout() {
        this.rowFactura.setVisibility(TextUtils.isEmpty(this.mPago.factura) ? 8 : 0);
        this.rowCUR.setVisibility(8);
        this.rowVEP.setVisibility(8);
        this.rowICS.setVisibility(8);
        this.rowPeriodo.setVisibility(8);
        this.rowAnticipoCuota.setVisibility(8);
        if (TextUtils.isEmpty(this.mPago.cuit)) {
            this.rowCUIT.setVisibility(8);
        }
    }

    public void showComprobantePago() {
        super.showComprobantePago();
    }
}
