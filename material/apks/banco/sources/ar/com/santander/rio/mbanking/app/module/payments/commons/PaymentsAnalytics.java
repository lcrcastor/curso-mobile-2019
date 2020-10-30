package ar.com.santander.rio.mbanking.app.module.payments.commons;

public interface PaymentsAnalytics {
    void registerDialogConfirm();

    void registerEventCancelDialogConfirm();

    void registerEventShareReceipt(int i);

    void registerReceipt();

    void registerScreenConfirm();

    void registerScreenHome();

    void registerScreenPrepareWithAmount();

    void registerScreenPrepareWithOutAmount();

    void registerTransactionHits(String str);
}
