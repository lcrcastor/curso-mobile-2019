package ar.com.santander.rio.mbanking.app.module.payments.creditCards;

import java.util.ArrayList;

public interface ConfirmarPagoPresenter {
    void onCreatePage();

    void sendRequestPagoTarjeta(ArrayList<String> arrayList);
}
