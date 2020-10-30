package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import ar.com.santander.rio.mbanking.app.base.BaseFragment;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;
import javax.inject.Provider;

public final class RecalificacionesFragment$$InjectAdapter extends Binding<RecalificacionesFragment> implements MembersInjector<RecalificacionesFragment>, Provider<RecalificacionesFragment> {
    private Binding<IDataManager> a;
    private Binding<BaseFragment> b;

    public RecalificacionesFragment$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.fragments.privado.RecalificacionesFragment", "members/ar.com.santander.rio.mbanking.app.ui.fragments.privado.RecalificacionesFragment", false, RecalificacionesFragment.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.managers.data.IDataManager", RecalificacionesFragment.class, getClass().getClassLoader());
        this.b = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.base.BaseFragment", RecalificacionesFragment.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
    }

    public RecalificacionesFragment get() {
        RecalificacionesFragment recalificacionesFragment = new RecalificacionesFragment();
        injectMembers(recalificacionesFragment);
        return recalificacionesFragment;
    }

    public void injectMembers(RecalificacionesFragment recalificacionesFragment) {
        recalificacionesFragment.mDataManager = (IDataManager) this.a.get();
        this.b.injectMembers(recalificacionesFragment);
    }
}
