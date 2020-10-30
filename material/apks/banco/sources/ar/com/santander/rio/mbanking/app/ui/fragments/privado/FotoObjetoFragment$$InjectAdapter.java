package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import ar.com.santander.rio.mbanking.app.base.BaseFragment;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;
import javax.inject.Provider;

public final class FotoObjetoFragment$$InjectAdapter extends Binding<FotoObjetoFragment> implements MembersInjector<FotoObjetoFragment>, Provider<FotoObjetoFragment> {
    private Binding<AnalyticsManager> a;
    private Binding<BaseFragment> b;

    public FotoObjetoFragment$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.fragments.privado.FotoObjetoFragment", "members/ar.com.santander.rio.mbanking.app.ui.fragments.privado.FotoObjetoFragment", false, FotoObjetoFragment.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager", FotoObjetoFragment.class, getClass().getClassLoader());
        this.b = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.base.BaseFragment", FotoObjetoFragment.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
    }

    public FotoObjetoFragment get() {
        FotoObjetoFragment fotoObjetoFragment = new FotoObjetoFragment();
        injectMembers(fotoObjetoFragment);
        return fotoObjetoFragment;
    }

    public void injectMembers(FotoObjetoFragment fotoObjetoFragment) {
        fotoObjetoFragment.analyticsManager = (AnalyticsManager) this.a.get();
        this.b.injectMembers(fotoObjetoFragment);
    }
}
