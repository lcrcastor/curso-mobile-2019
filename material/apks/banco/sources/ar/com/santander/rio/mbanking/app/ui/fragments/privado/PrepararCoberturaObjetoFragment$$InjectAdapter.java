package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import ar.com.santander.rio.mbanking.app.base.BaseFragment;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;
import javax.inject.Provider;

public final class PrepararCoberturaObjetoFragment$$InjectAdapter extends Binding<PrepararCoberturaObjetoFragment> implements MembersInjector<PrepararCoberturaObjetoFragment>, Provider<PrepararCoberturaObjetoFragment> {
    private Binding<IDataManager> a;
    private Binding<AnalyticsManager> b;
    private Binding<BaseFragment> c;

    public PrepararCoberturaObjetoFragment$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.fragments.privado.PrepararCoberturaObjetoFragment", "members/ar.com.santander.rio.mbanking.app.ui.fragments.privado.PrepararCoberturaObjetoFragment", false, PrepararCoberturaObjetoFragment.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.managers.data.IDataManager", PrepararCoberturaObjetoFragment.class, getClass().getClassLoader());
        this.b = linker.requestBinding("ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager", PrepararCoberturaObjetoFragment.class, getClass().getClassLoader());
        this.c = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.base.BaseFragment", PrepararCoberturaObjetoFragment.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
        set2.add(this.c);
    }

    public PrepararCoberturaObjetoFragment get() {
        PrepararCoberturaObjetoFragment prepararCoberturaObjetoFragment = new PrepararCoberturaObjetoFragment();
        injectMembers(prepararCoberturaObjetoFragment);
        return prepararCoberturaObjetoFragment;
    }

    public void injectMembers(PrepararCoberturaObjetoFragment prepararCoberturaObjetoFragment) {
        prepararCoberturaObjetoFragment.mDataManager = (IDataManager) this.a.get();
        prepararCoberturaObjetoFragment.analyticsManager = (AnalyticsManager) this.b.get();
        this.c.injectMembers(prepararCoberturaObjetoFragment);
    }
}
