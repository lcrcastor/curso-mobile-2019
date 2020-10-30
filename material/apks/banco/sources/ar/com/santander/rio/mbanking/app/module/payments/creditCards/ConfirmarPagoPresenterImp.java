package ar.com.santander.rio.mbanking.app.module.payments.creditCards;

import java.util.ArrayList;

public class ConfirmarPagoPresenterImp implements ConfirmarPagoPresenter {
    private PagoTarjetasView a;

    public ConfirmarPagoPresenterImp(PagoTarjetasView pagoTarjetasView) {
        this.a = pagoTarjetasView;
    }

    public void onCreatePage() {
        this.a.setConfirmarPagoView();
    }

    public void sendRequestPagoTarjeta(ArrayList<String> arrayList) {
        this.a.getDataManager().pagoTarjeta(arrayList);
    }
}
