package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import ar.com.santander.rio.mbanking.app.base.BaseFragment;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;
import javax.inject.Provider;

public final class UbicacionSeguroObjetoFragment$$InjectAdapter extends Binding<UbicacionSeguroObjetoFragment> implements MembersInjector<UbicacionSeguroObjetoFragment>, Provider<UbicacionSeguroObjetoFragment> {
    private Binding<AnalyticsManager> a;
    private Binding<BaseFragment> b;

    public UbicacionSeguroObjetoFragment$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.fragments.privado.UbicacionSeguroObjetoFragment", "members/ar.com.santander.rio.mbanking.app.ui.fragments.privado.UbicacionSeguroObjetoFragment", false, UbicacionSeguroObjetoFragment.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager", UbicacionSeguroObjetoFragment.class, getClass().getClassLoader());
        this.b = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.base.BaseFragment", UbicacionSeguroObjetoFragment.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
    }

    public UbicacionSeguroObjetoFragment get() {
        UbicacionSeguroObjetoFragment ubicacionSeguroObjetoFragment = new UbicacionSeguroObjetoFragment();
        injectMembers(ubicacionSeguroObjetoFragment);
        return ubicacionSeguroObjetoFragment;
    }

    public void injectMembers(UbicacionSeguroObjetoFragment ubicacionSeguroObjetoFragment) {
        ubicacionSeguroObjetoFragment.analyticsManager = (AnalyticsManager) this.a.get();
        this.b.injectMembers(ubicacionSeguroObjetoFragment);
    }
}
