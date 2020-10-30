package ar.com.santander.rio.mbanking.app.module.accounts.filters;

import android.util.SparseArray;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.commons.CAmount;
import ar.com.santander.rio.mbanking.app.commons.CAmountWebService;
import ar.com.santander.rio.mbanking.app.module.accounts.ConstantsTransactions;
import ar.com.santander.rio.mbanking.app.module.accounts.filters.FiltersSimplePresenter.TypeFilterAdvanced;
import ar.com.santander.rio.mbanking.app.ui.Constants;
import ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity;
import ar.com.santander.rio.mbanking.services.model.general.Cuenta;
import ar.com.santander.rio.mbanking.services.model.general.FilterTransactionsAccount;
import ar.com.santander.rio.mbanking.services.model.general.OptionsTransactions;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ConsDescriptionBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ListGroupBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ListTableBean;
import ar.com.santander.rio.mbanking.utils.UtilDate;
import java.util.ArrayList;
import java.util.Date;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

public class FiltersAdvancedPresenterImpl implements FiltersAdvancedPresenter {
    private ValidateFilters a = new ValidateFilters();
    private ConsDescriptionBodyResponseBean b;
    private String c;
    private Cuenta d;
    public FiltersAdvancedView mFiltersAdvancedView;

    public FiltersAdvancedPresenterImpl(FiltersAdvancedView filtersAdvancedView, Cuenta cuenta) {
        this.mFiltersAdvancedView = filtersAdvancedView;
        this.d = cuenta;
    }

    public void onStartPresenter() {
        a();
        b();
    }

    private void a() {
        if (TypeFilterAdvanced.ADVANCED_SEARCH.equals(this.mFiltersAdvancedView.getCurrentTypeFilter())) {
            this.mFiltersAdvancedView.setTitleFilter(R.string.ID109_ACCOUNTS_AVDSEARCH_LBL_SEARCH);
        } else if (TypeFilterAdvanced.GO_TO_DATE.equals(this.mFiltersAdvancedView.getCurrentTypeFilter())) {
            this.mFiltersAdvancedView.setTitleFilter(R.string.ID106_ACCOUNTS_DATE_LBL_DATE);
        }
    }

    private void b() {
        if (TypeFilterAdvanced.ADVANCED_SEARCH.equals(this.mFiltersAdvancedView.getCurrentTypeFilter())) {
            this.mFiltersAdvancedView.setLabelFromDate(R.string.ID110_ACCOUNTS_AVDSEARCH_PCK_FROM);
        } else if (TypeFilterAdvanced.GO_TO_DATE.equals(this.mFiltersAdvancedView.getCurrentTypeFilter())) {
            this.mFiltersAdvancedView.setLabelFromDate(R.string.ID99_ACCOUNTS_CHANGEACC_ACT_IR);
        }
    }

    public void onDatePickerFromClicked(Date date) {
        this.mFiltersAdvancedView.setDateFrom(new DateTime((Object) date).toString(this.mFiltersAdvancedView.getContext().getString(R.string.FORMAT_FULL_DATE)));
    }

    public void onDatePickerToClicked(Date date) {
        this.mFiltersAdvancedView.setDateTo(new DateTime((Object) date).toString(this.mFiltersAdvancedView.getContext().getString(R.string.FORMAT_FULL_DATE)));
    }

    public void acceptFilters() {
        ((SantanderRioMainActivity) this.mFiltersAdvancedView.getContext()).hideKeyboard();
        if (c()) {
            this.mFiltersAdvancedView.onAcceptFiltersClicked(getFilterTransactionsAccount());
            n();
        }
    }

