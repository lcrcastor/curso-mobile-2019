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

public final class TenenciaCreditosFragment$$InjectAdapter extends Binding<TenenciaCreditosFragment> implements MembersInjector<TenenciaCreditosFragment>, Provider<TenenciaCreditosFragment> {
    private Binding<IDataManager> a;
    private Binding<SessionManager> b;
    private Binding<AnalyticsManager> c;
    private Binding<BaseFragment> d;

    public TenenciaCreditosFragment$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.fragments.privado.TenenciaCreditosFragment", "members/ar.com.santander.rio.mbanking.app.ui.fragments.privado.TenenciaCreditosFragment", false, TenenciaCreditosFragment.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.managers.data.IDataManager", TenenciaCreditosFragment.class, getClass().getClassLoader());
        this.b = linker.requestBinding("ar.com.santander.rio.mbanking.managers.session.SessionManager", TenenciaCreditosFragment.class, getClass().getClassLoader());
        this.c = linker.requestBinding("ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager", TenenciaCreditosFragment.class, getClass().getClassLoader());
        this.d = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.base.BaseFragment", TenenciaCreditosFragment.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
        set2.add(this.c);
        set2.add(this.d);
    }

    public TenenciaCreditosFragment get() {
        TenenciaCreditosFragment tenenciaCreditosFragment = new TenenciaCreditosFragment();
        injectMembers(tenenciaCreditosFragment);
        return tenenciaCreditosFragment;
    }

    public void injectMembers(TenenciaCreditosFragment tenenciaCreditosFragment) {
        tenenciaCreditosFragment.mDataManager = (IDataManager) this.a.get();
        tenenciaCreditosFragment.mSessionManager = (SessionManager) this.b.get();
        tenenciaCreditosFragment.mAnalyticsManager = (AnalyticsManager) this.c.get();
        this.d.injectMembers(tenenciaCreditosFragment);
    }
}
