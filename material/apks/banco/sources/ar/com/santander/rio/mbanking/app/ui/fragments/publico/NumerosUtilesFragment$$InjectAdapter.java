package ar.com.santander.rio.mbanking.app.ui.fragments.publico;

import ar.com.santander.rio.mbanking.app.base.BaseFragment;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import com.squareup.otto.Bus;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;
import javax.inject.Provider;

public final class NumerosUtilesFragment$$InjectAdapter extends Binding<NumerosUtilesFragment> implements MembersInjector<NumerosUtilesFragment>, Provider<NumerosUtilesFragment> {
    private Binding<IDataManager> a;
    private Binding<Bus> b;
    private Binding<AnalyticsManager> c;
    private Binding<BaseFragment> d;

    public NumerosUtilesFragment$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.fragments.publico.NumerosUtilesFragment", "members/ar.com.santander.rio.mbanking.app.ui.fragments.publico.NumerosUtilesFragment", false, NumerosUtilesFragment.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.managers.data.IDataManager", NumerosUtilesFragment.class, getClass().getClassLoader());
        this.b = linker.requestBinding("com.squareup.otto.Bus", NumerosUtilesFragment.class, getClass().getClassLoader());
        this.c = linker.requestBinding("ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager", NumerosUtilesFragment.class, getClass().getClassLoader());
        this.d = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.base.BaseFragment", NumerosUtilesFragment.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
        set2.add(this.c);
        set2.add(this.d);
    }

    public NumerosUtilesFragment get() {
        NumerosUtilesFragment numerosUtilesFragment = new NumerosUtilesFragment();
        injectMembers(numerosUtilesFragment);
        return numerosUtilesFragment;
    }

    public void injectMembers(NumerosUtilesFragment numerosUtilesFragment) {
        numerosUtilesFragment.a = (IDataManager) this.a.get();
        numerosUtilesFragment.b = (Bus) this.b.get();
        numerosUtilesFragment.c = (AnalyticsManager) this.c.get();
        this.d.injectMembers(numerosUtilesFragment);
    }
}
