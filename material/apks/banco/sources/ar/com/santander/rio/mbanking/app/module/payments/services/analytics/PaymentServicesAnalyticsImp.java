package ar.com.santander.rio.mbanking.app.module.payments.services.analytics;

import android.content.Context;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.module.payments.commons.PaymentsAnalytics;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;

public class PaymentServicesAnalyticsImp implements PaymentsAnalytics {
    private Context a;
    private AnalyticsManager b;

    public PaymentServicesAnalyticsImp(Context context, AnalyticsManager analyticsManager) {
        this.a = context;
        this.b = analyticsManager;
    }

    public void registerScreenHome() {
        this.b.trackScreen(this.a.getString(R.string.analytics_screen_name_payment_services_home));
    }

    public void registerScreenPrepareWithAmount() {
        this.b.trackScreen(this.a.getString(R.string.analytics_screen_name_prepare_with_amount_payment_services_home));
    }

    public void registerScreenPrepareWithOutAmount() {
        this.b.trackScreen(this.a.getString(R.string.analytics_screen_name_prepare_with_out_amount_payment_services_home));
    }

    public void registerScreenConfirm() {
        this.b.trackScreen(this.a.getString(R.string.analytics_screen_name_confirm_payment_services_home));
    }

    public void registerDialogConfirm() {
        this.b.trackScreen(this.a.getString(R.string.analytics_screen_name_show_dialog_confirm_payment_services_home));
    }

    public void registerReceipt() {
        this.b.trackScreen(this.a.getString(R.string.analytics_screen_name_receipt_payment_services_home));
    }

    public void registerEventCancelDialogConfirm() {
        this.b.trackEvent(this.a.getString(R.string.analytics_category_payment_services), this.a.getString(R.string.analytics_action_payment_services_cancel_dialog_confirm), this.a.getString(R.string.analytics_label_payment_services_cancel_dialog_confirm));
    }

    public void registerEventShareReceipt(int i) {
        this.b.trackEvent(this.a.getString(R.string.analytics_category_payment_services), this.a.getString(R.string.analytics_action_payment_services_share_receipt), this.a.getString(i));
    }

    public void registerTransactionHits(String str) {
        this.b.trackTransaction(this.a.getString(R.string.transaction_hit_product_name_pago_servicio), str);
    }
}
