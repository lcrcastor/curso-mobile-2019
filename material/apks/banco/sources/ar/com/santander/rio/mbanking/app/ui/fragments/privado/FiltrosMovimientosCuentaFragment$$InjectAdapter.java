package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import ar.com.santander.rio.mbanking.app.base.BaseFragment;
import ar.com.santander.rio.mbanking.app.module.accounts.analytics.AccountAnalytics;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;
import javax.inject.Provider;

public final class FiltrosMovimientosCuentaFragment$$InjectAdapter extends Binding<FiltrosMovimientosCuentaFragment> implements MembersInjector<FiltrosMovimientosCuentaFragment>, Provider<FiltrosMovimientosCuentaFragment> {
    private Binding<AccountAnalytics> a;
    private Binding<SessionManager> b;
    private Binding<BaseFragment> c;

    public FiltrosMovimientosCuentaFragment$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.fragments.privado.FiltrosMovimientosCuentaFragment", "members/ar.com.santander.rio.mbanking.app.ui.fragments.privado.FiltrosMovimientosCuentaFragment", false, FiltrosMovimientosCuentaFragment.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.app.module.accounts.analytics.AccountAnalytics", FiltrosMovimientosCuentaFragment.class, getClass().getClassLoader());
        this.b = linker.requestBinding("ar.com.santander.rio.mbanking.managers.session.SessionManager", FiltrosMovimientosCuentaFragment.class, getClass().getClassLoader());
        this.c = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.base.BaseFragment", FiltrosMovimientosCuentaFragment.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
        set2.add(this.c);
    }

    public FiltrosMovimientosCuentaFragment get() {
        FiltrosMovimientosCuentaFragment filtrosMovimientosCuentaFragment = new FiltrosMovimientosCuentaFragment();
        injectMembers(filtrosMovimientosCuentaFragment);
        return filtrosMovimientosCuentaFragment;
    }

    public void injectMembers(FiltrosMovimientosCuentaFragment filtrosMovimientosCuentaFragment) {
        filtrosMovimientosCuentaFragment.mAccountAnalytics = (AccountAnalytics) this.a.get();
        filtrosMovimientosCuentaFragment.mSessionManager = (SessionManager) this.b.get();
        this.c.injectMembers(filtrosMovimientosCuentaFragment);
    }
}
