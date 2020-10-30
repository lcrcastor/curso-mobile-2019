package ar.com.santander.rio.mbanking.app.ui.activities;

import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.managers.security.SoftTokenManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import com.squareup.otto.Bus;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;
import javax.inject.Provider;

public final class ActivityEnvioDineroConfirmacionEnvio$$InjectAdapter extends Binding<ActivityEnvioDineroConfirmacionEnvio> implements MembersInjector<ActivityEnvioDineroConfirmacionEnvio>, Provider<ActivityEnvioDineroConfirmacionEnvio> {
    private Binding<IDataManager> a;
    private Binding<SessionManager> b;
    private Binding<Bus> c;
    private Binding<SoftTokenManager> d;
    private Binding<AnalyticsManager> e;
    private Binding<BaseActivity> f;

    public ActivityEnvioDineroConfirmacionEnvio$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroConfirmacionEnvio", "members/ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroConfirmacionEnvio", false, ActivityEnvioDineroConfirmacionEnvio.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.managers.data.IDataManager", ActivityEnvioDineroConfirmacionEnvio.class, getClass().getClassLoader());
        this.b = linker.requestBinding("ar.com.santander.rio.mbanking.managers.session.SessionManager", ActivityEnvioDineroConfirmacionEnvio.class, getClass().getClassLoader());
        this.c = linker.requestBinding("com.squareup.otto.Bus", ActivityEnvioDineroConfirmacionEnvio.class, getClass().getClassLoader());
        this.d = linker.requestBinding("ar.com.santander.rio.mbanking.managers.security.SoftTokenManager", ActivityEnvioDineroConfirmacionEnvio.class, getClass().getClassLoader());
        this.e = linker.requestBinding("ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager", ActivityEnvioDineroConfirmacionEnvio.class, getClass().getClassLoader());
        this.f = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.base.BaseActivity", ActivityEnvioDineroConfirmacionEnvio.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
        set2.add(this.c);
        set2.add(this.d);
        set2.add(this.e);
        set2.add(this.f);
    }

    public ActivityEnvioDineroConfirmacionEnvio get() {
        ActivityEnvioDineroConfirmacionEnvio activityEnvioDineroConfirmacionEnvio = new ActivityEnvioDineroConfirmacionEnvio();
        injectMembers(activityEnvioDineroConfirmacionEnvio);
        return activityEnvioDineroConfirmacionEnvio;
    }

    public void injectMembers(ActivityEnvioDineroConfirmacionEnvio activityEnvioDineroConfirmacionEnvio) {
        activityEnvioDineroConfirmacionEnvio.r = (IDataManager) this.a.get();
        activityEnvioDineroConfirmacionEnvio.s = (SessionManager) this.b.get();
        activityEnvioDineroConfirmacionEnvio.t = (Bus) this.c.get();
        activityEnvioDineroConfirmacionEnvio.u = (SoftTokenManager) this.d.get();
        activityEnvioDineroConfirmacionEnvio.v = (AnalyticsManager) this.e.get();
        this.f.injectMembers(activityEnvioDineroConfirmacionEnvio);
    }
}
