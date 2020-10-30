package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import ar.com.santander.rio.mbanking.app.base.BaseMvpFragment;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;
import javax.inject.Provider;

public final class MarcacionPorViajeFragment$$InjectAdapter extends Binding<MarcacionPorViajeFragment> implements MembersInjector<MarcacionPorViajeFragment>, Provider<MarcacionPorViajeFragment> {
    private Binding<SessionManager> a;
    private Binding<AnalyticsManager> b;
    private Binding<BaseMvpFragment> c;

    public MarcacionPorViajeFragment$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.fragments.privado.MarcacionPorViajeFragment", "members/ar.com.santander.rio.mbanking.app.ui.fragments.privado.MarcacionPorViajeFragment", false, MarcacionPorViajeFragment.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.managers.session.SessionManager", MarcacionPorViajeFragment.class, getClass().getClassLoader());
        this.b = linker.requestBinding("ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager", MarcacionPorViajeFragment.class, getClass().getClassLoader());
        this.c = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.base.BaseMvpFragment", MarcacionPorViajeFragment.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
        set2.add(this.c);
    }

    public MarcacionPorViajeFragment get() {
        MarcacionPorViajeFragment marcacionPorViajeFragment = new MarcacionPorViajeFragment();
        injectMembers(marcacionPorViajeFragment);
        return marcacionPorViajeFragment;
    }

    public void injectMembers(MarcacionPorViajeFragment marcacionPorViajeFragment) {
        marcacionPorViajeFragment.h = (SessionManager) this.a.get();
        marcacionPorViajeFragment.i = (AnalyticsManager) this.b.get();
        this.c.injectMembers(marcacionPorViajeFragment);
    }
}
