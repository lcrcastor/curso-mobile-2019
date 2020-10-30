package ar.com.santander.rio.mbanking.app.ui.fragments.publico;

import ar.com.santander.rio.mbanking.app.base.BaseFragment;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.managers.preferences.SettingsManager;
import ar.com.santander.rio.mbanking.managers.security.SoftTokenManager;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;
import javax.inject.Provider;

public final class RegistrarSubeFragment$$InjectAdapter extends Binding<RegistrarSubeFragment> implements MembersInjector<RegistrarSubeFragment>, Provider<RegistrarSubeFragment> {
    private Binding<SettingsManager> a;
    private Binding<IDataManager> b;
    private Binding<AnalyticsManager> c;
    private Binding<SoftTokenManager> d;
    private Binding<BaseFragment> e;

    public RegistrarSubeFragment$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.fragments.publico.RegistrarSubeFragment", "members/ar.com.santander.rio.mbanking.app.ui.fragments.publico.RegistrarSubeFragment", false, RegistrarSubeFragment.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.managers.preferences.SettingsManager", RegistrarSubeFragment.class, getClass().getClassLoader());
        this.b = linker.requestBinding("ar.com.santander.rio.mbanking.managers.data.IDataManager", RegistrarSubeFragment.class, getClass().getClassLoader());
        this.c = linker.requestBinding("ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager", RegistrarSubeFragment.class, getClass().getClassLoader());
        this.d = linker.requestBinding("ar.com.santander.rio.mbanking.managers.security.SoftTokenManager", RegistrarSubeFragment.class, getClass().getClassLoader());
        this.e = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.base.BaseFragment", RegistrarSubeFragment.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
        set2.add(this.c);
        set2.add(this.d);
        set2.add(this.e);
    }

    public RegistrarSubeFragment get() {
        RegistrarSubeFragment registrarSubeFragment = new RegistrarSubeFragment();
        injectMembers(registrarSubeFragment);
        return registrarSubeFragment;
    }

    public void injectMembers(RegistrarSubeFragment registrarSubeFragment) {
        registrarSubeFragment.a = (SettingsManager) this.a.get();
        registrarSubeFragment.mDataManager = (IDataManager) this.b.get();
        registrarSubeFragment.b = (AnalyticsManager) this.c.get();
        registrarSubeFragment.c = (SoftTokenManager) this.d.get();
        this.e.injectMembers(registrarSubeFragment);
    }
}
