package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;
import javax.inject.Provider;

public final class PerfilDelInversorActivity$$InjectAdapter extends Binding<PerfilDelInversorActivity> implements MembersInjector<PerfilDelInversorActivity>, Provider<PerfilDelInversorActivity> {
    private Binding<AnalyticsManager> a;
    private Binding<BaseActivity> b;

    public PerfilDelInversorActivity$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.fragments.privado.PerfilDelInversorActivity", "members/ar.com.santander.rio.mbanking.app.ui.fragments.privado.PerfilDelInversorActivity", false, PerfilDelInversorActivity.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager", PerfilDelInversorActivity.class, getClass().getClassLoader());
        this.b = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.base.BaseActivity", PerfilDelInversorActivity.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
    }

    public PerfilDelInversorActivity get() {
        PerfilDelInversorActivity perfilDelInversorActivity = new PerfilDelInversorActivity();
        injectMembers(perfilDelInversorActivity);
        return perfilDelInversorActivity;
    }

    public void injectMembers(PerfilDelInversorActivity perfilDelInversorActivity) {
        perfilDelInversorActivity.analyticsManager = (AnalyticsManager) this.a.get();
        this.b.injectMembers(perfilDelInversorActivity);
    }
}
