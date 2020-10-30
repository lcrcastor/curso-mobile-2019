package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import ar.com.santander.rio.mbanking.app.base.BaseMvpFragment;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.security.SoftTokenManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;
import javax.inject.Provider;

public final class DetalleDebinFragment$$InjectAdapter extends Binding<DetalleDebinFragment> implements MembersInjector<DetalleDebinFragment>, Provider<DetalleDebinFragment> {
    private Binding<SoftTokenManager> a;
    private Binding<SessionManager> b;
    private Binding<AnalyticsManager> c;
    private Binding<BaseMvpFragment> d;

    public DetalleDebinFragment$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.fragments.privado.DetalleDebinFragment", "members/ar.com.santander.rio.mbanking.app.ui.fragments.privado.DetalleDebinFragment", false, DetalleDebinFragment.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.managers.security.SoftTokenManager", DetalleDebinFragment.class, getClass().getClassLoader());
        this.b = linker.requestBinding("ar.com.santander.rio.mbanking.managers.session.SessionManager", DetalleDebinFragment.class, getClass().getClassLoader());
        this.c = linker.requestBinding("ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager", DetalleDebinFragment.class, getClass().getClassLoader());
        this.d = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.base.BaseMvpFragment", DetalleDebinFragment.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
        set2.add(this.c);
        set2.add(this.d);
    }

    public DetalleDebinFragment get() {
        DetalleDebinFragment detalleDebinFragment = new DetalleDebinFragment();
        injectMembers(detalleDebinFragment);
        return detalleDebinFragment;
    }

    public void injectMembers(DetalleDebinFragment detalleDebinFragment) {
        detalleDebinFragment.a = (SoftTokenManager) this.a.get();
        detalleDebinFragment.b = (SessionManager) this.b.get();
        detalleDebinFragment.c = (AnalyticsManager) this.c.get();
        this.d.injectMembers(detalleDebinFragment);
    }
}
