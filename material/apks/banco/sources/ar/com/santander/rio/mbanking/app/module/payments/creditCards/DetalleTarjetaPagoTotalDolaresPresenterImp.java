package ar.com.santander.rio.mbanking.app.module.payments.creditCards;

public class DetalleTarjetaPagoTotalDolaresPresenterImp implements DetalleTarjetaPagoTotalDolaresPresenter {
    public PagoTarjetasView pagoTarjetasView;

    public DetalleTarjetaPagoTotalDolaresPresenterImp(PagoTarjetasView pagoTarjetasView2) {
        this.pagoTarjetasView = pagoTarjetasView2;
    }

    public void onCreatePage() {
        this.pagoTarjetasView.setDetalleTarjetaPagoTotalDolaresView();
    }
}
