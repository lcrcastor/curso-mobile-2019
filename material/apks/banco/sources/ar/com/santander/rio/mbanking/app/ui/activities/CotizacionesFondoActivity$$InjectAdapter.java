package ar.com.santander.rio.mbanking.app.ui.activities;

import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;
import javax.inject.Provider;

public final class CotizacionesFondoActivity$$InjectAdapter extends Binding<CotizacionesFondoActivity> implements MembersInjector<CotizacionesFondoActivity>, Provider<CotizacionesFondoActivity> {
    private Binding<AnalyticsManager> a;
    private Binding<MvpPrivateMenuActivity> b;

    public CotizacionesFondoActivity$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.activities.CotizacionesFondoActivity", "members/ar.com.santander.rio.mbanking.app.ui.activities.CotizacionesFondoActivity", false, CotizacionesFondoActivity.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager", CotizacionesFondoActivity.class, getClass().getClassLoader());
        this.b = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.ui.activities.MvpPrivateMenuActivity", CotizacionesFondoActivity.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
    }

    public CotizacionesFondoActivity get() {
        CotizacionesFondoActivity cotizacionesFondoActivity = new CotizacionesFondoActivity();
        injectMembers(cotizacionesFondoActivity);
        return cotizacionesFondoActivity;
    }

    public void injectMembers(CotizacionesFondoActivity cotizacionesFondoActivity) {
        cotizacionesFondoActivity.t = (AnalyticsManager) this.a.get();
        this.b.injectMembers(cotizacionesFondoActivity);
    }
}
