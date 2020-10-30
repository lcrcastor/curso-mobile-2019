package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import ar.com.santander.rio.mbanking.app.base.ITRSABaseFragment;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;
import javax.inject.Provider;

public final class ComprobanteSeguroAccidenteFragment$$InjectAdapter extends Binding<ComprobanteSeguroAccidenteFragment> implements MembersInjector<ComprobanteSeguroAccidenteFragment>, Provider<ComprobanteSeguroAccidenteFragment> {
    private Binding<IDataManager> a;
    private Binding<ITRSABaseFragment> b;

    public ComprobanteSeguroAccidenteFragment$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.fragments.privado.ComprobanteSeguroAccidenteFragment", "members/ar.com.santander.rio.mbanking.app.ui.fragments.privado.ComprobanteSeguroAccidenteFragment", false, ComprobanteSeguroAccidenteFragment.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.managers.data.IDataManager", ComprobanteSeguroAccidenteFragment.class, getClass().getClassLoader());
        this.b = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.base.ITRSABaseFragment", ComprobanteSeguroAccidenteFragment.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
    }

    public ComprobanteSeguroAccidenteFragment get() {
        ComprobanteSeguroAccidenteFragment comprobanteSeguroAccidenteFragment = new ComprobanteSeguroAccidenteFragment();
        injectMembers(comprobanteSeguroAccidenteFragment);
        return comprobanteSeguroAccidenteFragment;
    }

    public void injectMembers(ComprobanteSeguroAccidenteFragment comprobanteSeguroAccidenteFragment) {
        comprobanteSeguroAccidenteFragment.a = (IDataManager) this.a.get();
        this.b.injectMembers(comprobanteSeguroAccidenteFragment);
    }
}
