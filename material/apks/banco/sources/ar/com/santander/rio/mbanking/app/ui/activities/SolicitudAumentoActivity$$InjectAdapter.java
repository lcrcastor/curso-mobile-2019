package ar.com.santander.rio.mbanking.app.ui.activities;

import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;
import javax.inject.Provider;

public final class SolicitudAumentoActivity$$InjectAdapter extends Binding<SolicitudAumentoActivity> implements MembersInjector<SolicitudAumentoActivity>, Provider<SolicitudAumentoActivity> {
    private Binding<SessionManager> a;
    private Binding<AnalyticsManager> b;
    private Binding<MvpPrivateMenuActivity> c;

    public SolicitudAumentoActivity$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.activities.SolicitudAumentoActivity", "members/ar.com.santander.rio.mbanking.app.ui.activities.SolicitudAumentoActivity", false, SolicitudAumentoActivity.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.managers.session.SessionManager", SolicitudAumentoActivity.class, getClass().getClassLoader());
        this.b = linker.requestBinding("ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager", SolicitudAumentoActivity.class, getClass().getClassLoader());
        this.c = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.ui.activities.MvpPrivateMenuActivity", SolicitudAumentoActivity.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
        set2.add(this.c);
    }

    public SolicitudAumentoActivity get() {
        SolicitudAumentoActivity solicitudAumentoActivity = new SolicitudAumentoActivity();
        injectMembers(solicitudAumentoActivity);
        return solicitudAumentoActivity;
    }

    public void injectMembers(SolicitudAumentoActivity solicitudAumentoActivity) {
        solicitudAumentoActivity.p = (SessionManager) this.a.get();
        solicitudAumentoActivity.q = (AnalyticsManager) this.b.get();
        this.c.injectMembers(solicitudAumentoActivity);
    }
}
