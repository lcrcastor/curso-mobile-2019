package ar.com.santander.rio.mbanking.app.module.payments.creditCards;

public class PrepararPagoPresenterImp implements PrepararPagoPresenter {
    public PagoTarjetasView pagoTarjetasView;

    public PrepararPagoPresenterImp(PagoTarjetasView pagoTarjetasView2) {
        this.pagoTarjetasView = pagoTarjetasView2;
    }

    public void onCreatePage(int i) {
        this.pagoTarjetasView.setPrepararPagoView(i);
    }
}
