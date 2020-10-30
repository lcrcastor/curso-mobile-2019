package ar.com.santander.rio.mbanking.app.ui.activities;

import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.security.SoftTokenManager;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;
import javax.inject.Provider;

public final class NuevoDestinatarioTransferenciaActivity$$InjectAdapter extends Binding<NuevoDestinatarioTransferenciaActivity> implements MembersInjector<NuevoDestinatarioTransferenciaActivity>, Provider<NuevoDestinatarioTransferenciaActivity> {
    private Binding<SoftTokenManager> a;
    private Binding<AnalyticsManager> b;
    private Binding<MvpPrivateMenuActivity> c;

    public NuevoDestinatarioTransferenciaActivity$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.activities.NuevoDestinatarioTransferenciaActivity", "members/ar.com.santander.rio.mbanking.app.ui.activities.NuevoDestinatarioTransferenciaActivity", false, NuevoDestinatarioTransferenciaActivity.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.managers.security.SoftTokenManager", NuevoDestinatarioTransferenciaActivity.class, getClass().getClassLoader());
        this.b = linker.requestBinding("ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager", NuevoDestinatarioTransferenciaActivity.class, getClass().getClassLoader());
        this.c = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.ui.activities.MvpPrivateMenuActivity", NuevoDestinatarioTransferenciaActivity.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
        set2.add(this.c);
    }

    public NuevoDestinatarioTransferenciaActivity get() {
        NuevoDestinatarioTransferenciaActivity nuevoDestinatarioTransferenciaActivity = new NuevoDestinatarioTransferenciaActivity();
        injectMembers(nuevoDestinatarioTransferenciaActivity);
        return nuevoDestinatarioTransferenciaActivity;
    }

    public void injectMembers(NuevoDestinatarioTransferenciaActivity nuevoDestinatarioTransferenciaActivity) {
        nuevoDestinatarioTransferenciaActivity.p = (SoftTokenManager) this.a.get();
        nuevoDestinatarioTransferenciaActivity.q = (AnalyticsManager) this.b.get();
        this.c.injectMembers(nuevoDestinatarioTransferenciaActivity);
    }
}
