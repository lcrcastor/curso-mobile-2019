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

public final class TarjetasFragment$$InjectAdapter extends Binding<TarjetasFragment> implements MembersInjector<TarjetasFragment>, Provider<TarjetasFragment> {
    private Binding<IDataManager> a;
    private Binding<SessionManager> b;
    private Binding<AnalyticsManager> c;
    private Binding<BaseFragment> d;

    public TarjetasFragment$$InjectAdapter() {
        super(TarjetasFragment.TAG, "members/ar.com.santander.rio.mbanking.app.ui.fragments.privado.TarjetasFragment", false, TarjetasFragment.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.managers.data.IDataManager", TarjetasFragment.class, getClass().getClassLoader());
        this.b = linker.requestBinding("ar.com.santander.rio.mbanking.managers.session.SessionManager", TarjetasFragment.class, getClass().getClassLoader());
        this.c = linker.requestBinding("ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager", TarjetasFragment.class, getClass().getClassLoader());
        this.d = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.base.BaseFragment", TarjetasFragment.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
        set2.add(this.c);
        set2.add(this.d);
    }

    public TarjetasFragment get() {
        TarjetasFragment tarjetasFragment = new TarjetasFragment();
        injectMembers(tarjetasFragment);
        return tarjetasFragment;
    }

    public void injectMembers(TarjetasFragment tarjetasFragment) {
        tarjetasFragment.e = (IDataManager) this.a.get();
        tarjetasFragment.f = (SessionManager) this.b.get();
        tarjetasFragment.g = (AnalyticsManager) this.c.get();
        this.d.injectMembers(tarjetasFragment);
    }
}
