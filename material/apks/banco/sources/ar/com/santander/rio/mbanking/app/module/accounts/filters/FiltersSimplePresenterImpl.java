package ar.com.santander.rio.mbanking.app.module.accounts.filters;

import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.module.accounts.filters.FiltersSimplePresenter.TypeFilterAdvanced;
import ar.com.santander.rio.mbanking.app.ui.Constants;
import ar.com.santander.rio.mbanking.services.model.general.Cuenta;
import ar.com.santander.rio.mbanking.services.model.general.FilterTransactionsAccount;
import java.util.ArrayList;
import java.util.Arrays;
import org.joda.time.DateTime;

public class FiltersSimplePresenterImpl implements FiltersSimplePresenter {
    private String a = null;
    public ArrayList<String> lstOptionsFilters = new ArrayList<>(Arrays.asList(this.mFiltersSimpleView.getActContext().getResources().getStringArray(R.array.options_filter_transaction)));
    public FiltersSimpleView mFiltersSimpleView;

    public FiltersSimplePresenterImpl(FiltersSimpleView filtersSimpleView, Cuenta cuenta) {
        this.mFiltersSimpleView = filtersSimpleView;
        if (cuenta.isBancaPrivada()) {
            this.lstOptionsFilters.remove(3);
        }
    }

    public void onFilterTransactions() {
        this.mFiltersSimpleView.createAndShowSimpleFilter(this.lstOptionsFilters, this.a);
    }

    public void onFilterClicked(String str) {
        this.a = str;
        FilterTransactionsAccount a2 = a(str);
        if (a2 != null) {
            this.mFiltersSimpleView.onCloseSelectionFilter(a2);
        } else {
            this.mFiltersSimpleView.openFilterAdvanced(b(str));
        }
    }

    public void setFilterSelected(String str) {
        this.a = str;
    }

    private FilterTransactionsAccount a(String str) {
        if (this.mFiltersSimpleView.getActContext().getString(R.string.ID96_ACCOUNTS_CHANGEACC_ACT_MOV1).equals(str)) {
            return new FilterTransactionsAccount(DateTime.now().toString(Constants.FORMAT_DATE_WS_1), DateTime.now().toString(Constants.FORMAT_DATE_WS_1), str);
        }
        if (this.mFiltersSimpleView.getActContext().getString(R.string.ID97_ACCOUNTS_CHANGEACC_ACT_MOV3).equals(str)) {
            return new FilterTransactionsAccount(DateTime.now().minusDays(3).toString(Constants.FORMAT_DATE_WS_1), DateTime.now().toString(Constants.FORMAT_DATE_WS_1), str);
        }
        if (this.mFiltersSimpleView.getActContext().getString(R.string.ID98_ACCOUNTS_CHANGEACC_ACT_MOV7).equals(str)) {
            return new FilterTransactionsAccount(DateTime.now().minusDays(7).toString(Constants.FORMAT_DATE_WS_1), DateTime.now().toString(Constants.FORMAT_DATE_WS_1), str);
        }
        return null;
    }

    private TypeFilterAdvanced b(String str) {
        if (this.mFiltersSimpleView.getActContext().getString(R.string.ID99_ACCOUNTS_CHANGEACC_ACT_IR).equals(str)) {
            return TypeFilterAdvanced.GO_TO_DATE;
        }
        return TypeFilterAdvanced.ADVANCED_SEARCH;
    }
}
