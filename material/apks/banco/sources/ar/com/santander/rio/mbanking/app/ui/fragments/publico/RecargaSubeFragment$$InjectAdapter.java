package ar.com.santander.rio.mbanking.app.ui.fragments.publico;

import ar.com.santander.rio.mbanking.app.base.BaseFragment;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.security.SoftTokenManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;
import javax.inject.Provider;

public final class RecargaSubeFragment$$InjectAdapter extends Binding<RecargaSubeFragment> implements MembersInjector<RecargaSubeFragment>, Provider<RecargaSubeFragment> {
    private Binding<SessionManager> a;
    private Binding<SoftTokenManager> b;
    private Binding<AnalyticsManager> c;
    private Binding<BaseFragment> d;

    public RecargaSubeFragment$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.fragments.publico.RecargaSubeFragment", "members/ar.com.santander.rio.mbanking.app.ui.fragments.publico.RecargaSubeFragment", false, RecargaSubeFragment.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.managers.session.SessionManager", RecargaSubeFragment.class, getClass().getClassLoader());
        this.b = linker.requestBinding("ar.com.santander.rio.mbanking.managers.security.SoftTokenManager", RecargaSubeFragment.class, getClass().getClassLoader());
        this.c = linker.requestBinding("ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager", RecargaSubeFragment.class, getClass().getClassLoader());
        this.d = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.base.BaseFragment", RecargaSubeFragment.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
        set2.add(this.c);
        set2.add(this.d);
    }

    public RecargaSubeFragment get() {
        RecargaSubeFragment recargaSubeFragment = new RecargaSubeFragment();
        injectMembers(recargaSubeFragment);
        return recargaSubeFragment;
    }

    public void injectMembers(RecargaSubeFragment recargaSubeFragment) {
        recargaSubeFragment.sessionManager = (SessionManager) this.a.get();
        recargaSubeFragment.a = (SoftTokenManager) this.b.get();
        recargaSubeFragment.b = (AnalyticsManager) this.c.get();
        this.d.injectMembers(recargaSubeFragment);
    }
}
