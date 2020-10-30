package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import ar.com.santander.rio.mbanking.app.base.BaseFragment;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;
import javax.inject.Provider;

public final class CotizarCoberturaObjetoFragment$$InjectAdapter extends Binding<CotizarCoberturaObjetoFragment> implements MembersInjector<CotizarCoberturaObjetoFragment>, Provider<CotizarCoberturaObjetoFragment> {
    private Binding<IDataManager> a;
    private Binding<AnalyticsManager> b;
    private Binding<BaseFragment> c;

    public CotizarCoberturaObjetoFragment$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.fragments.privado.CotizarCoberturaObjetoFragment", "members/ar.com.santander.rio.mbanking.app.ui.fragments.privado.CotizarCoberturaObjetoFragment", false, CotizarCoberturaObjetoFragment.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.managers.data.IDataManager", CotizarCoberturaObjetoFragment.class, getClass().getClassLoader());
        this.b = linker.requestBinding("ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager", CotizarCoberturaObjetoFragment.class, getClass().getClassLoader());
        this.c = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.base.BaseFragment", CotizarCoberturaObjetoFragment.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
        set2.add(this.c);
    }

    public CotizarCoberturaObjetoFragment get() {
        CotizarCoberturaObjetoFragment cotizarCoberturaObjetoFragment = new CotizarCoberturaObjetoFragment();
        injectMembers(cotizarCoberturaObjetoFragment);
        return cotizarCoberturaObjetoFragment;
    }

    public void injectMembers(CotizarCoberturaObjetoFragment cotizarCoberturaObjetoFragment) {
        cotizarCoberturaObjetoFragment.mDataManager = (IDataManager) this.a.get();
        cotizarCoberturaObjetoFragment.analyticsManager = (AnalyticsManager) this.b.get();
        this.c.injectMembers(cotizarCoberturaObjetoFragment);
    }
}
