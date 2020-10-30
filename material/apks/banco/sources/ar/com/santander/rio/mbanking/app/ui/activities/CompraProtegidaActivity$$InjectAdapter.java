package ar.com.santander.rio.mbanking.app.ui.activities;

import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;
import javax.inject.Provider;

public final class CompraProtegidaActivity$$InjectAdapter extends Binding<CompraProtegidaActivity> implements MembersInjector<CompraProtegidaActivity>, Provider<CompraProtegidaActivity> {
    private Binding<AnalyticsManager> a;
    private Binding<MvpPrivateMenuActivity> b;

    public CompraProtegidaActivity$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.activities.CompraProtegidaActivity", "members/ar.com.santander.rio.mbanking.app.ui.activities.CompraProtegidaActivity", false, CompraProtegidaActivity.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager", CompraProtegidaActivity.class, getClass().getClassLoader());
        this.b = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.ui.activities.MvpPrivateMenuActivity", CompraProtegidaActivity.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
    }

    public CompraProtegidaActivity get() {
        CompraProtegidaActivity compraProtegidaActivity = new CompraProtegidaActivity();
        injectMembers(compraProtegidaActivity);
        return compraProtegidaActivity;
    }

    public void injectMembers(CompraProtegidaActivity compraProtegidaActivity) {
        compraProtegidaActivity.p = (AnalyticsManager) this.a.get();
        this.b.injectMembers(compraProtegidaActivity);
    }
}
