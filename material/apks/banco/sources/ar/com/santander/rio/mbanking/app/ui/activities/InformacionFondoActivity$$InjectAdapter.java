package ar.com.santander.rio.mbanking.app.ui.activities;

import ar.com.santander.rio.mbanking.app.base.BaseMvpActivity;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;
import javax.inject.Provider;

public final class InformacionFondoActivity$$InjectAdapter extends Binding<InformacionFondoActivity> implements MembersInjector<InformacionFondoActivity>, Provider<InformacionFondoActivity> {
    private Binding<SessionManager> a;
    private Binding<AnalyticsManager> b;
    private Binding<BaseMvpActivity> c;

    public InformacionFondoActivity$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.activities.InformacionFondoActivity", "members/ar.com.santander.rio.mbanking.app.ui.activities.InformacionFondoActivity", false, InformacionFondoActivity.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.managers.session.SessionManager", InformacionFondoActivity.class, getClass().getClassLoader());
        this.b = linker.requestBinding("ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager", InformacionFondoActivity.class, getClass().getClassLoader());
        this.c = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.base.BaseMvpActivity", InformacionFondoActivity.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
        set2.add(this.c);
    }

    public InformacionFondoActivity get() {
        InformacionFondoActivity informacionFondoActivity = new InformacionFondoActivity();
        injectMembers(informacionFondoActivity);
        return informacionFondoActivity;
    }

    public void injectMembers(InformacionFondoActivity informacionFondoActivity) {
        informacionFondoActivity.p = (SessionManager) this.a.get();
        informacionFondoActivity.s = (AnalyticsManager) this.b.get();
        this.c.injectMembers(informacionFondoActivity);
    }
}