    private boolean c() {
        FilterTransactionsAccount filterTransactionsAccount = getFilterTransactionsAccount();
        if (filterTransactionsAccount.fromDate != null) {
            ValidateFilters validateFilters = this.a;
            if (ValidateFilters.isDateAfterToToday(UtilDate.getDateFromString(filterTransactionsAccount.fromDate, Constants.FORMAT_DATE_WS_1))) {
                a(this.mFiltersAdvancedView.getContext().getString(R.string.MSG_USER000037_Movimientos_errorFecha));
                return false;
            }
        }
        if (filterTransactionsAccount.toDate != null) {
            ValidateFilters validateFilters2 = this.a;
            if (ValidateFilters.isDateAfterToToday(UtilDate.getDateFromString(filterTransactionsAccount.toDate, Constants.FORMAT_DATE_WS_1))) {
                a(this.mFiltersAdvancedView.getContext().getString(R.string.MSG_USER000037_Movimientos_errorFecha));
                return false;
            }
        }
        if (!(filterTransactionsAccount.fromDate == null || filterTransactionsAccount.toDate == null)) {
            ValidateFilters validateFilters3 = this.a;
            if (!ValidateFilters.isDateBeforeOrEquals(UtilDate.getDateFromString(filterTransactionsAccount.fromDate, Constants.FORMAT_DATE_WS_1), UtilDate.getDateFromString(filterTransactionsAccount.toDate, Constants.FORMAT_DATE_WS_1))) {
                a(this.mFiltersAdvancedView.getContext().getString(R.string.MSG_USER000035_Movimientos_errorFechaDesde));
                return false;
            }
        }
        if (!(filterTransactionsAccount.fromDate == null || filterTransactionsAccount.toDate == null)) {
            ValidateFilters validateFilters4 = this.a;
            if (!ValidateFilters.isDiffBetweenDate(UtilDate.getDateFromString(filterTransactionsAccount.fromDate, Constants.FORMAT_DATE_WS_1), UtilDate.getDateFromString(filterTransactionsAccount.toDate, Constants.FORMAT_DATE_WS_1), 60)) {
                a(this.mFiltersAdvancedView.getContext().getString(R.string.IDXX_FILTER_TRANSACTION_MAX_DAYS, new Object[]{Integer.valueOf(60)}));
                return false;
            }
        }
        if (filterTransactionsAccount.fromAmount != null) {
            ValidateFilters validateFilters5 = this.a;
            if (!ValidateFilters.isValidAmount(filterTransactionsAccount.fromAmount)) {
                a(this.mFiltersAdvancedView.getContext().getString(R.string.MSG_USER000039_PagoTarj_errorImporte));
                return false;
            }
        }
        if (filterTransactionsAccount.toAmount != null) {
            ValidateFilters validateFilters6 = this.a;
            if (!ValidateFilters.isValidAmount(filterTransactionsAccount.toAmount)) {
                a(this.mFiltersAdvancedView.getContext().getString(R.string.MSG_USER000039_PagoTarj_errorImporte));
                return false;
            }
        }
        if (!(filterTransactionsAccount.toAmount == null || filterTransactionsAccount.fromAmount == null)) {
            ValidateFilters validateFilters7 = this.a;
            if (!ValidateFilters.isMinorOrEquals(filterTransactionsAccount.fromAmount, filterTransactionsAccount.toAmount)) {
                a(this.mFiltersAdvancedView.getContext().getString(R.string.MSG_USER000036_Movimientos_errorImporteDesde));
                return false;
            }
        }
        return true;
    }

    private void a(String str) {
        this.mFiltersAdvancedView.showMessage(str);
    }

    public void cancelFilters() {
        ((SantanderRioMainActivity) this.mFiltersAdvancedView.getContext()).hideKeyboard();
        n();
    }

