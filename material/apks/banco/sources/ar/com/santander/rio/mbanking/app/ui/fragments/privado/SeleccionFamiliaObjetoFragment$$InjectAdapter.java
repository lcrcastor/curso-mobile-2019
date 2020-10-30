package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import ar.com.santander.rio.mbanking.app.base.BaseFragment;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;
import javax.inject.Provider;

public final class SeleccionFamiliaObjetoFragment$$InjectAdapter extends Binding<SeleccionFamiliaObjetoFragment> implements MembersInjector<SeleccionFamiliaObjetoFragment>, Provider<SeleccionFamiliaObjetoFragment> {
    private Binding<IDataManager> a;
    private Binding<AnalyticsManager> b;
    private Binding<BaseFragment> c;

    public SeleccionFamiliaObjetoFragment$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.fragments.privado.SeleccionFamiliaObjetoFragment", "members/ar.com.santander.rio.mbanking.app.ui.fragments.privado.SeleccionFamiliaObjetoFragment", false, SeleccionFamiliaObjetoFragment.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.managers.data.IDataManager", SeleccionFamiliaObjetoFragment.class, getClass().getClassLoader());
        this.b = linker.requestBinding("ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager", SeleccionFamiliaObjetoFragment.class, getClass().getClassLoader());
        this.c = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.base.BaseFragment", SeleccionFamiliaObjetoFragment.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
        set2.add(this.c);
    }

    public SeleccionFamiliaObjetoFragment get() {
        SeleccionFamiliaObjetoFragment seleccionFamiliaObjetoFragment = new SeleccionFamiliaObjetoFragment();
        injectMembers(seleccionFamiliaObjetoFragment);
        return seleccionFamiliaObjetoFragment;
    }

    public void injectMembers(SeleccionFamiliaObjetoFragment seleccionFamiliaObjetoFragment) {
        seleccionFamiliaObjetoFragment.mDataManager = (IDataManager) this.a.get();
        seleccionFamiliaObjetoFragment.analyticsManager = (AnalyticsManager) this.b.get();
        this.c.injectMembers(seleccionFamiliaObjetoFragment);
    }
}
