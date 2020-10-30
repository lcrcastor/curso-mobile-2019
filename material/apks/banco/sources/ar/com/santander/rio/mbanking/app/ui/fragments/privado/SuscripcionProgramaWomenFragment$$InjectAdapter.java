package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import ar.com.santander.rio.mbanking.app.base.BaseFragment;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;
import javax.inject.Provider;

public final class SuscripcionProgramaWomenFragment$$InjectAdapter extends Binding<SuscripcionProgramaWomenFragment> implements MembersInjector<SuscripcionProgramaWomenFragment>, Provider<SuscripcionProgramaWomenFragment> {
    private Binding<AnalyticsManager> a;
    private Binding<BaseFragment> b;

    public SuscripcionProgramaWomenFragment$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.fragments.privado.SuscripcionProgramaWomenFragment", "members/ar.com.santander.rio.mbanking.app.ui.fragments.privado.SuscripcionProgramaWomenFragment", false, SuscripcionProgramaWomenFragment.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager", SuscripcionProgramaWomenFragment.class, getClass().getClassLoader());
        this.b = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.base.BaseFragment", SuscripcionProgramaWomenFragment.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
    }

    public SuscripcionProgramaWomenFragment get() {
        SuscripcionProgramaWomenFragment suscripcionProgramaWomenFragment = new SuscripcionProgramaWomenFragment();
        injectMembers(suscripcionProgramaWomenFragment);
        return suscripcionProgramaWomenFragment;
    }

    public void injectMembers(SuscripcionProgramaWomenFragment suscripcionProgramaWomenFragment) {
        suscripcionProgramaWomenFragment.b = (AnalyticsManager) this.a.get();
        this.b.injectMembers(suscripcionProgramaWomenFragment);
    }
}
