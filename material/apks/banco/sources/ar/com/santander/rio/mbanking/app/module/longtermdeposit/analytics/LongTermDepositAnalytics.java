package ar.com.santander.rio.mbanking.app.module.longtermdeposit.analytics;

public interface LongTermDepositAnalytics {
    void registerEventCancelDialogConfirm();

    void registerEventRatesCurrencyChange(String str);

    void registerEventShareReceipt(int i);

    void registerScreeErrorTotal();

    void registerScreenConfirm();

    void registerScreenConstitution();

    void registerScreenDialogConfirm();

    void registerScreenErrorParcial();

    void registerScreenLongTermDepositDetail(String str);

    void registerScreenLongTermDepositEmptyList();

    void registerScreenLongTermDepositList();

    void registerScreenLongTermDepositListError();

    void registerScreenRates();

    void registerScreenReceipt();

    void registerTransactionHits(String str);
}
