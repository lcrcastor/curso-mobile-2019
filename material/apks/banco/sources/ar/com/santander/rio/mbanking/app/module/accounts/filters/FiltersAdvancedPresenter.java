package ar.com.santander.rio.mbanking.app.module.accounts.filters;

import ar.com.santander.rio.mbanking.app.module.accounts.filters.FiltersSimplePresenter.TypeFilterAdvanced;
import ar.com.santander.rio.mbanking.services.model.general.FilterTransactionsAccount;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ConsDescriptionBodyResponseBean;
import java.util.Date;

public interface FiltersAdvancedPresenter {
    void acceptFilters();

    void cancelFilters();

    void createFilterForm(ConsDescriptionBodyResponseBean consDescriptionBodyResponseBean, TypeFilterAdvanced typeFilterAdvanced, FilterTransactionsAccount filterTransactionsAccount);

    void onDatePickerFromClicked(Date date);

    void onDatePickerToClicked(Date date);

    void onStartPresenter();
}
