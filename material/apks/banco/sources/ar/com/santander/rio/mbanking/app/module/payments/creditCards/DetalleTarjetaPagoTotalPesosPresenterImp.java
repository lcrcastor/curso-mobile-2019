package ar.com.santander.rio.mbanking.app.module.payments.creditCards;

public class DetalleTarjetaPagoTotalPesosPresenterImp implements DetalleTarjetaPagoTotalPesosPresenter {
    private PagoTarjetasView a;

    public DetalleTarjetaPagoTotalPesosPresenterImp(PagoTarjetasView pagoTarjetasView) {
        this.a = pagoTarjetasView;
    }

    public void onCreatePage() {
        this.a.setDetalleTarjetaPagoTotalPesosView();
    }
}
