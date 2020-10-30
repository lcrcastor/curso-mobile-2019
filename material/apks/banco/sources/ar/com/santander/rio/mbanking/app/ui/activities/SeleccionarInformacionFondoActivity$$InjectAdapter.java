package ar.com.santander.rio.mbanking.app.ui.activities;

import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;
import javax.inject.Provider;

public final class SeleccionarInformacionFondoActivity$$InjectAdapter extends Binding<SeleccionarInformacionFondoActivity> implements MembersInjector<SeleccionarInformacionFondoActivity>, Provider<SeleccionarInformacionFondoActivity> {
    private Binding<AnalyticsManager> a;
    private Binding<MvpPrivateMenuActivity> b;

    public SeleccionarInformacionFondoActivity$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.activities.SeleccionarInformacionFondoActivity", "members/ar.com.santander.rio.mbanking.app.ui.activities.SeleccionarInformacionFondoActivity", false, SeleccionarInformacionFondoActivity.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager", SeleccionarInformacionFondoActivity.class, getClass().getClassLoader());
        this.b = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.ui.activities.MvpPrivateMenuActivity", SeleccionarInformacionFondoActivity.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
    }

    public SeleccionarInformacionFondoActivity get() {
        SeleccionarInformacionFondoActivity seleccionarInformacionFondoActivity = new SeleccionarInformacionFondoActivity();
        injectMembers(seleccionarInformacionFondoActivity);
        return seleccionarInformacionFondoActivity;
    }

    public void injectMembers(SeleccionarInformacionFondoActivity seleccionarInformacionFondoActivity) {
        seleccionarInformacionFondoActivity.w = (AnalyticsManager) this.a.get();
        this.b.injectMembers(seleccionarInformacionFondoActivity);
    }
}