    public void createFilterForm(ConsDescriptionBodyResponseBean consDescriptionBodyResponseBean, TypeFilterAdvanced typeFilterAdvanced, FilterTransactionsAccount filterTransactionsAccount) {
        if (TypeFilterAdvanced.ADVANCED_SEARCH.equals(typeFilterAdvanced)) {
            this.c = this.mFiltersAdvancedView.getContext().getString(R.string.ID109_ACCOUNTS_AVDSEARCH_LBL_SEARCH);
            b(consDescriptionBodyResponseBean, filterTransactionsAccount);
            return;
        }
        this.c = this.mFiltersAdvancedView.getContext().getString(R.string.ID99_ACCOUNTS_CHANGEACC_ACT_IR);
        a(consDescriptionBodyResponseBean, filterTransactionsAccount);
    }

    private void a(ConsDescriptionBodyResponseBean consDescriptionBodyResponseBean, FilterTransactionsAccount filterTransactionsAccount) {
        this.b = consDescriptionBodyResponseBean;
        this.mFiltersAdvancedView.hideViewGroupToDate();
        this.mFiltersAdvancedView.hideViewGroupAmount();
        this.mFiltersAdvancedView.setDateFrom(c(filterTransactionsAccount.fromDate));
        this.mFiltersAdvancedView.setDateTo(null);
        this.mFiltersAdvancedView.hideViewTypeAmount();
        c(consDescriptionBodyResponseBean, filterTransactionsAccount);
        d(filterTransactionsAccount.fromAmount);
        e(filterTransactionsAccount.toAmount);
    }

    private void b(ConsDescriptionBodyResponseBean consDescriptionBodyResponseBean, FilterTransactionsAccount filterTransactionsAccount) {
        this.b = consDescriptionBodyResponseBean;
        this.mFiltersAdvancedView.setDateFrom(c(filterTransactionsAccount.fromDate));
        this.mFiltersAdvancedView.setDateTo(c(filterTransactionsAccount.toDate));
        c(consDescriptionBodyResponseBean, filterTransactionsAccount);
        ArrayList d2 = d();
        if (filterTransactionsAccount.typeAmount != null) {
            this.mFiltersAdvancedView.setTextTypeAmount(b(e(), filterTransactionsAccount.typeAmount.value).getLabel());
        } else if (d2 != null && d2.size() > 0) {
            this.mFiltersAdvancedView.setTextTypeAmount((String) d2.get(0));
        }
        this.mFiltersAdvancedView.setSelectorAmount(d2);
        d(filterTransactionsAccount.fromAmount);
        e(filterTransactionsAccount.toAmount);
    }

    private void c(ConsDescriptionBodyResponseBean consDescriptionBodyResponseBean, FilterTransactionsAccount filterTransactionsAccount) {
        ArrayList itemTypeTransactions = getItemTypeTransactions(consDescriptionBodyResponseBean);
        if (filterTransactionsAccount.typeTransaction != null) {
            this.mFiltersAdvancedView.setTextTypeTransaction(b(getListItemsTypeTransactions(consDescriptionBodyResponseBean), filterTransactionsAccount.typeTransaction.value).getLabel());
        } else if (itemTypeTransactions != null && itemTypeTransactions.size() > 0) {
            this.mFiltersAdvancedView.setTextTypeTransaction((String) itemTypeTransactions.get(0));
        }
        this.mFiltersAdvancedView.setSelectorTransactions(itemTypeTransactions);
    }

    private ArrayList<String> d() {
        ArrayList<String> arrayList = new ArrayList<>();
        SparseArray e = e();
        for (int i = 0; i < e.size(); i++) {
            arrayList.add(((OptionsTransactions) e.get(i)).getLabel());
        }
        return arrayList;
    }

    private SparseArray<OptionsTransactions> e() {
        return ConstantsTransactions.getAllAmounts(this.mFiltersAdvancedView.getContext());
    }

    public ArrayList<String> getItemTypeTransactions(ConsDescriptionBodyResponseBean consDescriptionBodyResponseBean) {
        ArrayList<String> arrayList = new ArrayList<>();
        SparseArray listItemsTypeTransactions = getListItemsTypeTransactions(consDescriptionBodyResponseBean);
        if (listItemsTypeTransactions != null) {
            for (int i = 0; i < listItemsTypeTransactions.size(); i++) {
                arrayList.add(((OptionsTransactions) listItemsTypeTransactions.get(i)).getLabel());
            }
        }
        return arrayList;
    }

