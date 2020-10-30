package ar.com.santander.rio.mbanking.app.ui.activities;

import ar.com.santander.rio.mbanking.app.base.BaseMvpActivity;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;

public final class BuySellDollarsConfirmationActivity$$InjectAdapter extends Binding<BuySellDollarsConfirmationActivity> implements MembersInjector<BuySellDollarsConfirmationActivity> {
    private Binding<SessionManager> a;
    private Binding<AnalyticsManager> b;
    private Binding<BaseMvpActivity> c;

    public BuySellDollarsConfirmationActivity$$InjectAdapter() {
        super(null, "members/ar.com.santander.rio.mbanking.app.ui.activities.BuySellDollarsConfirmationActivity", false, BuySellDollarsConfirmationActivity.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.managers.session.SessionManager", BuySellDollarsConfirmationActivity.class, getClass().getClassLoader());
        this.b = linker.requestBinding("ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager", BuySellDollarsConfirmationActivity.class, getClass().getClassLoader());
        this.c = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.base.BaseMvpActivity", BuySellDollarsConfirmationActivity.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
        set2.add(this.c);
    }

    public void injectMembers(BuySellDollarsConfirmationActivity buySellDollarsConfirmationActivity) {
        buySellDollarsConfirmationActivity.mSessionManager = (SessionManager) this.a.get();
        buySellDollarsConfirmationActivity.p = (AnalyticsManager) this.b.get();
        this.c.injectMembers(buySellDollarsConfirmationActivity);
    }
}
