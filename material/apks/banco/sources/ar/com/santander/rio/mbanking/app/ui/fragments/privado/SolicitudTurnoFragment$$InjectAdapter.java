package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import ar.com.santander.rio.mbanking.app.base.BaseFragment;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;
import javax.inject.Provider;

public final class SolicitudTurnoFragment$$InjectAdapter extends Binding<SolicitudTurnoFragment> implements MembersInjector<SolicitudTurnoFragment>, Provider<SolicitudTurnoFragment> {
    private Binding<IDataManager> a;
    private Binding<SessionManager> b;
    private Binding<BaseFragment> c;

    public SolicitudTurnoFragment$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.fragments.privado.SolicitudTurnoFragment", "members/ar.com.santander.rio.mbanking.app.ui.fragments.privado.SolicitudTurnoFragment", false, SolicitudTurnoFragment.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.managers.data.IDataManager", SolicitudTurnoFragment.class, getClass().getClassLoader());
        this.b = linker.requestBinding("ar.com.santander.rio.mbanking.managers.session.SessionManager", SolicitudTurnoFragment.class, getClass().getClassLoader());
        this.c = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.base.BaseFragment", SolicitudTurnoFragment.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
        set2.add(this.c);
    }

    public SolicitudTurnoFragment get() {
        SolicitudTurnoFragment solicitudTurnoFragment = new SolicitudTurnoFragment();
        injectMembers(solicitudTurnoFragment);
        return solicitudTurnoFragment;
    }

    public void injectMembers(SolicitudTurnoFragment solicitudTurnoFragment) {
        solicitudTurnoFragment.mDataManager = (IDataManager) this.a.get();
        solicitudTurnoFragment.a = (SessionManager) this.b.get();
        this.c.injectMembers(solicitudTurnoFragment);
    }
}
