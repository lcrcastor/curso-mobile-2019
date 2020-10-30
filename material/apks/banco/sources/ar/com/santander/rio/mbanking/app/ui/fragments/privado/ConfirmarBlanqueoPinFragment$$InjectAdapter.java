package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import ar.com.santander.rio.mbanking.app.base.BaseFragment;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;
import javax.inject.Provider;

public final class ConfirmarBlanqueoPinFragment$$InjectAdapter extends Binding<ConfirmarBlanqueoPinFragment> implements MembersInjector<ConfirmarBlanqueoPinFragment>, Provider<ConfirmarBlanqueoPinFragment> {
    private Binding<IDataManager> a;
    private Binding<BaseFragment> b;

    public ConfirmarBlanqueoPinFragment$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.fragments.privado.ConfirmarBlanqueoPinFragment", "members/ar.com.santander.rio.mbanking.app.ui.fragments.privado.ConfirmarBlanqueoPinFragment", false, ConfirmarBlanqueoPinFragment.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.managers.data.IDataManager", ConfirmarBlanqueoPinFragment.class, getClass().getClassLoader());
        this.b = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.base.BaseFragment", ConfirmarBlanqueoPinFragment.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
    }

    public ConfirmarBlanqueoPinFragment get() {
        ConfirmarBlanqueoPinFragment confirmarBlanqueoPinFragment = new ConfirmarBlanqueoPinFragment();
        injectMembers(confirmarBlanqueoPinFragment);
        return confirmarBlanqueoPinFragment;
    }

    public void injectMembers(ConfirmarBlanqueoPinFragment confirmarBlanqueoPinFragment) {
        confirmarBlanqueoPinFragment.a = (IDataManager) this.a.get();
        this.b.injectMembers(confirmarBlanqueoPinFragment);
    }
}
