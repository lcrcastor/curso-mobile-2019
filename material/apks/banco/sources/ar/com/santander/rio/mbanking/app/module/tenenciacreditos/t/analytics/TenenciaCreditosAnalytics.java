package ar.com.santander.rio.mbanking.app.module.tenenciacreditos.t.analytics;

public interface TenenciaCreditosAnalytics {
    void registerEventCancelDialogConfirm();

    void registerEventRatesCurrencyChange(String str);

    void registerEventShareReceipt(int i);

    void registerScreenConfirm();

    void registerScreenConstitution();

    void registerScreenDialogConfirm();

    void registerScreenLongTermDepositDetail(String str);

    void registerScreenLongTermDepositEmptyList();

    void registerScreenLongTermDepositList();

    void registerScreenLongTermDepositListError();

    void registerScreenRates();

    void registerScreenReceipt();

    void registerTransactionHits(String str);
}
