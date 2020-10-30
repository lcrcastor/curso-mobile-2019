package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import ar.com.santander.rio.mbanking.app.base.BaseFragment;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;
import javax.inject.Provider;

public final class RecalificacionConfirmarFragment$$InjectAdapter extends Binding<RecalificacionConfirmarFragment> implements MembersInjector<RecalificacionConfirmarFragment>, Provider<RecalificacionConfirmarFragment> {
    private Binding<IDataManager> a;
    private Binding<BaseFragment> b;

    public RecalificacionConfirmarFragment$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.fragments.privado.RecalificacionConfirmarFragment", "members/ar.com.santander.rio.mbanking.app.ui.fragments.privado.RecalificacionConfirmarFragment", false, RecalificacionConfirmarFragment.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.managers.data.IDataManager", RecalificacionConfirmarFragment.class, getClass().getClassLoader());
        this.b = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.base.BaseFragment", RecalificacionConfirmarFragment.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
    }

    public RecalificacionConfirmarFragment get() {
        RecalificacionConfirmarFragment recalificacionConfirmarFragment = new RecalificacionConfirmarFragment();
        injectMembers(recalificacionConfirmarFragment);
        return recalificacionConfirmarFragment;
    }

    public void injectMembers(RecalificacionConfirmarFragment recalificacionConfirmarFragment) {
        recalificacionConfirmarFragment.mDataManager = (IDataManager) this.a.get();
        this.b.injectMembers(recalificacionConfirmarFragment);
    }
}
