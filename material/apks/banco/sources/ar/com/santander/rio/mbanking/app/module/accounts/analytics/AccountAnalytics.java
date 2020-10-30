package ar.com.santander.rio.mbanking.app.module.accounts.analytics;

import ar.com.santander.rio.mbanking.app.module.accounts.filters.FiltersSimplePresenter.TypeFilterAdvanced;
import ar.com.santander.rio.mbanking.services.model.general.FilterTransactionsAccount;

public interface AccountAnalytics {
    void registerEventChangeAccount();

    void registerEventManagerSearch(TypeFilterAdvanced typeFilterAdvanced, FilterTransactionsAccount filterTransactionsAccount);

    void registerEventShare();

    void registerEventSimpleSearch(String str);

    void registerScreenAdvancedSearchTransactions();

    void registerScreenAmount();

    void registerScreenCBU();

    void registerScreenDetailsTransaction();

    void registerScreenHome();

    void registerScreenSimpleSearchTransactions();
}
