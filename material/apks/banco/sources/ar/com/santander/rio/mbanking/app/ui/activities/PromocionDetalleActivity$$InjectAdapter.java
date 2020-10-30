package ar.com.santander.rio.mbanking.app.ui.activities;

import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;
import javax.inject.Provider;

public final class PromocionDetalleActivity$$InjectAdapter extends Binding<PromocionDetalleActivity> implements MembersInjector<PromocionDetalleActivity>, Provider<PromocionDetalleActivity> {
    private Binding<AnalyticsManager> a;
    private Binding<SessionManager> b;
    private Binding<BaseActivity> c;

    public PromocionDetalleActivity$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.activities.PromocionDetalleActivity", "members/ar.com.santander.rio.mbanking.app.ui.activities.PromocionDetalleActivity", false, PromocionDetalleActivity.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager", PromocionDetalleActivity.class, getClass().getClassLoader());
        this.b = linker.requestBinding("ar.com.santander.rio.mbanking.managers.session.SessionManager", PromocionDetalleActivity.class, getClass().getClassLoader());
        this.c = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.base.BaseActivity", PromocionDetalleActivity.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
        set2.add(this.c);
    }

    public PromocionDetalleActivity get() {
        PromocionDetalleActivity promocionDetalleActivity = new PromocionDetalleActivity();
        injectMembers(promocionDetalleActivity);
        return promocionDetalleActivity;
    }

    public void injectMembers(PromocionDetalleActivity promocionDetalleActivity) {
        promocionDetalleActivity.p = (AnalyticsManager) this.a.get();
        promocionDetalleActivity.q = (SessionManager) this.b.get();
        this.c.injectMembers(promocionDetalleActivity);
    }
}
