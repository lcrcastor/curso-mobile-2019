package ar.com.santander.rio.mbanking.app.ui.activities;

import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;

public final class BuySellDollarsReceiptActivity$$InjectAdapter extends Binding<BuySellDollarsReceiptActivity> implements MembersInjector<BuySellDollarsReceiptActivity> {
    private Binding<SessionManager> a;
    private Binding<AnalyticsManager> b;
    private Binding<MvpPrivateMenuActivity> c;

    public BuySellDollarsReceiptActivity$$InjectAdapter() {
        super(null, "members/ar.com.santander.rio.mbanking.app.ui.activities.BuySellDollarsReceiptActivity", false, BuySellDollarsReceiptActivity.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.managers.session.SessionManager", BuySellDollarsReceiptActivity.class, getClass().getClassLoader());
        this.b = linker.requestBinding("ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager", BuySellDollarsReceiptActivity.class, getClass().getClassLoader());
        this.c = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.ui.activities.MvpPrivateMenuActivity", BuySellDollarsReceiptActivity.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
        set2.add(this.c);
    }

    public void injectMembers(BuySellDollarsReceiptActivity buySellDollarsReceiptActivity) {
        buySellDollarsReceiptActivity.mSessionManager = (SessionManager) this.a.get();
        buySellDollarsReceiptActivity.p = (AnalyticsManager) this.b.get();
        this.c.injectMembers(buySellDollarsReceiptActivity);
    }
}
