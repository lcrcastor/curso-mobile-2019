package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import ar.com.santander.rio.mbanking.app.base.BaseFragment;
import ar.com.santander.rio.mbanking.app.module.accounts.analytics.AccountAnalytics;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;
import javax.inject.Provider;

public final class MovimientosCuentaFragment$$InjectAdapter extends Binding<MovimientosCuentaFragment> implements MembersInjector<MovimientosCuentaFragment>, Provider<MovimientosCuentaFragment> {
    private Binding<AccountAnalytics> a;
    private Binding<IDataManager> b;
    private Binding<SessionManager> c;
    private Binding<BaseFragment> d;

    public MovimientosCuentaFragment$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.fragments.privado.MovimientosCuentaFragment", "members/ar.com.santander.rio.mbanking.app.ui.fragments.privado.MovimientosCuentaFragment", false, MovimientosCuentaFragment.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.app.module.accounts.analytics.AccountAnalytics", MovimientosCuentaFragment.class, getClass().getClassLoader());
        this.b = linker.requestBinding("ar.com.santander.rio.mbanking.managers.data.IDataManager", MovimientosCuentaFragment.class, getClass().getClassLoader());
        this.c = linker.requestBinding("ar.com.santander.rio.mbanking.managers.session.SessionManager", MovimientosCuentaFragment.class, getClass().getClassLoader());
        this.d = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.base.BaseFragment", MovimientosCuentaFragment.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
        set2.add(this.c);
        set2.add(this.d);
    }

    public MovimientosCuentaFragment get() {
        MovimientosCuentaFragment movimientosCuentaFragment = new MovimientosCuentaFragment();
        injectMembers(movimientosCuentaFragment);
        return movimientosCuentaFragment;
    }

    public void injectMembers(MovimientosCuentaFragment movimientosCuentaFragment) {
        movimientosCuentaFragment.mAccountAnalytics = (AccountAnalytics) this.a.get();
        movimientosCuentaFragment.mDataManager = (IDataManager) this.b.get();
        movimientosCuentaFragment.mSessionManager = (SessionManager) this.c.get();
        this.d.injectMembers(movimientosCuentaFragment);
    }
}
