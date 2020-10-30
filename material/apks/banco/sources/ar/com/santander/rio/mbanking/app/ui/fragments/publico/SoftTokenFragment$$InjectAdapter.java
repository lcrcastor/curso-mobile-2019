package ar.com.santander.rio.mbanking.app.ui.fragments.publico;

import ar.com.santander.rio.mbanking.app.base.BaseFragment;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.security.SoftTokenManager;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;
import javax.inject.Provider;

public final class SoftTokenFragment$$InjectAdapter extends Binding<SoftTokenFragment> implements MembersInjector<SoftTokenFragment>, Provider<SoftTokenFragment> {
    private Binding<SoftTokenManager> a;
    private Binding<AnalyticsManager> b;
    private Binding<BaseFragment> c;

    public SoftTokenFragment$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.fragments.publico.SoftTokenFragment", "members/ar.com.santander.rio.mbanking.app.ui.fragments.publico.SoftTokenFragment", false, SoftTokenFragment.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.managers.security.SoftTokenManager", SoftTokenFragment.class, getClass().getClassLoader());
        this.b = linker.requestBinding("ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager", SoftTokenFragment.class, getClass().getClassLoader());
        this.c = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.base.BaseFragment", SoftTokenFragment.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
        set2.add(this.c);
    }

    public SoftTokenFragment get() {
        SoftTokenFragment softTokenFragment = new SoftTokenFragment();
        injectMembers(softTokenFragment);
        return softTokenFragment;
    }

    public void injectMembers(SoftTokenFragment softTokenFragment) {
        softTokenFragment.a = (SoftTokenManager) this.a.get();
        softTokenFragment.b = (AnalyticsManager) this.b.get();
        this.c.injectMembers(softTokenFragment);
    }
}
