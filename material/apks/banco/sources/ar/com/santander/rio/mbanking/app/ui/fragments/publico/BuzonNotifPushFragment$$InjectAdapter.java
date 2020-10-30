package ar.com.santander.rio.mbanking.app.ui.fragments.publico;

import ar.com.santander.rio.mbanking.app.base.BaseMvpFragment;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;
import javax.inject.Provider;

public final class BuzonNotifPushFragment$$InjectAdapter extends Binding<BuzonNotifPushFragment> implements MembersInjector<BuzonNotifPushFragment>, Provider<BuzonNotifPushFragment> {
    private Binding<AnalyticsManager> a;
    private Binding<BaseMvpFragment> b;

    public BuzonNotifPushFragment$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.fragments.publico.BuzonNotifPushFragment", "members/ar.com.santander.rio.mbanking.app.ui.fragments.publico.BuzonNotifPushFragment", false, BuzonNotifPushFragment.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager", BuzonNotifPushFragment.class, getClass().getClassLoader());
        this.b = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.base.BaseMvpFragment", BuzonNotifPushFragment.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
    }

    public BuzonNotifPushFragment get() {
        BuzonNotifPushFragment buzonNotifPushFragment = new BuzonNotifPushFragment();
        injectMembers(buzonNotifPushFragment);
        return buzonNotifPushFragment;
    }

    public void injectMembers(BuzonNotifPushFragment buzonNotifPushFragment) {
        buzonNotifPushFragment.a = (AnalyticsManager) this.a.get();
        this.b.injectMembers(buzonNotifPushFragment);
    }
}
