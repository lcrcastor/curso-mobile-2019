package ar.com.santander.rio.mbanking.app.module.payments.creditCards;

import java.util.ArrayList;

public interface ConfirmarStopDebitPresenter {
    void onCreatePage();

    void sendRequestStopDebit(ArrayList<String> arrayList);
}
