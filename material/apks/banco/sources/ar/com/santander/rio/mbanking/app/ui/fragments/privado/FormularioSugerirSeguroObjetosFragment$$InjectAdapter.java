package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import ar.com.santander.rio.mbanking.app.base.BaseFragment;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;
import javax.inject.Provider;

public final class FormularioSugerirSeguroObjetosFragment$$InjectAdapter extends Binding<FormularioSugerirSeguroObjetosFragment> implements MembersInjector<FormularioSugerirSeguroObjetosFragment>, Provider<FormularioSugerirSeguroObjetosFragment> {
    private Binding<IDataManager> a;
    private Binding<AnalyticsManager> b;
    private Binding<BaseFragment> c;

    public FormularioSugerirSeguroObjetosFragment$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.fragments.privado.FormularioSugerirSeguroObjetosFragment", "members/ar.com.santander.rio.mbanking.app.ui.fragments.privado.FormularioSugerirSeguroObjetosFragment", false, FormularioSugerirSeguroObjetosFragment.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.managers.data.IDataManager", FormularioSugerirSeguroObjetosFragment.class, getClass().getClassLoader());
        this.b = linker.requestBinding("ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager", FormularioSugerirSeguroObjetosFragment.class, getClass().getClassLoader());
        this.c = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.base.BaseFragment", FormularioSugerirSeguroObjetosFragment.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
        set2.add(this.c);
    }

    public FormularioSugerirSeguroObjetosFragment get() {
        FormularioSugerirSeguroObjetosFragment formularioSugerirSeguroObjetosFragment = new FormularioSugerirSeguroObjetosFragment();
        injectMembers(formularioSugerirSeguroObjetosFragment);
        return formularioSugerirSeguroObjetosFragment;
    }

    public void injectMembers(FormularioSugerirSeguroObjetosFragment formularioSugerirSeguroObjetosFragment) {
        formularioSugerirSeguroObjetosFragment.a = (IDataManager) this.a.get();
        formularioSugerirSeguroObjetosFragment.analyticsManager = (AnalyticsManager) this.b.get();
        this.c.injectMembers(formularioSugerirSeguroObjetosFragment);
    }
}
