package ar.com.santander.rio.mbanking.app.module.accounts.filters;

public interface FiltersSimplePresenter {

    public enum TypeFilterAdvanced {
        GO_TO_DATE,
        ADVANCED_SEARCH
    }

    void onFilterClicked(String str);

    void onFilterTransactions();

    void setFilterSelected(String str);
}
