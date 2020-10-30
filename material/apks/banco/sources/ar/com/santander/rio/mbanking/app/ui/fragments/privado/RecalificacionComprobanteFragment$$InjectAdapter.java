package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import ar.com.santander.rio.mbanking.app.base.ITRSABaseFragment;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;
import javax.inject.Provider;

public final class RecalificacionComprobanteFragment$$InjectAdapter extends Binding<RecalificacionComprobanteFragment> implements MembersInjector<RecalificacionComprobanteFragment>, Provider<RecalificacionComprobanteFragment> {
    private Binding<IDataManager> a;
    private Binding<ITRSABaseFragment> b;

    public RecalificacionComprobanteFragment$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.fragments.privado.RecalificacionComprobanteFragment", "members/ar.com.santander.rio.mbanking.app.ui.fragments.privado.RecalificacionComprobanteFragment", false, RecalificacionComprobanteFragment.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.managers.data.IDataManager", RecalificacionComprobanteFragment.class, getClass().getClassLoader());
        this.b = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.base.ITRSABaseFragment", RecalificacionComprobanteFragment.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
    }

    public RecalificacionComprobanteFragment get() {
        RecalificacionComprobanteFragment recalificacionComprobanteFragment = new RecalificacionComprobanteFragment();
        injectMembers(recalificacionComprobanteFragment);
        return recalificacionComprobanteFragment;
    }

    public void injectMembers(RecalificacionComprobanteFragment recalificacionComprobanteFragment) {
        recalificacionComprobanteFragment.mDataManager = (IDataManager) this.a.get();
        this.b.injectMembers(recalificacionComprobanteFragment);
    }
}
