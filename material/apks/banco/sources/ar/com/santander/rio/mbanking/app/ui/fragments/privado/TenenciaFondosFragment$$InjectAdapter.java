package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import ar.com.santander.rio.mbanking.app.base.BaseMvpFragment;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.managers.security.SoftTokenManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;
import javax.inject.Provider;

public final class TenenciaFondosFragment$$InjectAdapter extends Binding<TenenciaFondosFragment> implements MembersInjector<TenenciaFondosFragment>, Provider<TenenciaFondosFragment> {
    private Binding<IDataManager> a;
    private Binding<SoftTokenManager> b;
    private Binding<SessionManager> c;
    private Binding<AnalyticsManager> d;
    private Binding<BaseMvpFragment> e;

    public TenenciaFondosFragment$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.fragments.privado.TenenciaFondosFragment", "members/ar.com.santander.rio.mbanking.app.ui.fragments.privado.TenenciaFondosFragment", false, TenenciaFondosFragment.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.managers.data.IDataManager", TenenciaFondosFragment.class, getClass().getClassLoader());
        this.b = linker.requestBinding("ar.com.santander.rio.mbanking.managers.security.SoftTokenManager", TenenciaFondosFragment.class, getClass().getClassLoader());
        this.c = linker.requestBinding("ar.com.santander.rio.mbanking.managers.session.SessionManager", TenenciaFondosFragment.class, getClass().getClassLoader());
        this.d = linker.requestBinding("ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager", TenenciaFondosFragment.class, getClass().getClassLoader());
        this.e = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.base.BaseMvpFragment", TenenciaFondosFragment.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
        set2.add(this.c);
        set2.add(this.d);
        set2.add(this.e);
    }

    public TenenciaFondosFragment get() {
        TenenciaFondosFragment tenenciaFondosFragment = new TenenciaFondosFragment();
        injectMembers(tenenciaFondosFragment);
        return tenenciaFondosFragment;
    }

    public void injectMembers(TenenciaFondosFragment tenenciaFondosFragment) {
        tenenciaFondosFragment.a = (IDataManager) this.a.get();
        tenenciaFondosFragment.b = (SoftTokenManager) this.b.get();
        tenenciaFondosFragment.c = (SessionManager) this.c.get();
        tenenciaFondosFragment.d = (AnalyticsManager) this.d.get();
        this.e.injectMembers(tenenciaFondosFragment);
    }
}
