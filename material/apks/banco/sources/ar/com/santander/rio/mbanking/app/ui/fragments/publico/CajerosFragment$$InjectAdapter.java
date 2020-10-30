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

public final class CajerosFragment$$InjectAdapter extends Binding<CajerosFragment> implements MembersInjector<CajerosFragment>, Provider<CajerosFragment> {
    private Binding<IDataManager> a;
    private Binding<AnalyticsManager> b;
    private Binding<SettingsManager> c;
    private Binding<BaseFragment> d;

    public CajerosFragment$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.fragments.publico.CajerosFragment", "members/ar.com.santander.rio.mbanking.app.ui.fragments.publico.CajerosFragment", false, CajerosFragment.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.managers.data.IDataManager", CajerosFragment.class, getClass().getClassLoader());
        this.b = linker.requestBinding("ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager", CajerosFragment.class, getClass().getClassLoader());
        this.c = linker.requestBinding("ar.com.santander.rio.mbanking.managers.preferences.SettingsManager", CajerosFragment.class, getClass().getClassLoader());
        this.d = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.base.BaseFragment", CajerosFragment.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
        set2.add(this.c);
        set2.add(this.d);
    }

    public CajerosFragment get() {
        CajerosFragment cajerosFragment = new CajerosFragment();
        injectMembers(cajerosFragment);
        return cajerosFragment;
    }

    public void injectMembers(CajerosFragment cajerosFragment) {
        cajerosFragment.a = (IDataManager) this.a.get();
        cajerosFragment.b = (AnalyticsManager) this.b.get();
        cajerosFragment.c = (SettingsManager) this.c.get();
        this.d.injectMembers(cajerosFragment);
    }
}
