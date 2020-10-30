package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import ar.com.santander.rio.mbanking.app.base.BaseFragment;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;
import javax.inject.Provider;

public final class LimiteExtraccionConfirmacionFragment$$InjectAdapter extends Binding<LimiteExtraccionConfirmacionFragment> implements MembersInjector<LimiteExtraccionConfirmacionFragment>, Provider<LimiteExtraccionConfirmacionFragment> {
    private Binding<IDataManager> a;
    private Binding<BaseFragment> b;

    public LimiteExtraccionConfirmacionFragment$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.fragments.privado.LimiteExtraccionConfirmacionFragment", "members/ar.com.santander.rio.mbanking.app.ui.fragments.privado.LimiteExtraccionConfirmacionFragment", false, LimiteExtraccionConfirmacionFragment.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.managers.data.IDataManager", LimiteExtraccionConfirmacionFragment.class, getClass().getClassLoader());
        this.b = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.base.BaseFragment", LimiteExtraccionConfirmacionFragment.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
    }

    public LimiteExtraccionConfirmacionFragment get() {
        LimiteExtraccionConfirmacionFragment limiteExtraccionConfirmacionFragment = new LimiteExtraccionConfirmacionFragment();
        injectMembers(limiteExtraccionConfirmacionFragment);
        return limiteExtraccionConfirmacionFragment;
    }

    public void injectMembers(LimiteExtraccionConfirmacionFragment limiteExtraccionConfirmacionFragment) {
        limiteExtraccionConfirmacionFragment.dataManager = (IDataManager) this.a.get();
        this.b.injectMembers(limiteExtraccionConfirmacionFragment);
    }
}
