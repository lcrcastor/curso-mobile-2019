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

public final class PromocionesFragment$$InjectAdapter extends Binding<PromocionesFragment> implements MembersInjector<PromocionesFragment>, Provider<PromocionesFragment> {
    private Binding<IDataManager> a;
    private Binding<AnalyticsManager> b;
    private Binding<SettingsManager> c;
    private Binding<BaseFragment> d;

    public PromocionesFragment$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.fragments.publico.PromocionesFragment", "members/ar.com.santander.rio.mbanking.app.ui.fragments.publico.PromocionesFragment", false, PromocionesFragment.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.managers.data.IDataManager", PromocionesFragment.class, getClass().getClassLoader());
        this.b = linker.requestBinding("ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager", PromocionesFragment.class, getClass().getClassLoader());
        this.c = linker.requestBinding("ar.com.santander.rio.mbanking.managers.preferences.SettingsManager", PromocionesFragment.class, getClass().getClassLoader());
        this.d = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.base.BaseFragment", PromocionesFragment.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
        set2.add(this.c);
        set2.add(this.d);
    }

    public PromocionesFragment get() {
        PromocionesFragment promocionesFragment = new PromocionesFragment();
        injectMembers(promocionesFragment);
        return promocionesFragment;
    }

    public void injectMembers(PromocionesFragment promocionesFragment) {
        promocionesFragment.a = (IDataManager) this.a.get();
        promocionesFragment.b = (AnalyticsManager) this.b.get();
        promocionesFragment.c = (SettingsManager) this.c.get();
        this.d.injectMembers(promocionesFragment);
    }
}
