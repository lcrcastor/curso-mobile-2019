package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import ar.com.santander.rio.mbanking.app.base.BaseFragment;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;
import javax.inject.Provider;

public final class LimiteExtraccionModificacionFragment$$InjectAdapter extends Binding<LimiteExtraccionModificacionFragment> implements MembersInjector<LimiteExtraccionModificacionFragment>, Provider<LimiteExtraccionModificacionFragment> {
    private Binding<IDataManager> a;
    private Binding<BaseFragment> b;

    public LimiteExtraccionModificacionFragment$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.fragments.privado.LimiteExtraccionModificacionFragment", "members/ar.com.santander.rio.mbanking.app.ui.fragments.privado.LimiteExtraccionModificacionFragment", false, LimiteExtraccionModificacionFragment.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.managers.data.IDataManager", LimiteExtraccionModificacionFragment.class, getClass().getClassLoader());
        this.b = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.base.BaseFragment", LimiteExtraccionModificacionFragment.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
    }

    public LimiteExtraccionModificacionFragment get() {
        LimiteExtraccionModificacionFragment limiteExtraccionModificacionFragment = new LimiteExtraccionModificacionFragment();
        injectMembers(limiteExtraccionModificacionFragment);
        return limiteExtraccionModificacionFragment;
    }

    public void injectMembers(LimiteExtraccionModificacionFragment limiteExtraccionModificacionFragment) {
        limiteExtraccionModificacionFragment.dataManager = (IDataManager) this.a.get();
        this.b.injectMembers(limiteExtraccionModificacionFragment);
    }
}
