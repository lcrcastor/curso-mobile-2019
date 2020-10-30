package ar.com.santander.rio.mbanking.app.ui.activities;

import ar.com.santander.rio.mbanking.app.base.BaseMvpActivity;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.preferences.SettingsManager;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;
import javax.inject.Provider;

public final class BuscarEmpresaNuevoPagoActivity$$InjectAdapter extends Binding<BuscarEmpresaNuevoPagoActivity> implements MembersInjector<BuscarEmpresaNuevoPagoActivity>, Provider<BuscarEmpresaNuevoPagoActivity> {
    private Binding<AnalyticsManager> a;
    private Binding<SettingsManager> b;
    private Binding<BaseMvpActivity> c;

    public BuscarEmpresaNuevoPagoActivity$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.activities.BuscarEmpresaNuevoPagoActivity", "members/ar.com.santander.rio.mbanking.app.ui.activities.BuscarEmpresaNuevoPagoActivity", false, BuscarEmpresaNuevoPagoActivity.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager", BuscarEmpresaNuevoPagoActivity.class, getClass().getClassLoader());
        this.b = linker.requestBinding("ar.com.santander.rio.mbanking.managers.preferences.SettingsManager", BuscarEmpresaNuevoPagoActivity.class, getClass().getClassLoader());
        this.c = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.base.BaseMvpActivity", BuscarEmpresaNuevoPagoActivity.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
        set2.add(this.c);
    }

    public BuscarEmpresaNuevoPagoActivity get() {
        BuscarEmpresaNuevoPagoActivity buscarEmpresaNuevoPagoActivity = new BuscarEmpresaNuevoPagoActivity();
        injectMembers(buscarEmpresaNuevoPagoActivity);
        return buscarEmpresaNuevoPagoActivity;
    }

    public void injectMembers(BuscarEmpresaNuevoPagoActivity buscarEmpresaNuevoPagoActivity) {
        buscarEmpresaNuevoPagoActivity.q = (AnalyticsManager) this.a.get();
        buscarEmpresaNuevoPagoActivity.r = (SettingsManager) this.b.get();
        this.c.injectMembers(buscarEmpresaNuevoPagoActivity);
    }
}
