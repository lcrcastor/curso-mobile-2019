package ar.com.santander.rio.mbanking.app.ui.activities;

import ar.com.santander.rio.mbanking.app.base.BaseMvpActivity;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;
import javax.inject.Provider;

public final class SeleccionarFondoActivity$$InjectAdapter extends Binding<SeleccionarFondoActivity> implements MembersInjector<SeleccionarFondoActivity>, Provider<SeleccionarFondoActivity> {
    private Binding<AnalyticsManager> a;
    private Binding<BaseMvpActivity> b;

    public SeleccionarFondoActivity$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.activities.SeleccionarFondoActivity", "members/ar.com.santander.rio.mbanking.app.ui.activities.SeleccionarFondoActivity", false, SeleccionarFondoActivity.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager", SeleccionarFondoActivity.class, getClass().getClassLoader());
        this.b = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.base.BaseMvpActivity", SeleccionarFondoActivity.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
    }

    public SeleccionarFondoActivity get() {
        SeleccionarFondoActivity seleccionarFondoActivity = new SeleccionarFondoActivity();
        injectMembers(seleccionarFondoActivity);
        return seleccionarFondoActivity;
    }

    public void injectMembers(SeleccionarFondoActivity seleccionarFondoActivity) {
        seleccionarFondoActivity.q = (AnalyticsManager) this.a.get();
        this.b.injectMembers(seleccionarFondoActivity);
    }
}
