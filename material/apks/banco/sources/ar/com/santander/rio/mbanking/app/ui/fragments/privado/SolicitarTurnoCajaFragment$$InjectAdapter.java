package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import ar.com.santander.rio.mbanking.app.base.BaseFragment;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;
import javax.inject.Provider;

public final class SolicitarTurnoCajaFragment$$InjectAdapter extends Binding<SolicitarTurnoCajaFragment> implements MembersInjector<SolicitarTurnoCajaFragment>, Provider<SolicitarTurnoCajaFragment> {
    private Binding<SessionManager> a;
    private Binding<BaseFragment> b;

    public SolicitarTurnoCajaFragment$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.fragments.privado.SolicitarTurnoCajaFragment", "members/ar.com.santander.rio.mbanking.app.ui.fragments.privado.SolicitarTurnoCajaFragment", false, SolicitarTurnoCajaFragment.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.managers.session.SessionManager", SolicitarTurnoCajaFragment.class, getClass().getClassLoader());
        this.b = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.base.BaseFragment", SolicitarTurnoCajaFragment.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
    }

    public SolicitarTurnoCajaFragment get() {
        SolicitarTurnoCajaFragment solicitarTurnoCajaFragment = new SolicitarTurnoCajaFragment();
        injectMembers(solicitarTurnoCajaFragment);
        return solicitarTurnoCajaFragment;
    }

    public void injectMembers(SolicitarTurnoCajaFragment solicitarTurnoCajaFragment) {
        solicitarTurnoCajaFragment.a = (SessionManager) this.a.get();
        this.b.injectMembers(solicitarTurnoCajaFragment);
    }
}
