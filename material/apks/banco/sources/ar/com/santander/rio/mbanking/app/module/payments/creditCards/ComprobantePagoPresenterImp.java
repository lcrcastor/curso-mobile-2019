package ar.com.santander.rio.mbanking.app.module.payments.creditCards;

public class ComprobantePagoPresenterImp implements ComprobantePagoPresenter {
    private PagoTarjetasView a;

    public ComprobantePagoPresenterImp(PagoTarjetasView pagoTarjetasView) {
        this.a = pagoTarjetasView;
    }

    public void onCreatePage(String str) {
        this.a.setComprobantePagoView(str);
    }
}
