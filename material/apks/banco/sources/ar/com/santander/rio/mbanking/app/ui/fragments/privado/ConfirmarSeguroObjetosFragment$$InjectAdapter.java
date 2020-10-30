package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import ar.com.santander.rio.mbanking.app.base.BaseFragment;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;
import javax.inject.Provider;

public final class ConfirmarSeguroObjetosFragment$$InjectAdapter extends Binding<ConfirmarSeguroObjetosFragment> implements MembersInjector<ConfirmarSeguroObjetosFragment>, Provider<ConfirmarSeguroObjetosFragment> {
    private Binding<AnalyticsManager> a;
    private Binding<SessionManager> b;
    private Binding<IDataManager> c;
    private Binding<BaseFragment> d;

    public ConfirmarSeguroObjetosFragment$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.fragments.privado.ConfirmarSeguroObjetosFragment", "members/ar.com.santander.rio.mbanking.app.ui.fragments.privado.ConfirmarSeguroObjetosFragment", false, ConfirmarSeguroObjetosFragment.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager", ConfirmarSeguroObjetosFragment.class, getClass().getClassLoader());
        this.b = linker.requestBinding("ar.com.santander.rio.mbanking.managers.session.SessionManager", ConfirmarSeguroObjetosFragment.class, getClass().getClassLoader());
        this.c = linker.requestBinding("ar.com.santander.rio.mbanking.managers.data.IDataManager", ConfirmarSeguroObjetosFragment.class, getClass().getClassLoader());
        this.d = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.base.BaseFragment", ConfirmarSeguroObjetosFragment.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
        set2.add(this.c);
        set2.add(this.d);
    }

    public ConfirmarSeguroObjetosFragment get() {
        ConfirmarSeguroObjetosFragment confirmarSeguroObjetosFragment = new ConfirmarSeguroObjetosFragment();
        injectMembers(confirmarSeguroObjetosFragment);
        return confirmarSeguroObjetosFragment;
    }

    public void injectMembers(ConfirmarSeguroObjetosFragment confirmarSeguroObjetosFragment) {
        confirmarSeguroObjetosFragment.analyticsManager = (AnalyticsManager) this.a.get();
        confirmarSeguroObjetosFragment.sessionManager = (SessionManager) this.b.get();
        confirmarSeguroObjetosFragment.mDataManager = (IDataManager) this.c.get();
        this.d.injectMembers(confirmarSeguroObjetosFragment);
    }
}
