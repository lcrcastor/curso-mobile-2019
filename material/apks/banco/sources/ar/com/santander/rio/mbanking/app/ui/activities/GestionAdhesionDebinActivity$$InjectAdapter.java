package ar.com.santander.rio.mbanking.app.ui.activities;

import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.security.SoftTokenManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;
import javax.inject.Provider;

public final class GestionAdhesionDebinActivity$$InjectAdapter extends Binding<GestionAdhesionDebinActivity> implements MembersInjector<GestionAdhesionDebinActivity>, Provider<GestionAdhesionDebinActivity> {
    private Binding<AnalyticsManager> a;
    private Binding<SessionManager> b;
    private Binding<SoftTokenManager> c;
    private Binding<MvpPrivateMenuActivity> d;

    public GestionAdhesionDebinActivity$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.activities.GestionAdhesionDebinActivity", "members/ar.com.santander.rio.mbanking.app.ui.activities.GestionAdhesionDebinActivity", false, GestionAdhesionDebinActivity.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager", GestionAdhesionDebinActivity.class, getClass().getClassLoader());
        this.b = linker.requestBinding("ar.com.santander.rio.mbanking.managers.session.SessionManager", GestionAdhesionDebinActivity.class, getClass().getClassLoader());
        this.c = linker.requestBinding("ar.com.santander.rio.mbanking.managers.security.SoftTokenManager", GestionAdhesionDebinActivity.class, getClass().getClassLoader());
        this.d = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.ui.activities.MvpPrivateMenuActivity", GestionAdhesionDebinActivity.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
        set2.add(this.c);
        set2.add(this.d);
    }

    public GestionAdhesionDebinActivity get() {
        GestionAdhesionDebinActivity gestionAdhesionDebinActivity = new GestionAdhesionDebinActivity();
        injectMembers(gestionAdhesionDebinActivity);
        return gestionAdhesionDebinActivity;
    }

    public void injectMembers(GestionAdhesionDebinActivity gestionAdhesionDebinActivity) {
        gestionAdhesionDebinActivity.p = (AnalyticsManager) this.a.get();
        gestionAdhesionDebinActivity.q = (SessionManager) this.b.get();
        gestionAdhesionDebinActivity.r = (SoftTokenManager) this.c.get();
        this.d.injectMembers(gestionAdhesionDebinActivity);
    }
}
