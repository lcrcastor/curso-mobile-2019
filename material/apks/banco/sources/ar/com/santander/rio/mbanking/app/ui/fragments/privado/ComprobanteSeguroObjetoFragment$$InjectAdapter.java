package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import ar.com.santander.rio.mbanking.app.base.ITRSABaseFragment;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;
import javax.inject.Provider;

public final class ComprobanteSeguroObjetoFragment$$InjectAdapter extends Binding<ComprobanteSeguroObjetoFragment> implements MembersInjector<ComprobanteSeguroObjetoFragment>, Provider<ComprobanteSeguroObjetoFragment> {
    private Binding<AnalyticsManager> a;
    private Binding<IDataManager> b;
    private Binding<ITRSABaseFragment> c;

    public ComprobanteSeguroObjetoFragment$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.fragments.privado.ComprobanteSeguroObjetoFragment", "members/ar.com.santander.rio.mbanking.app.ui.fragments.privado.ComprobanteSeguroObjetoFragment", false, ComprobanteSeguroObjetoFragment.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager", ComprobanteSeguroObjetoFragment.class, getClass().getClassLoader());
        this.b = linker.requestBinding("ar.com.santander.rio.mbanking.managers.data.IDataManager", ComprobanteSeguroObjetoFragment.class, getClass().getClassLoader());
        this.c = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.base.ITRSABaseFragment", ComprobanteSeguroObjetoFragment.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
        set2.add(this.c);
    }

    public ComprobanteSeguroObjetoFragment get() {
        ComprobanteSeguroObjetoFragment comprobanteSeguroObjetoFragment = new ComprobanteSeguroObjetoFragment();
        injectMembers(comprobanteSeguroObjetoFragment);
        return comprobanteSeguroObjetoFragment;
    }

    public void injectMembers(ComprobanteSeguroObjetoFragment comprobanteSeguroObjetoFragment) {
        comprobanteSeguroObjetoFragment.analyticsManager = (AnalyticsManager) this.a.get();
        comprobanteSeguroObjetoFragment.mDataManager = (IDataManager) this.b.get();
        this.c.injectMembers(comprobanteSeguroObjetoFragment);
    }
}
