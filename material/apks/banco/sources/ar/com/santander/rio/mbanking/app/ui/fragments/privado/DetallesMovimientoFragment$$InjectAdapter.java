package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import ar.com.santander.rio.mbanking.app.base.BaseFragment;
import ar.com.santander.rio.mbanking.app.module.accounts.analytics.AccountAnalytics;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;
import javax.inject.Provider;

public final class DetallesMovimientoFragment$$InjectAdapter extends Binding<DetallesMovimientoFragment> implements MembersInjector<DetallesMovimientoFragment>, Provider<DetallesMovimientoFragment> {
    private Binding<AccountAnalytics> a;
    private Binding<BaseFragment> b;

    public DetallesMovimientoFragment$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.fragments.privado.DetallesMovimientoFragment", "members/ar.com.santander.rio.mbanking.app.ui.fragments.privado.DetallesMovimientoFragment", false, DetallesMovimientoFragment.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.app.module.accounts.analytics.AccountAnalytics", DetallesMovimientoFragment.class, getClass().getClassLoader());
        this.b = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.base.BaseFragment", DetallesMovimientoFragment.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
    }

    public DetallesMovimientoFragment get() {
        DetallesMovimientoFragment detallesMovimientoFragment = new DetallesMovimientoFragment();
        injectMembers(detallesMovimientoFragment);
        return detallesMovimientoFragment;
    }

    public void injectMembers(DetallesMovimientoFragment detallesMovimientoFragment) {
        detallesMovimientoFragment.mAccountAnalytics = (AccountAnalytics) this.a.get();
        this.b.injectMembers(detallesMovimientoFragment);
    }
}
