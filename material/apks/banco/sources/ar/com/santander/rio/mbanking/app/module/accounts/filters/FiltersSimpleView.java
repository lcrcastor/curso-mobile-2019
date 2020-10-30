package ar.com.santander.rio.mbanking.app.module.accounts.filters;

import android.content.Context;
import ar.com.santander.rio.mbanking.app.module.accounts.filters.FiltersSimplePresenter.TypeFilterAdvanced;
import ar.com.santander.rio.mbanking.services.model.general.FilterTransactionsAccount;
import java.util.ArrayList;

public interface FiltersSimpleView {
    void createAndShowSimpleFilter(ArrayList<String> arrayList, String str);

    Context getActContext();

    void onCloseSelectionFilter(FilterTransactionsAccount filterTransactionsAccount);

    void openFilterAdvanced(TypeFilterAdvanced typeFilterAdvanced);
}
