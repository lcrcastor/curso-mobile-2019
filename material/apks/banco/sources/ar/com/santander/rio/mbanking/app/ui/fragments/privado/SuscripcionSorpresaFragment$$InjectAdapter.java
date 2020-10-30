package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import ar.com.santander.rio.mbanking.app.base.BaseFragment;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;
import javax.inject.Provider;

public final class SuscripcionSorpresaFragment$$InjectAdapter extends Binding<SuscripcionSorpresaFragment> implements MembersInjector<SuscripcionSorpresaFragment>, Provider<SuscripcionSorpresaFragment> {
    private Binding<IDataManager> a;
    private Binding<SessionManager> b;
    private Binding<AnalyticsManager> c;
    private Binding<BaseFragment> d;

    public SuscripcionSorpresaFragment$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.fragments.privado.SuscripcionSorpresaFragment", "members/ar.com.santander.rio.mbanking.app.ui.fragments.privado.SuscripcionSorpresaFragment", false, SuscripcionSorpresaFragment.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.managers.data.IDataManager", SuscripcionSorpresaFragment.class, getClass().getClassLoader());
        this.b = linker.requestBinding("ar.com.santander.rio.mbanking.managers.session.SessionManager", SuscripcionSorpresaFragment.class, getClass().getClassLoader());
        this.c = linker.requestBinding("ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager", SuscripcionSorpresaFragment.class, getClass().getClassLoader());
        this.d = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.base.BaseFragment", SuscripcionSorpresaFragment.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
        set2.add(this.c);
        set2.add(this.d);
    }

    public SuscripcionSorpresaFragment get() {
        SuscripcionSorpresaFragment suscripcionSorpresaFragment = new SuscripcionSorpresaFragment();
        injectMembers(suscripcionSorpresaFragment);
        return suscripcionSorpresaFragment;
    }

    public void injectMembers(SuscripcionSorpresaFragment suscripcionSorpresaFragment) {
        suscripcionSorpresaFragment.a = (IDataManager) this.a.get();
        suscripcionSorpresaFragment.b = (SessionManager) this.b.get();
        suscripcionSorpresaFragment.c = (AnalyticsManager) this.c.get();
        this.d.injectMembers(suscripcionSorpresaFragment);
    }
}
