package ar.com.santander.rio.mbanking.app.module.accounts.filters;

import android.content.Context;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.app.module.accounts.filters.FiltersSimplePresenter.TypeFilterAdvanced;
import ar.com.santander.rio.mbanking.components.IsbanDatePickerDialogFragment;
import ar.com.santander.rio.mbanking.services.model.general.FilterTransactionsAccount;
import ar.com.santander.rio.mbanking.view.NumericEditText;
import java.util.ArrayList;

public interface FiltersAdvancedView {
    Context getContext();

    TypeFilterAdvanced getCurrentTypeFilter();

    IsbanDatePickerDialogFragment getDatePickerDialogFrom();

    IsbanDatePickerDialogFragment getDatePickerDialogTo();

    EditText getEditAdvancedSearch();

    String getTextTypeAmount();

    String getTextTypeTransaction();

    Button getViewButtonSearch();

    NumericEditText getViewFromAmount();

    TextView getViewFromDate();

    NumericEditText getViewToAmount();

    TextView getViewToDate();

    void hideViewGroupAmount();

    void hideViewGroupToDate();

    void hideViewTypeAmount();

    void lockMenu(boolean z);

    void onAcceptFiltersClicked(FilterTransactionsAccount filterTransactionsAccount);

    void onClickButtonSearch();

    void restoreMainActionBar();

    void setDateFrom(String str);

    void setDateTo(String str);

    void setEditAdvanceSearch(String str);

    void setFromAmount(String str);

    void setLabelFromDate(int i);

    void setSelectorAmount(ArrayList<String> arrayList);

    void setSelectorTransactions(ArrayList<String> arrayList);

    void setTextTypeAmount(String str);

    void setTextTypeTransaction(String str);

    void setTitleFilter(int i);

    void setToAmount(String str);

    void showDatePicker(IsbanDatePickerDialogFragment isbanDatePickerDialogFragment);

    void showMessage(String str);
}
