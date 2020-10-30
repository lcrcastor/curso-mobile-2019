package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import ar.com.santander.rio.mbanking.app.base.BaseMvpFragment;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;
import javax.inject.Provider;

public final class BuySellDollarsFragment$$InjectAdapter extends Binding<BuySellDollarsFragment> implements MembersInjector<BuySellDollarsFragment>, Provider<BuySellDollarsFragment> {
    private Binding<SessionManager> a;
    private Binding<AnalyticsManager> b;
    private Binding<BaseMvpFragment> c;

    public BuySellDollarsFragment$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.fragments.privado.BuySellDollarsFragment", "members/ar.com.santander.rio.mbanking.app.ui.fragments.privado.BuySellDollarsFragment", false, BuySellDollarsFragment.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.managers.session.SessionManager", BuySellDollarsFragment.class, getClass().getClassLoader());
        this.b = linker.requestBinding("ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager", BuySellDollarsFragment.class, getClass().getClassLoader());
        this.c = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.base.BaseMvpFragment", BuySellDollarsFragment.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
        set2.add(this.c);
    }

    public BuySellDollarsFragment get() {
        BuySellDollarsFragment buySellDollarsFragment = new BuySellDollarsFragment();
        injectMembers(buySellDollarsFragment);
        return buySellDollarsFragment;
    }

    public void injectMembers(BuySellDollarsFragment buySellDollarsFragment) {
        buySellDollarsFragment.mSessionManager = (SessionManager) this.a.get();
        buySellDollarsFragment.a = (AnalyticsManager) this.b.get();
        this.c.injectMembers(buySellDollarsFragment);
    }
}
