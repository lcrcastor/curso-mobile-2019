package ar.com.santander.rio.mbanking.services.model.general;

import ar.com.santander.rio.mbanking.app.module.accounts.filters.FiltersSimplePresenter.TypeFilterAdvanced;

public class FilterTransactionsAccount {
    private static final String EMPTY = "";
    public String advancedSearch = "";
    public String currency = null;
    public String fromAmount = null;
    public String fromDate = null;
    public String label = "";
    public String toAmount = null;
    public String toDate = null;
    public OptionsTransactions typeAmount = null;
    public TypeFilterAdvanced typeFilterAdvanced;
    public OptionsTransactions typeTransaction = null;

    public FilterTransactionsAccount() {
    }

    public FilterTransactionsAccount(String str, String str2) {
        this.fromDate = str;
        this.toDate = str2;
    }

    public FilterTransactionsAccount(String str, String str2, String str3) {
        this.fromDate = str;
        this.toDate = str2;
        this.label = str3;
    }
}
