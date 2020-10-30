package ar.com.santander.rio.mbanking.app.ui.fragments.publico;

import ar.com.santander.rio.mbanking.app.base.BaseFragment;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;
import javax.inject.Provider;

public final class SucursalesFragment$$InjectAdapter extends Binding<SucursalesFragment> implements MembersInjector<SucursalesFragment>, Provider<SucursalesFragment> {
    private Binding<IDataManager> a;
    private Binding<AnalyticsManager> b;
    private Binding<SessionManager> c;
    private Binding<BaseFragment> d;

    public SucursalesFragment$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.fragments.publico.SucursalesFragment", "members/ar.com.santander.rio.mbanking.app.ui.fragments.publico.SucursalesFragment", false, SucursalesFragment.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.managers.data.IDataManager", SucursalesFragment.class, getClass().getClassLoader());
        this.b = linker.requestBinding("ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager", SucursalesFragment.class, getClass().getClassLoader());
        this.c = linker.requestBinding("ar.com.santander.rio.mbanking.managers.session.SessionManager", SucursalesFragment.class, getClass().getClassLoader());
        this.d = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.base.BaseFragment", SucursalesFragment.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
        set2.add(this.c);
        set2.add(this.d);
    }

    public SucursalesFragment get() {
        SucursalesFragment sucursalesFragment = new SucursalesFragment();
        injectMembers(sucursalesFragment);
        return sucursalesFragment;
    }

    public void injectMembers(SucursalesFragment sucursalesFragment) {
        sucursalesFragment.dataManager = (IDataManager) this.a.get();
        sucursalesFragment.analyticsManager = (AnalyticsManager) this.b.get();
        sucursalesFragment.a = (SessionManager) this.c.get();
        this.d.injectMembers(sucursalesFragment);
    }
}
