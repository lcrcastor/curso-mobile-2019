package ar.com.santander.rio.mbanking.app.module.payments.creditCards;

public class AyudaTarjetaPresenterImp implements AyudaTarjetaPresenter {
    public PagoTarjetasView pagoTarjetasView;

    public void onCreatePage() {
    }

    public AyudaTarjetaPresenterImp(PagoTarjetasView pagoTarjetasView2) {
        this.pagoTarjetasView = pagoTarjetasView2;
    }
}
