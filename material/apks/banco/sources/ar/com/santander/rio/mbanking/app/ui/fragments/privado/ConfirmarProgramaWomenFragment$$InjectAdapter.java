package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import ar.com.santander.rio.mbanking.app.base.BaseFragment;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;
import javax.inject.Provider;

public final class ConfirmarProgramaWomenFragment$$InjectAdapter extends Binding<ConfirmarProgramaWomenFragment> implements MembersInjector<ConfirmarProgramaWomenFragment>, Provider<ConfirmarProgramaWomenFragment> {
    private Binding<AnalyticsManager> a;
    private Binding<SessionManager> b;
    private Binding<BaseFragment> c;

    public ConfirmarProgramaWomenFragment$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.fragments.privado.ConfirmarProgramaWomenFragment", "members/ar.com.santander.rio.mbanking.app.ui.fragments.privado.ConfirmarProgramaWomenFragment", false, ConfirmarProgramaWomenFragment.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager", ConfirmarProgramaWomenFragment.class, getClass().getClassLoader());
        this.b = linker.requestBinding("ar.com.santander.rio.mbanking.managers.session.SessionManager", ConfirmarProgramaWomenFragment.class, getClass().getClassLoader());
        this.c = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.base.BaseFragment", ConfirmarProgramaWomenFragment.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
        set2.add(this.c);
    }

    public ConfirmarProgramaWomenFragment get() {
        ConfirmarProgramaWomenFragment confirmarProgramaWomenFragment = new ConfirmarProgramaWomenFragment();
        injectMembers(confirmarProgramaWomenFragment);
        return confirmarProgramaWomenFragment;
    }

    public void injectMembers(ConfirmarProgramaWomenFragment confirmarProgramaWomenFragment) {
        confirmarProgramaWomenFragment.a = (AnalyticsManager) this.a.get();
        confirmarProgramaWomenFragment.b = (SessionManager) this.b.get();
        this.c.injectMembers(confirmarProgramaWomenFragment);
    }
}
