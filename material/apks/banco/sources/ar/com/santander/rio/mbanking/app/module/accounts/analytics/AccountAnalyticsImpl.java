package ar.com.santander.rio.mbanking.app.module.accounts.analytics;

import android.content.Context;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.module.accounts.filters.FiltersSimplePresenter.TypeFilterAdvanced;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.services.model.general.FilterTransactionsAccount;
import io.fabric.sdk.android.services.events.EventsFilesManager;

public class AccountAnalyticsImpl implements AccountAnalytics {
    private Context a;
    public AnalyticsManager mAnalyticsManager;

    public AccountAnalyticsImpl(Context context, AnalyticsManager analyticsManager) {
        this.a = context;
        this.mAnalyticsManager = analyticsManager;
    }

    public void registerScreenHome() {
        this.mAnalyticsManager.trackScreen(this.a.getString(R.string.analytics_screen_name_accounts_home));
    }

    public void registerScreenAmount() {
        this.mAnalyticsManager.trackScreen(this.a.getString(R.string.analytics_screen_name_accounts_amount));
    }

    public void registerScreenCBU() {
        this.mAnalyticsManager.trackScreen(this.a.getString(R.string.analytics_screen_name_accounts_cbu));
    }

    public void registerScreenDetailsTransaction() {
        this.mAnalyticsManager.trackScreen(this.a.getString(R.string.analytics_screen_name_accounts_details));
    }

    public void registerScreenAdvancedSearchTransactions() {
        this.mAnalyticsManager.trackScreen(this.a.getString(R.string.analytics_screen_name_accounts_search_advanced));
    }

    public void registerScreenSimpleSearchTransactions() {
        this.mAnalyticsManager.trackScreen(this.a.getString(R.string.analytics_screen_name_accounts_search_simple));
    }

    public void registerEventShare() {
        this.mAnalyticsManager.trackEvent(this.a.getString(R.string.analytics_category_accounts_and_transactions), this.a.getString(R.string.analytics_action_accounts_and_transactions_share), this.a.getString(R.string.analytics_label_accounts_and_transactions_share));
    }

    public void registerEventChangeAccount() {
        this.mAnalyticsManager.trackEvent(this.a.getString(R.string.analytics_category_accounts_and_transactions), this.a.getString(R.string.analytics_action_accounts_and_transactions_change_account), this.a.getString(R.string.analytics_label_accounts_and_transactions_change_account));
    }

    public void registerEventSimpleSearch(String str) {
        String a2 = a(str);
        if (a2 != null) {
            this.mAnalyticsManager.trackEvent(this.a.getString(R.string.analytics_category_accounts_and_transactions), this.a.getString(R.string.analytics_action_accounts_and_transactions_search_transaction), a2);
        }
    }

    public void registerEventManagerSearch(TypeFilterAdvanced typeFilterAdvanced, FilterTransactionsAccount filterTransactionsAccount) {
        String str = null;
        if (TypeFilterAdvanced.GO_TO_DATE.equals(typeFilterAdvanced)) {
            String str2 = filterTransactionsAccount.fromDate;
            if (filterTransactionsAccount.typeTransaction != null) {
                str = filterTransactionsAccount.typeTransaction.value;
            }
            a(str2, str);
            return;
        }
        String str3 = filterTransactionsAccount.fromDate;
        String str4 = filterTransactionsAccount.toDate;
        String str5 = filterTransactionsAccount.fromAmount;
        String str6 = filterTransactionsAccount.toAmount;
        String str7 = filterTransactionsAccount.toAmount != null ? filterTransactionsAccount.typeAmount.value : null;
        if (filterTransactionsAccount.typeTransaction != null) {
            str = filterTransactionsAccount.typeTransaction.value;
        }
        a(str3, str4, str5, str6, str7, str);
    }

    private void a(String str, String str2) {
        AnalyticsManager analyticsManager = this.mAnalyticsManager;
        String string = this.a.getString(R.string.analytics_category_accounts_and_transactions);
        String string2 = this.a.getString(R.string.analytics_action_accounts_and_transactions_search_by_date);
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR);
        sb.append(str2);
        analyticsManager.trackEvent(string, string2, sb.toString());
    }

    private void a(String str, String str2, String str3, String str4, String str5, String str6) {
        AnalyticsManager analyticsManager = this.mAnalyticsManager;
        String string = this.a.getString(R.string.analytics_category_accounts_and_transactions);
        String string2 = this.a.getString(R.string.analytics_action_accounts_and_transactions_advanced_search);
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR);
        sb.append(str2);
        sb.append(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR);
        sb.append(str3);
        sb.append(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR);
        sb.append(str4);
        sb.append(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR);
        sb.append(str2);
        sb.append(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR);
        sb.append(str5);
        sb.append(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR);
        sb.append(str6);
        analyticsManager.trackEvent(string, string2, sb.toString());
    }

    private String a(String str) {
        if (this.a.getString(R.string.ID96_ACCOUNTS_CHANGEACC_ACT_MOV1).equals(str)) {
            return this.a.getString(R.string.analytics_label_accounts_and_transactions_search_transaction_today);
        }
        if (this.a.getString(R.string.ID97_ACCOUNTS_CHANGEACC_ACT_MOV3).equals(str)) {
            return this.a.getString(R.string.analytics_label_accounts_and_transactions_search_transaction_3_days);
        }
        if (this.a.getString(R.string.ID98_ACCOUNTS_CHANGEACC_ACT_MOV7).equals(str)) {
            return this.a.getString(R.string.analytics_label_accounts_and_transactions_search_transaction_7_days);
        }
        return null;
    }
}
