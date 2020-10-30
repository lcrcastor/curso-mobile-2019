package ar.com.santander.rio.mbanking.app.module.payments.creditCards;

public class ComprobanteStopDebitPresenterImp implements ComprobanteStopDebitPresenter {
    private PagoTarjetasView a;

    public ComprobanteStopDebitPresenterImp(PagoTarjetasView pagoTarjetasView) {
        this.a = pagoTarjetasView;
    }

    public void onCreatePage(String str, String str2) {
        this.a.setComprobanteStopDebitView(str, str2);
    }
}
