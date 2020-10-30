package ar.com.santander.rio.mbanking.app.ui.activities;

import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;
import javax.inject.Provider;

public final class NuevoDebinActivity$$InjectAdapter extends Binding<NuevoDebinActivity> implements MembersInjector<NuevoDebinActivity>, Provider<NuevoDebinActivity> {
    private Binding<AnalyticsManager> a;
    private Binding<MvpPrivateMenuActivity> b;

    public NuevoDebinActivity$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.activities.NuevoDebinActivity", "members/ar.com.santander.rio.mbanking.app.ui.activities.NuevoDebinActivity", false, NuevoDebinActivity.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager", NuevoDebinActivity.class, getClass().getClassLoader());
        this.b = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.ui.activities.MvpPrivateMenuActivity", NuevoDebinActivity.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
    }

    public NuevoDebinActivity get() {
        NuevoDebinActivity nuevoDebinActivity = new NuevoDebinActivity();
        injectMembers(nuevoDebinActivity);
        return nuevoDebinActivity;
    }

    public void injectMembers(NuevoDebinActivity nuevoDebinActivity) {
        nuevoDebinActivity.q = (AnalyticsManager) this.a.get();
        this.b.injectMembers(nuevoDebinActivity);
    }
}