    public SparseArray<OptionsTransactions> getListItemsTypeTransactions(ConsDescriptionBodyResponseBean consDescriptionBodyResponseBean) {
        SparseArray<OptionsTransactions> sparseArray = new SparseArray<>();
        if (!(consDescriptionBodyResponseBean == null || consDescriptionBodyResponseBean.listTableBeans == null || consDescriptionBodyResponseBean.listTableBeans.size() <= 0)) {
            ListTableBean listTableBean = null;
            for (int i = 0; i < consDescriptionBodyResponseBean.listTableBeans.size(); i++) {
                if (ConstantsTransactions.INDEX_TYPE_ACCOUNT_LARGE.equals(((ListTableBean) consDescriptionBodyResponseBean.listTableBeans.get(i)).idTable)) {
                    listTableBean = (ListTableBean) consDescriptionBodyResponseBean.listTableBeans.get(i);
                }
            }
            if (!(listTableBean == null || listTableBean.listGroupBeans == null)) {
                for (int i2 = 0; i2 < listTableBean.listGroupBeans.size(); i2++) {
                    sparseArray.put(sparseArray.size(), new OptionsTransactions(((ListGroupBean) listTableBean.listGroupBeans.get(i2)).code, ((ListGroupBean) listTableBean.listGroupBeans.get(i2)).getLabel()));
                }
            }
        }
        return sparseArray;
    }

    public FilterTransactionsAccount getFilterTransactionsAccount() {
        FilterTransactionsAccount filterTransactionsAccount = new FilterTransactionsAccount();
        filterTransactionsAccount.label = this.c;
        filterTransactionsAccount.fromDate = g();
        filterTransactionsAccount.toDate = h();
        filterTransactionsAccount.fromAmount = i();
        filterTransactionsAccount.toAmount = j();
        filterTransactionsAccount.typeAmount = k();
        filterTransactionsAccount.typeTransaction = l();
        filterTransactionsAccount.advancedSearch = f();
        filterTransactionsAccount.typeFilterAdvanced = m();
        return filterTransactionsAccount;
    }

    private String f() {
        if (this.mFiltersAdvancedView.getEditAdvancedSearch().getText() != null) {
            return this.mFiltersAdvancedView.getEditAdvancedSearch().getText().toString();
        }
        return null;
    }

    private String g() {
        if (this.mFiltersAdvancedView.getViewFromDate().getText() != null) {
            return b(this.mFiltersAdvancedView.getViewFromDate().getText().toString());
        }
        return null;
    }

    private String h() {
        if (this.mFiltersAdvancedView.getViewToDate().getText() == null || this.mFiltersAdvancedView.getViewToDate().getText().length() <= 0) {
            return g();
        }
        return b(this.mFiltersAdvancedView.getViewToDate().getText().toString());
    }

    private String i() {
        if (this.mFiltersAdvancedView.getViewFromAmount() == null || this.mFiltersAdvancedView.getViewFromAmount().getText().length() <= 0) {
            return null;
        }
        return CAmountWebService.getAmountToWebService(Double.valueOf(this.mFiltersAdvancedView.getViewFromAmount().getNumericValue()), true);
    }

    private String j() {
        if (this.mFiltersAdvancedView.getViewToAmount() == null || this.mFiltersAdvancedView.getViewToAmount().getText().length() <= 0) {
            return null;
        }
        return CAmountWebService.getAmountToWebService(Double.valueOf(this.mFiltersAdvancedView.getViewToAmount().getNumericValue()), true);
    }

