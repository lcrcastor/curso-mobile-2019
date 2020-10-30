package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import ar.com.santander.rio.mbanking.app.base.BaseFragment;
import ar.com.santander.rio.mbanking.app.module.accounts.analytics.AccountAnalytics;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.managers.preferences.SettingsManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;

public final class CuentasFragment$$InjectAdapter extends Binding<CuentasFragment> implements MembersInjector<CuentasFragment> {
    private Binding<AccountAnalytics> a;
    private Binding<AnalyticsManager> b;
    private Binding<SettingsManager> c;
    private Binding<IDataManager> d;
    private Binding<SessionManager> e;
    private Binding<BaseFragment> f;

    public CuentasFragment$$InjectAdapter() {
        super(null, "members/ar.com.santander.rio.mbanking.app.ui.fragments.privado.CuentasFragment", false, CuentasFragment.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.app.module.accounts.analytics.AccountAnalytics", CuentasFragment.class, getClass().getClassLoader());
        this.b = linker.requestBinding("ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager", CuentasFragment.class, getClass().getClassLoader());
        this.c = linker.requestBinding("ar.com.santander.rio.mbanking.managers.preferences.SettingsManager", CuentasFragment.class, getClass().getClassLoader());
        this.d = linker.requestBinding("ar.com.santander.rio.mbanking.managers.data.IDataManager", CuentasFragment.class, getClass().getClassLoader());
        this.e = linker.requestBinding("ar.com.santander.rio.mbanking.managers.session.SessionManager", CuentasFragment.class, getClass().getClassLoader());
        this.f = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.base.BaseFragment", CuentasFragment.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
        set2.add(this.c);
        set2.add(this.d);
        set2.add(this.e);
        set2.add(this.f);
    }

    public void injectMembers(CuentasFragment cuentasFragment) {
        cuentasFragment.mAccountAnalytics = (AccountAnalytics) this.a.get();
        cuentasFragment.analyticsManager = (AnalyticsManager) this.b.get();
        cuentasFragment.a = (SettingsManager) this.c.get();
        cuentasFragment.b = (IDataManager) this.d.get();
        cuentasFragment.c = (SessionManager) this.e.get();
        this.f.injectMembers(cuentasFragment);
    }
}
