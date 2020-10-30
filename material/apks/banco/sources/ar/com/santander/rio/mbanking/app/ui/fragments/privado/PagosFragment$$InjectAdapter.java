package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import ar.com.santander.rio.mbanking.app.base.BaseFragment;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.managers.preferences.SettingsManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;
import javax.inject.Provider;

public final class PagosFragment$$InjectAdapter extends Binding<PagosFragment> implements MembersInjector<PagosFragment>, Provider<PagosFragment> {
    private Binding<IDataManager> a;
    private Binding<SessionManager> b;
    private Binding<AnalyticsManager> c;
    private Binding<SettingsManager> d;
    private Binding<BaseFragment> e;

    public PagosFragment$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.fragments.privado.PagosFragment", "members/ar.com.santander.rio.mbanking.app.ui.fragments.privado.PagosFragment", false, PagosFragment.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.managers.data.IDataManager", PagosFragment.class, getClass().getClassLoader());
        this.b = linker.requestBinding("ar.com.santander.rio.mbanking.managers.session.SessionManager", PagosFragment.class, getClass().getClassLoader());
        this.c = linker.requestBinding("ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager", PagosFragment.class, getClass().getClassLoader());
        this.d = linker.requestBinding("ar.com.santander.rio.mbanking.managers.preferences.SettingsManager", PagosFragment.class, getClass().getClassLoader());
        this.e = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.base.BaseFragment", PagosFragment.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
        set2.add(this.c);
        set2.add(this.d);
        set2.add(this.e);
    }

    public PagosFragment get() {
        PagosFragment pagosFragment = new PagosFragment();
        injectMembers(pagosFragment);
        return pagosFragment;
    }

    public void injectMembers(PagosFragment pagosFragment) {
        pagosFragment.mDataManager = (IDataManager) this.a.get();
        pagosFragment.mSessionManager = (SessionManager) this.b.get();
        pagosFragment.mAnalitycsManager = (AnalyticsManager) this.c.get();
        pagosFragment.a = (SettingsManager) this.d.get();
        this.e.injectMembers(pagosFragment);
    }
}
