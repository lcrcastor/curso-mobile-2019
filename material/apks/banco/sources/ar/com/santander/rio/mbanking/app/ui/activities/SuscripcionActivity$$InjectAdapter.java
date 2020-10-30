package ar.com.santander.rio.mbanking.app.ui.activities;

import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import com.squareup.otto.Bus;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;
import javax.inject.Provider;

public final class SuscripcionActivity$$InjectAdapter extends Binding<SuscripcionActivity> implements MembersInjector<SuscripcionActivity>, Provider<SuscripcionActivity> {
    private Binding<Bus> a;
    private Binding<IDataManager> b;
    private Binding<SessionManager> c;
    private Binding<AnalyticsManager> d;
    private Binding<BaseActivity> e;

    public SuscripcionActivity$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.activities.SuscripcionActivity", "members/ar.com.santander.rio.mbanking.app.ui.activities.SuscripcionActivity", false, SuscripcionActivity.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("com.squareup.otto.Bus", SuscripcionActivity.class, getClass().getClassLoader());
        this.b = linker.requestBinding("ar.com.santander.rio.mbanking.managers.data.IDataManager", SuscripcionActivity.class, getClass().getClassLoader());
        this.c = linker.requestBinding("ar.com.santander.rio.mbanking.managers.session.SessionManager", SuscripcionActivity.class, getClass().getClassLoader());
        this.d = linker.requestBinding("ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager", SuscripcionActivity.class, getClass().getClassLoader());
        this.e = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.base.BaseActivity", SuscripcionActivity.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
        set2.add(this.c);
        set2.add(this.d);
        set2.add(this.e);
    }

    public SuscripcionActivity get() {
        SuscripcionActivity suscripcionActivity = new SuscripcionActivity();
        injectMembers(suscripcionActivity);
        return suscripcionActivity;
    }

    public void injectMembers(SuscripcionActivity suscripcionActivity) {
        suscripcionActivity.p = (Bus) this.a.get();
        suscripcionActivity.q = (IDataManager) this.b.get();
        suscripcionActivity.r = (SessionManager) this.c.get();
        suscripcionActivity.s = (AnalyticsManager) this.d.get();
        this.e.injectMembers(suscripcionActivity);
    }
}
