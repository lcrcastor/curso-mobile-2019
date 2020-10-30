package ar.com.santander.rio.mbanking.app.ui.activities;

import ar.com.santander.rio.mbanking.app.base.BaseMvpActivity;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;
import javax.inject.Provider;

public final class EscanearCodigoBarrasNuevoPagoActivity$$InjectAdapter extends Binding<EscanearCodigoBarrasNuevoPagoActivity> implements MembersInjector<EscanearCodigoBarrasNuevoPagoActivity>, Provider<EscanearCodigoBarrasNuevoPagoActivity> {
    private Binding<AnalyticsManager> a;
    private Binding<BaseMvpActivity> b;

    public EscanearCodigoBarrasNuevoPagoActivity$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.activities.EscanearCodigoBarrasNuevoPagoActivity", "members/ar.com.santander.rio.mbanking.app.ui.activities.EscanearCodigoBarrasNuevoPagoActivity", false, EscanearCodigoBarrasNuevoPagoActivity.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager", EscanearCodigoBarrasNuevoPagoActivity.class, getClass().getClassLoader());
        this.b = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.base.BaseMvpActivity", EscanearCodigoBarrasNuevoPagoActivity.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
    }

    public EscanearCodigoBarrasNuevoPagoActivity get() {
        EscanearCodigoBarrasNuevoPagoActivity escanearCodigoBarrasNuevoPagoActivity = new EscanearCodigoBarrasNuevoPagoActivity();
        injectMembers(escanearCodigoBarrasNuevoPagoActivity);
        return escanearCodigoBarrasNuevoPagoActivity;
    }

    public void injectMembers(EscanearCodigoBarrasNuevoPagoActivity escanearCodigoBarrasNuevoPagoActivity) {
        escanearCodigoBarrasNuevoPagoActivity.q = (AnalyticsManager) this.a.get();
        this.b.injectMembers(escanearCodigoBarrasNuevoPagoActivity);
    }
}
