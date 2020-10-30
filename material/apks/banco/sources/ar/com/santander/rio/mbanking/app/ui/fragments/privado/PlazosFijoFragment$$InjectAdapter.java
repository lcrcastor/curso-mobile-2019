package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import ar.com.santander.rio.mbanking.app.base.BaseFragment;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;
import javax.inject.Provider;

public final class PlazosFijoFragment$$InjectAdapter extends Binding<PlazosFijoFragment> implements MembersInjector<PlazosFijoFragment>, Provider<PlazosFijoFragment> {
    private Binding<IDataManager> a;
    private Binding<SessionManager> b;
    private Binding<AnalyticsManager> c;
    private Binding<AnalyticsManager> d;
    private Binding<BaseFragment> e;

    public PlazosFijoFragment$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.fragments.privado.PlazosFijoFragment", "members/ar.com.santander.rio.mbanking.app.ui.fragments.privado.PlazosFijoFragment", false, PlazosFijoFragment.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.managers.data.IDataManager", PlazosFijoFragment.class, getClass().getClassLoader());
        this.b = linker.requestBinding("ar.com.santander.rio.mbanking.managers.session.SessionManager", PlazosFijoFragment.class, getClass().getClassLoader());
        this.c = linker.requestBinding("ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager", PlazosFijoFragment.class, getClass().getClassLoader());
        this.d = linker.requestBinding("ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager", PlazosFijoFragment.class, getClass().getClassLoader());
        this.e = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.base.BaseFragment", PlazosFijoFragment.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
        set2.add(this.c);
        set2.add(this.d);
        set2.add(this.e);
    }

    public PlazosFijoFragment get() {
        PlazosFijoFragment plazosFijoFragment = new PlazosFijoFragment();
        injectMembers(plazosFijoFragment);
        return plazosFijoFragment;
    }

    public void injectMembers(PlazosFijoFragment plazosFijoFragment) {
        plazosFijoFragment.mDataManager = (IDataManager) this.a.get();
        plazosFijoFragment.mSessionManager = (SessionManager) this.b.get();
        plazosFijoFragment.mAnalyticsManager = (AnalyticsManager) this.c.get();
        plazosFijoFragment.analyticsManager = (AnalyticsManager) this.d.get();
        this.e.injectMembers(plazosFijoFragment);
    }
}
