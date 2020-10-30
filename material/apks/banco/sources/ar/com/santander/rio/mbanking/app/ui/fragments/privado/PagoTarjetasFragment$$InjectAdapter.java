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

public final class PagoTarjetasFragment$$InjectAdapter extends Binding<PagoTarjetasFragment> implements MembersInjector<PagoTarjetasFragment>, Provider<PagoTarjetasFragment> {
    private Binding<SessionManager> a;
    private Binding<IDataManager> b;
    private Binding<AnalyticsManager> c;
    private Binding<BaseFragment> d;

    public PagoTarjetasFragment$$InjectAdapter() {
        super(PagoTarjetasFragment.TAG, "members/ar.com.santander.rio.mbanking.app.ui.fragments.privado.PagoTarjetasFragment", false, PagoTarjetasFragment.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.managers.session.SessionManager", PagoTarjetasFragment.class, getClass().getClassLoader());
        this.b = linker.requestBinding("ar.com.santander.rio.mbanking.managers.data.IDataManager", PagoTarjetasFragment.class, getClass().getClassLoader());
        this.c = linker.requestBinding("ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager", PagoTarjetasFragment.class, getClass().getClassLoader());
        this.d = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.base.BaseFragment", PagoTarjetasFragment.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
        set2.add(this.c);
        set2.add(this.d);
    }

    public PagoTarjetasFragment get() {
        PagoTarjetasFragment pagoTarjetasFragment = new PagoTarjetasFragment();
        injectMembers(pagoTarjetasFragment);
        return pagoTarjetasFragment;
    }

    public void injectMembers(PagoTarjetasFragment pagoTarjetasFragment) {
        pagoTarjetasFragment.a = (SessionManager) this.a.get();
        pagoTarjetasFragment.b = (IDataManager) this.b.get();
        pagoTarjetasFragment.c = (AnalyticsManager) this.c.get();
        this.d.injectMembers(pagoTarjetasFragment);
    }
}
