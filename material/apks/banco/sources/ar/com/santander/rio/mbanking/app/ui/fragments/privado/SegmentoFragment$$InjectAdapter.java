package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import ar.com.santander.rio.mbanking.app.base.BaseFragment;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;
import javax.inject.Provider;

public final class SegmentoFragment$$InjectAdapter extends Binding<SegmentoFragment> implements MembersInjector<SegmentoFragment>, Provider<SegmentoFragment> {
    private Binding<SessionManager> a;
    private Binding<BaseFragment> b;

    public SegmentoFragment$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.fragments.privado.SegmentoFragment", "members/ar.com.santander.rio.mbanking.app.ui.fragments.privado.SegmentoFragment", false, SegmentoFragment.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.managers.session.SessionManager", SegmentoFragment.class, getClass().getClassLoader());
        this.b = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.base.BaseFragment", SegmentoFragment.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
    }

    public SegmentoFragment get() {
        SegmentoFragment segmentoFragment = new SegmentoFragment();
        injectMembers(segmentoFragment);
        return segmentoFragment;
    }

    public void injectMembers(SegmentoFragment segmentoFragment) {
        segmentoFragment.a = (SessionManager) this.a.get();
        this.b.injectMembers(segmentoFragment);
    }
}
