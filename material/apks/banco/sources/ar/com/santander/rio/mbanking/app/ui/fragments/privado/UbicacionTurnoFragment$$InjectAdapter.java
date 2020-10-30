package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import ar.com.santander.rio.mbanking.app.base.BaseFragment;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;
import javax.inject.Provider;

public final class UbicacionTurnoFragment$$InjectAdapter extends Binding<UbicacionTurnoFragment> implements MembersInjector<UbicacionTurnoFragment>, Provider<UbicacionTurnoFragment> {
    private Binding<IDataManager> a;
    private Binding<BaseFragment> b;

    public UbicacionTurnoFragment$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.fragments.privado.UbicacionTurnoFragment", "members/ar.com.santander.rio.mbanking.app.ui.fragments.privado.UbicacionTurnoFragment", false, UbicacionTurnoFragment.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.managers.data.IDataManager", UbicacionTurnoFragment.class, getClass().getClassLoader());
        this.b = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.base.BaseFragment", UbicacionTurnoFragment.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
    }

    public UbicacionTurnoFragment get() {
        UbicacionTurnoFragment ubicacionTurnoFragment = new UbicacionTurnoFragment();
        injectMembers(ubicacionTurnoFragment);
        return ubicacionTurnoFragment;
    }

    public void injectMembers(UbicacionTurnoFragment ubicacionTurnoFragment) {
        ubicacionTurnoFragment.mDataManager = (IDataManager) this.a.get();
        this.b.injectMembers(ubicacionTurnoFragment);
    }
}
