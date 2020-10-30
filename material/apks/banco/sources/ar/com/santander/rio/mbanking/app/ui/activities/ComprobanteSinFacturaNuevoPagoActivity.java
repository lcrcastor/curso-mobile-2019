package ar.com.santander.rio.mbanking.app.ui.activities;

public class ComprobanteSinFacturaNuevoPagoActivity extends ComprobanteNuevoPagoActivity {
    public void configureLayout() {
        this.rowCUR.setVisibility(8);
        this.rowVEP.setVisibility(8);
        this.rowICS.setVisibility(8);
        this.rowCUIT.setVisibility(8);
        this.rowPeriodo.setVisibility(8);
        this.rowAnticipoCuota.setVisibility(8);
        this.rowFactura.setVisibility(8);
    }

    public void showComprobantePago() {
        super.showComprobantePago();
    }
}
