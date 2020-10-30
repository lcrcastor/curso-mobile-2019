package ar.com.santander.rio.mbanking.app.module.payments.phones.analytics;

import android.content.Context;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.module.payments.commons.PaymentsAnalytics;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;

public class PhoneTopUpAnalyticsImp implements PaymentsAnalytics {
    private Context a;
    private AnalyticsManager b;

    public void registerScreenPrepareWithOutAmount() {
    }

    public PhoneTopUpAnalyticsImp(Context context, AnalyticsManager analyticsManager) {
        this.a = context;
        this.b = analyticsManager;
    }

    public void registerScreenHome() {
        this.b.trackScreen(this.a.getString(R.string.analytics_screen_name_phone_top_up_home));
    }

    public void registerScreenPrepareWithAmount() {
        this.b.trackScreen(this.a.getString(R.string.analytics_screen_name_prepare_phone_top_up_home));
    }

    public void registerScreenConfirm() {
        this.b.trackScreen(this.a.getString(R.string.analytics_screen_name_confirm_phone_top_up_home));
    }

    public void registerDialogConfirm() {
        this.b.trackScreen(this.a.getString(R.string.analytics_screen_name_show_dialog_confirm_phone_top_up_home));
    }

    public void registerReceipt() {
        this.b.trackScreen(this.a.getString(R.string.analytics_screen_name_receipt_phone_top_up_home));
    }

    public void registerEventCancelDialogConfirm() {
        this.b.trackEvent(this.a.getString(R.string.analytics_category_phone_top_up), this.a.getString(R.string.analytics_action_phone_top_up_cancel_dialog_confirm), this.a.getString(R.string.analytics_label_phone_top_up_cancel_dialog_confirm));
    }

    public void registerEventShareReceipt(int i) {
        this.b.trackEvent(this.a.getString(R.string.analytics_category_phone_top_up), this.a.getString(R.string.analytics_action_phone_top_up_share_receipt), this.a.getString(i));
    }

    public void registerTransactionHits(String str) {
        this.b.trackTransaction(this.a.getString(R.string.transaction_hit_product_name_regarga_celulares), str);
    }
}
