package ar.com.santander.rio.mbanking.app.ui.activities;

import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;

public final class ComprobanteNuevoPagoActivity$$InjectAdapter extends Binding<ComprobanteNuevoPagoActivity> implements MembersInjector<ComprobanteNuevoPagoActivity> {
    private Binding<AnalyticsManager> a;
    private Binding<MvpPrivateMenuActivity> b;

    public ComprobanteNuevoPagoActivity$$InjectAdapter() {
        super(null, "members/ar.com.santander.rio.mbanking.app.ui.activities.ComprobanteNuevoPagoActivity", false, ComprobanteNuevoPagoActivity.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager", ComprobanteNuevoPagoActivity.class, getClass().getClassLoader());
        this.b = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.ui.activities.MvpPrivateMenuActivity", ComprobanteNuevoPagoActivity.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
    }

    public void injectMembers(ComprobanteNuevoPagoActivity comprobanteNuevoPagoActivity) {
        comprobanteNuevoPagoActivity.r = (AnalyticsManager) this.a.get();
        this.b.injectMembers(comprobanteNuevoPagoActivity);
    }
}
