package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import ar.com.santander.rio.mbanking.app.base.BaseFragment;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.managers.security.SoftTokenManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;
import javax.inject.Provider;

public final class TransferenciasFragment$$InjectAdapter extends Binding<TransferenciasFragment> implements MembersInjector<TransferenciasFragment>, Provider<TransferenciasFragment> {
    private Binding<SessionManager> a;
    private Binding<IDataManager> b;
    private Binding<AnalyticsManager> c;
    private Binding<SoftTokenManager> d;
    private Binding<BaseFragment> e;

    public TransferenciasFragment$$InjectAdapter() {
        super(TransferenciasFragment.LOG_TAG, "members/ar.com.santander.rio.mbanking.app.ui.fragments.privado.TransferenciasFragment", false, TransferenciasFragment.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.managers.session.SessionManager", TransferenciasFragment.class, getClass().getClassLoader());
        this.b = linker.requestBinding("ar.com.santander.rio.mbanking.managers.data.IDataManager", TransferenciasFragment.class, getClass().getClassLoader());
        this.c = linker.requestBinding("ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager", TransferenciasFragment.class, getClass().getClassLoader());
        this.d = linker.requestBinding("ar.com.santander.rio.mbanking.managers.security.SoftTokenManager", TransferenciasFragment.class, getClass().getClassLoader());
        this.e = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.base.BaseFragment", TransferenciasFragment.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
        set2.add(this.c);
        set2.add(this.d);
        set2.add(this.e);
    }

    public TransferenciasFragment get() {
        TransferenciasFragment transferenciasFragment = new TransferenciasFragment();
        injectMembers(transferenciasFragment);
        return transferenciasFragment;
    }

    public void injectMembers(TransferenciasFragment transferenciasFragment) {
        transferenciasFragment.b = (SessionManager) this.a.get();
        transferenciasFragment.c = (IDataManager) this.b.get();
        transferenciasFragment.d = (AnalyticsManager) this.c.get();
        transferenciasFragment.e = (SoftTokenManager) this.d.get();
        this.e.injectMembers(transferenciasFragment);
    }
}
