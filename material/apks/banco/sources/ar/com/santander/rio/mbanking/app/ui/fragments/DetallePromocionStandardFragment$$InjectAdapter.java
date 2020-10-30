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

public final class DetallePromocionStandardFragment$$InjectAdapter extends Binding<DetallePromocionStandardFragment> implements MembersInjector<DetallePromocionStandardFragment>, Provider<DetallePromocionStandardFragment> {
    private Binding<IDataManager> a;
    private Binding<SessionManager> b;
    private Binding<AnalyticsManager> c;
    private Binding<BaseFragment> d;

    public DetallePromocionStandardFragment$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.fragments.DetallePromocionStandardFragment", "members/ar.com.santander.rio.mbanking.app.ui.fragments.DetallePromocionStandardFragment", false, DetallePromocionStandardFragment.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.managers.data.IDataManager", DetallePromocionStandardFragment.class, getClass().getClassLoader());
        this.b = linker.requestBinding("ar.com.santander.rio.mbanking.managers.session.SessionManager", DetallePromocionStandardFragment.class, getClass().getClassLoader());
        this.c = linker.requestBinding("ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager", DetallePromocionStandardFragment.class, getClass().getClassLoader());
        this.d = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.base.BaseFragment", DetallePromocionStandardFragment.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
        set2.add(this.c);
        set2.add(this.d);
    }

    public DetallePromocionStandardFragment get() {
        DetallePromocionStandardFragment detallePromocionStandardFragment = new DetallePromocionStandardFragment();
        injectMembers(detallePromocionStandardFragment);
        return detallePromocionStandardFragment;
    }

    public void injectMembers(DetallePromocionStandardFragment detallePromocionStandardFragment) {
        detallePromocionStandardFragment.a = (IDataManager) this.a.get();
        detallePromocionStandardFragment.b = (SessionManager) this.b.get();
        detallePromocionStandardFragment.c = (AnalyticsManager) this.c.get();
        this.d.injectMembers(detallePromocionStandardFragment);
    }
}
