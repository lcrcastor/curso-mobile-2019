package ar.com.santander.rio.mbanking.app.ui.activities;

import ar.com.santander.rio.mbanking.app.base.BaseMvpActivity;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;
import javax.inject.Provider;

public final class SeleccionarEmpresaNuevoPagoActivity$$InjectAdapter extends Binding<SeleccionarEmpresaNuevoPagoActivity> implements MembersInjector<SeleccionarEmpresaNuevoPagoActivity>, Provider<SeleccionarEmpresaNuevoPagoActivity> {
    private Binding<AnalyticsManager> a;
    private Binding<BaseMvpActivity> b;

    public SeleccionarEmpresaNuevoPagoActivity$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.activities.SeleccionarEmpresaNuevoPagoActivity", "members/ar.com.santander.rio.mbanking.app.ui.activities.SeleccionarEmpresaNuevoPagoActivity", false, SeleccionarEmpresaNuevoPagoActivity.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager", SeleccionarEmpresaNuevoPagoActivity.class, getClass().getClassLoader());
        this.b = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.base.BaseMvpActivity", SeleccionarEmpresaNuevoPagoActivity.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
    }

    public SeleccionarEmpresaNuevoPagoActivity get() {
        SeleccionarEmpresaNuevoPagoActivity seleccionarEmpresaNuevoPagoActivity = new SeleccionarEmpresaNuevoPagoActivity();
        injectMembers(seleccionarEmpresaNuevoPagoActivity);
        return seleccionarEmpresaNuevoPagoActivity;
    }

    public void injectMembers(SeleccionarEmpresaNuevoPagoActivity seleccionarEmpresaNuevoPagoActivity) {
        seleccionarEmpresaNuevoPagoActivity.q = (AnalyticsManager) this.a.get();
        this.b.injectMembers(seleccionarEmpresaNuevoPagoActivity);
    }
}
