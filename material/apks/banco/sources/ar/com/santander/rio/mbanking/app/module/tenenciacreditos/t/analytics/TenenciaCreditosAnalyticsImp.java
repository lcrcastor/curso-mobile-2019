package ar.com.santander.rio.mbanking.app.module.tenenciacreditos.t.analytics;

import android.content.Context;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.utils.UtilsCuentas;

public class TenenciaCreditosAnalyticsImp implements TenenciaCreditosAnalytics {
    private Context a;
    private AnalyticsManager b;

    public TenenciaCreditosAnalyticsImp(Context context, AnalyticsManager analyticsManager) {
        this.a = context;
        this.b = analyticsManager;
    }

    public void registerScreenConstitution() {
        this.b.trackScreen(this.a.getString(R.string.analytics_screen_name_long_term_deposit_constitution));
    }

    public void registerScreenConfirm() {
        this.b.trackScreen(this.a.getString(R.string.analytics_screen_name_long_term_deposit_confirm));
    }

    public void registerScreenDialogConfirm() {
        this.b.trackScreen(this.a.getString(R.string.analytics_screen_name_long_term_deposit_dialog_confirm));
    }

    public void registerScreenReceipt() {
        this.b.trackScreen(this.a.getString(R.string.analytics_screen_name_long_term_deposit_receipt));
    }

    public void registerEventCancelDialogConfirm() {
        this.b.trackEvent(this.a.getString(R.string.analytics_category_long_term_deposit), this.a.getString(R.string.analytics_action_long_term_deposit_cancel_dialog_confirm), this.a.getString(R.string.analytics_label_long_term_deposit_cancel_dialog_confirm));
    }

    public void registerEventShareReceipt(int i) {
        this.b.trackEvent(this.a.getString(R.string.analytics_category_long_term_deposit), this.a.getString(R.string.analytics_action_long_term_deposit_share_receipt), this.a.getString(i));
    }

    public void registerTransactionHits(String str) {
        this.b.trackTransaction(this.a.getString(R.string.transaction_hit_product_name_plazos_fijos), str);
    }

    public void registerScreenLongTermDepositDetail(String str) {
        AnalyticsManager analyticsManager = this.b;
        StringBuilder sb = new StringBuilder();
        sb.append(this.a.getString(R.string.analytics_screen_name_long_term_deposit_detail));
        sb.append(UtilsCuentas.SEPARAOR2);
        sb.append(str.toLowerCase());
        analyticsManager.trackScreen(sb.toString());
    }

    public void registerScreenRates() {
        this.b.trackScreen(this.a.getString(R.string.analytics_screen_name_long_term_deposit_valid_rates));
    }

    public void registerScreenLongTermDepositList() {
        this.b.trackScreen(this.a.getString(R.string.analytics_screen_name_long_term_deposit_list));
    }

    public void registerScreenLongTermDepositEmptyList() {
        this.b.trackScreen(this.a.getString(R.string.analytics_screen_name_long_term_deposit_list_empty));
    }

    public void registerScreenLongTermDepositListError() {
        this.b.trackScreen(this.a.getString(R.string.analytics_screen_name_long_term_deposit_list_error));
    }

    public void registerEventRatesCurrencyChange(String str) {
        if (str.equalsIgnoreCase(this.a.getString(R.string.IDXXX_F10_XX_TRADICIONAL_PESOS))) {
            this.b.trackEvent(this.a.getString(R.string.analytics_category_long_term_deposit), this.a.getString(R.string.analytics_action_long_term_deposit_switch_currency), this.a.getString(R.string.analytics_label_long_term_deposit_switch_currency_pesos));
        } else if (str.equalsIgnoreCase(this.a.getString(R.string.IDXXX_F10_XX_TRADICIONAL_DOLARES))) {
            this.b.trackEvent(this.a.getString(R.string.analytics_category_long_term_deposit), this.a.getString(R.string.analytics_action_long_term_deposit_switch_currency), this.a.getString(R.string.analytics_label_long_term_deposit_switch_currency_dolares));
        }
    }
}