    private OptionsTransactions k() {
        String textTypeAmount = this.mFiltersAdvancedView.getTextTypeAmount();
        if (textTypeAmount != null || !"".equals(textTypeAmount)) {
            OptionsTransactions a2 = a(ConstantsTransactions.getAllAmounts(this.mFiltersAdvancedView.getContext()), textTypeAmount);
            if (a2 != null) {
                return a2;
            }
        }
        return ConstantsTransactions.getTypeAmountDefault(this.mFiltersAdvancedView.getContext());
    }

    private OptionsTransactions l() {
        String textTypeTransaction = this.mFiltersAdvancedView.getTextTypeTransaction();
        if (textTypeTransaction != null || !"".equals(textTypeTransaction)) {
            OptionsTransactions a2 = a(getListItemsTypeTransactions(this.b), textTypeTransaction);
            if (a2 != null) {
                return a2;
            }
        }
        return ConstantsTransactions.getTypeTransactionDefault(this.mFiltersAdvancedView.getContext());
    }

    private TypeFilterAdvanced m() {
        if (TypeFilterAdvanced.ADVANCED_SEARCH.equals(this.mFiltersAdvancedView.getCurrentTypeFilter())) {
            return TypeFilterAdvanced.ADVANCED_SEARCH;
        }
        if (TypeFilterAdvanced.GO_TO_DATE.equals(this.mFiltersAdvancedView.getCurrentTypeFilter())) {
            return TypeFilterAdvanced.GO_TO_DATE;
        }
        return null;
    }

    private String b(String str) {
        try {
            return DateTime.parse(str, DateTimeFormat.forPattern(this.mFiltersAdvancedView.getContext().getString(R.string.FORMAT_FULL_DATE))).toString(Constants.FORMAT_DATE_WS_1);
        } catch (Exception e) {
            e.fillInStackTrace();
            return null;
        }
    }

    private OptionsTransactions a(SparseArray<OptionsTransactions> sparseArray, String str) {
        if (sparseArray != null) {
            try {
                if (sparseArray.size() > 0 && str != null) {
                    for (int i = 0; i < sparseArray.size(); i++) {
                        if (str.toLowerCase().equals(((OptionsTransactions) sparseArray.get(i)).getLabel().toLowerCase())) {
                            return (OptionsTransactions) sparseArray.get(i);
                        }
                    }
                }
            } catch (Exception unused) {
            }
        }
        return null;
    }

    private OptionsTransactions b(SparseArray<OptionsTransactions> sparseArray, String str) {
        if (sparseArray != null) {
            try {
                if (sparseArray.size() > 0 && str != null) {
                    for (int i = 0; i < sparseArray.size(); i++) {
                        if (str.toLowerCase().equals(((OptionsTransactions) sparseArray.get(i)).value.toLowerCase())) {
                            return (OptionsTransactions) sparseArray.get(i);
                        }
                    }
                }
            } catch (Exception unused) {
            }
        }
        return null;
    }

    private void n() {
        this.mFiltersAdvancedView.lockMenu(false);
        this.mFiltersAdvancedView.restoreMainActionBar();
        ((SantanderRioMainActivity) this.mFiltersAdvancedView.getContext()).backLastFragment();
    }

    private String o() {
        return DateTime.now().toString(this.mFiltersAdvancedView.getContext().getString(R.string.FORMAT_FULL_DATE));
    }

    private String c(String str) {
        String str2;
        if (str != null) {
            try {
                str2 = UtilDate.getDateWithFullNameMonth(str, Constants.FORMAT_DATE_WS_1, this.mFiltersAdvancedView.getContext().getString(R.string.FORMAT_FULL_DATE));
            } catch (Exception unused) {
                return o();
            }
        } else {
            str2 = o();
        }
        return str2;
    }

    private void d(String str) {
        if (str != null) {
            this.mFiltersAdvancedView.setFromAmount(CAmount.getInstance(str).getAmount());
        }
    }

    private void e(String str) {
        if (str != null) {
            this.mFiltersAdvancedView.setToAmount(CAmount.getInstance(str).getAmount());
        }
    }
}
