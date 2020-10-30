package ar.com.santander.rio.mbanking.app.ui.fragments;

import ar.com.santander.rio.mbanking.app.base.BaseFragment;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;
import javax.inject.Provider;

public final class DetallePromocionShoppingFragment$$InjectAdapter extends Binding<DetallePromocionShoppingFragment> implements MembersInjector<DetallePromocionShoppingFragment>, Provider<DetallePromocionShoppingFragment> {
    private Binding<SessionManager> a;
    private Binding<IDataManager> b;
    private Binding<AnalyticsManager> c;
    private Binding<BaseFragment> d;

    public DetallePromocionShoppingFragment$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.fragments.DetallePromocionShoppingFragment", "members/ar.com.santander.rio.mbanking.app.ui.fragments.DetallePromocionShoppingFragment", false, DetallePromocionShoppingFragment.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.managers.session.SessionManager", DetallePromocionShoppingFragment.class, getClass().getClassLoader());
        this.b = linker.requestBinding("ar.com.santander.rio.mbanking.managers.data.IDataManager", DetallePromocionShoppingFragment.class, getClass().getClassLoader());
        this.c = linker.requestBinding("ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager", DetallePromocionShoppingFragment.class, getClass().getClassLoader());
        this.d = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.base.BaseFragment", DetallePromocionShoppingFragment.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
        set2.add(this.c);
        set2.add(this.d);
    }

    public DetallePromocionShoppingFragment get() {
        DetallePromocionShoppingFragment detallePromocionShoppingFragment = new DetallePromocionShoppingFragment();
        injectMembers(detallePromocionShoppingFragment);
        return detallePromocionShoppingFragment;
    }

    public void injectMembers(DetallePromocionShoppingFragment detallePromocionShoppingFragment) {
        detallePromocionShoppingFragment.a = (SessionManager) this.a.get();
        detallePromocionShoppingFragment.b = (IDataManager) this.b.get();
        detallePromocionShoppingFragment.c = (AnalyticsManager) this.c.get();
        this.d.injectMembers(detallePromocionShoppingFragment);
    }
}
