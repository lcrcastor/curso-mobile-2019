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

public final class CompletarSeguroObjetosFragment$$InjectAdapter extends Binding<CompletarSeguroObjetosFragment> implements MembersInjector<CompletarSeguroObjetosFragment>, Provider<CompletarSeguroObjetosFragment> {
    private Binding<AnalyticsManager> a;
    private Binding<SessionManager> b;
    private Binding<IDataManager> c;
    private Binding<BaseFragment> d;

    public CompletarSeguroObjetosFragment$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.fragments.privado.CompletarSeguroObjetosFragment", "members/ar.com.santander.rio.mbanking.app.ui.fragments.privado.CompletarSeguroObjetosFragment", false, CompletarSeguroObjetosFragment.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager", CompletarSeguroObjetosFragment.class, getClass().getClassLoader());
        this.b = linker.requestBinding("ar.com.santander.rio.mbanking.managers.session.SessionManager", CompletarSeguroObjetosFragment.class, getClass().getClassLoader());
        this.c = linker.requestBinding("ar.com.santander.rio.mbanking.managers.data.IDataManager", CompletarSeguroObjetosFragment.class, getClass().getClassLoader());
        this.d = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.base.BaseFragment", CompletarSeguroObjetosFragment.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
        set2.add(this.c);
        set2.add(this.d);
    }

    public CompletarSeguroObjetosFragment get() {
        CompletarSeguroObjetosFragment completarSeguroObjetosFragment = new CompletarSeguroObjetosFragment();
        injectMembers(completarSeguroObjetosFragment);
        return completarSeguroObjetosFragment;
    }

    public void injectMembers(CompletarSeguroObjetosFragment completarSeguroObjetosFragment) {
        completarSeguroObjetosFragment.analyticsManager = (AnalyticsManager) this.a.get();
        completarSeguroObjetosFragment.sessionManager = (SessionManager) this.b.get();
        completarSeguroObjetosFragment.mDataManager = (IDataManager) this.c.get();
        this.d.injectMembers(completarSeguroObjetosFragment);
    }
}
