package ar.com.santander.rio.mbanking.app.ui.activities;

import ar.com.santander.rio.mbanking.app.base.BaseMvpActivity;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;
import javax.inject.Provider;

public final class SeleccionarPagoNuevoPagoActivity$$InjectAdapter extends Binding<SeleccionarPagoNuevoPagoActivity> implements MembersInjector<SeleccionarPagoNuevoPagoActivity>, Provider<SeleccionarPagoNuevoPagoActivity> {
    private Binding<AnalyticsManager> a;
    private Binding<BaseMvpActivity> b;

    public SeleccionarPagoNuevoPagoActivity$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.activities.SeleccionarPagoNuevoPagoActivity", "members/ar.com.santander.rio.mbanking.app.ui.activities.SeleccionarPagoNuevoPagoActivity", false, SeleccionarPagoNuevoPagoActivity.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager", SeleccionarPagoNuevoPagoActivity.class, getClass().getClassLoader());
        this.b = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.base.BaseMvpActivity", SeleccionarPagoNuevoPagoActivity.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
    }

    public SeleccionarPagoNuevoPagoActivity get() {
        SeleccionarPagoNuevoPagoActivity seleccionarPagoNuevoPagoActivity = new SeleccionarPagoNuevoPagoActivity();
        injectMembers(seleccionarPagoNuevoPagoActivity);
        return seleccionarPagoNuevoPagoActivity;
    }

    public void injectMembers(SeleccionarPagoNuevoPagoActivity seleccionarPagoNuevoPagoActivity) {
        seleccionarPagoNuevoPagoActivity.r = (AnalyticsManager) this.a.get();
        this.b.injectMembers(seleccionarPagoNuevoPagoActivity);
    }
}
