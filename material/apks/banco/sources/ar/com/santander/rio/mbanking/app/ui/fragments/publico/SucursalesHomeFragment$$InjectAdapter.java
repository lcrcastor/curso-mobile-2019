package ar.com.santander.rio.mbanking.app.ui.fragments.publico;

import ar.com.santander.rio.mbanking.app.base.BaseFragment;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.managers.preferences.SettingsManager;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;
import javax.inject.Provider;

public final class SucursalesHomeFragment$$InjectAdapter extends Binding<SucursalesHomeFragment> implements MembersInjector<SucursalesHomeFragment>, Provider<SucursalesHomeFragment> {
    private Binding<IDataManager> a;
    private Binding<AnalyticsManager> b;
    private Binding<SettingsManager> c;
    private Binding<BaseFragment> d;

    public SucursalesHomeFragment$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.fragments.publico.SucursalesHomeFragment", "members/ar.com.santander.rio.mbanking.app.ui.fragments.publico.SucursalesHomeFragment", false, SucursalesHomeFragment.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.managers.data.IDataManager", SucursalesHomeFragment.class, getClass().getClassLoader());
        this.b = linker.requestBinding("ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager", SucursalesHomeFragment.class, getClass().getClassLoader());
        this.c = linker.requestBinding("ar.com.santander.rio.mbanking.managers.preferences.SettingsManager", SucursalesHomeFragment.class, getClass().getClassLoader());
        this.d = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.base.BaseFragment", SucursalesHomeFragment.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
        set2.add(this.c);
        set2.add(this.d);
    }

    public SucursalesHomeFragment get() {
        SucursalesHomeFragment sucursalesHomeFragment = new SucursalesHomeFragment();
        injectMembers(sucursalesHomeFragment);
        return sucursalesHomeFragment;
    }

    public void injectMembers(SucursalesHomeFragment sucursalesHomeFragment) {
        sucursalesHomeFragment.a = (IDataManager) this.a.get();
        sucursalesHomeFragment.b = (AnalyticsManager) this.b.get();
        sucursalesHomeFragment.c = (SettingsManager) this.c.get();
        this.d.injectMembers(sucursalesHomeFragment);
    }
}
