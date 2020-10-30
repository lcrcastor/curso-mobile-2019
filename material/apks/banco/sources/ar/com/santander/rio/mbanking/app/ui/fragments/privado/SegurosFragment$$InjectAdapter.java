package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import ar.com.santander.rio.mbanking.app.base.BaseMvpFragment;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.preferences.SettingsManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;
import javax.inject.Provider;

public final class SegurosFragment$$InjectAdapter extends Binding<SegurosFragment> implements MembersInjector<SegurosFragment>, Provider<SegurosFragment> {
    private Binding<AnalyticsManager> a;
    private Binding<SettingsManager> b;
    private Binding<SessionManager> c;
    private Binding<BaseMvpFragment> d;

    public SegurosFragment$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.fragments.privado.SegurosFragment", "members/ar.com.santander.rio.mbanking.app.ui.fragments.privado.SegurosFragment", false, SegurosFragment.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager", SegurosFragment.class, getClass().getClassLoader());
        this.b = linker.requestBinding("ar.com.santander.rio.mbanking.managers.preferences.SettingsManager", SegurosFragment.class, getClass().getClassLoader());
        this.c = linker.requestBinding("ar.com.santander.rio.mbanking.managers.session.SessionManager", SegurosFragment.class, getClass().getClassLoader());
        this.d = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.base.BaseMvpFragment", SegurosFragment.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
        set2.add(this.c);
        set2.add(this.d);
    }

    public SegurosFragment get() {
        SegurosFragment segurosFragment = new SegurosFragment();
        injectMembers(segurosFragment);
        return segurosFragment;
    }

    public void injectMembers(SegurosFragment segurosFragment) {
        segurosFragment.a = (AnalyticsManager) this.a.get();
        segurosFragment.b = (SettingsManager) this.b.get();
        segurosFragment.c = (SessionManager) this.c.get();
        this.d.injectMembers(segurosFragment);
    }
}
