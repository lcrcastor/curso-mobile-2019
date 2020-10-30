package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import ar.com.santander.rio.mbanking.app.base.BaseMvpFragment;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.preferences.SettingsManager;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;
import javax.inject.Provider;

public final class NuevoPagoFragment$$InjectAdapter extends Binding<NuevoPagoFragment> implements MembersInjector<NuevoPagoFragment>, Provider<NuevoPagoFragment> {
    private Binding<AnalyticsManager> a;
    private Binding<SettingsManager> b;
    private Binding<BaseMvpFragment> c;

    public NuevoPagoFragment$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.fragments.privado.NuevoPagoFragment", "members/ar.com.santander.rio.mbanking.app.ui.fragments.privado.NuevoPagoFragment", false, NuevoPagoFragment.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager", NuevoPagoFragment.class, getClass().getClassLoader());
        this.b = linker.requestBinding("ar.com.santander.rio.mbanking.managers.preferences.SettingsManager", NuevoPagoFragment.class, getClass().getClassLoader());
        this.c = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.base.BaseMvpFragment", NuevoPagoFragment.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
        set2.add(this.c);
    }

    public NuevoPagoFragment get() {
        NuevoPagoFragment nuevoPagoFragment = new NuevoPagoFragment();
        injectMembers(nuevoPagoFragment);
        return nuevoPagoFragment;
    }

    public void injectMembers(NuevoPagoFragment nuevoPagoFragment) {
        nuevoPagoFragment.a = (AnalyticsManager) this.a.get();
        nuevoPagoFragment.b = (SettingsManager) this.b.get();
        this.c.injectMembers(nuevoPagoFragment);
    }
}
