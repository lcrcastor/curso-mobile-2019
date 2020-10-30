package ar.com.santander.rio.mbanking.app.module.payments.creditCards;

import java.util.ArrayList;

public class ConfirmarStopDebitPresenterImp implements ConfirmarStopDebitPresenter {
    private PagoTarjetasView a;

    public ConfirmarStopDebitPresenterImp(PagoTarjetasView pagoTarjetasView) {
        this.a = pagoTarjetasView;
    }

    public void onCreatePage() {
        this.a.setConfirmarStopDebitView();
    }

    public void sendRequestStopDebit(ArrayList<String> arrayList) {
        this.a.getDataManager().stopDebit(arrayList);
    }
}
