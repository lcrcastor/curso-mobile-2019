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

public final class ActivityEnvioDineroListadoDestinatarios$$InjectAdapter extends Binding<ActivityEnvioDineroListadoDestinatarios> implements MembersInjector<ActivityEnvioDineroListadoDestinatarios>, Provider<ActivityEnvioDineroListadoDestinatarios> {
    private Binding<AnalyticsManager> a;
    private Binding<IDataManager> b;
    private Binding<SessionManager> c;
    private Binding<Bus> d;
    private Binding<BaseActivity> e;

    public ActivityEnvioDineroListadoDestinatarios$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroListadoDestinatarios", "members/ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroListadoDestinatarios", false, ActivityEnvioDineroListadoDestinatarios.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager", ActivityEnvioDineroListadoDestinatarios.class, getClass().getClassLoader());
        this.b = linker.requestBinding("ar.com.santander.rio.mbanking.managers.data.IDataManager", ActivityEnvioDineroListadoDestinatarios.class, getClass().getClassLoader());
        this.c = linker.requestBinding("ar.com.santander.rio.mbanking.managers.session.SessionManager", ActivityEnvioDineroListadoDestinatarios.class, getClass().getClassLoader());
        this.d = linker.requestBinding("com.squareup.otto.Bus", ActivityEnvioDineroListadoDestinatarios.class, getClass().getClassLoader());
        this.e = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.base.BaseActivity", ActivityEnvioDineroListadoDestinatarios.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
        set2.add(this.c);
        set2.add(this.d);
        set2.add(this.e);
    }

    public ActivityEnvioDineroListadoDestinatarios get() {
        ActivityEnvioDineroListadoDestinatarios activityEnvioDineroListadoDestinatarios = new ActivityEnvioDineroListadoDestinatarios();
        injectMembers(activityEnvioDineroListadoDestinatarios);
        return activityEnvioDineroListadoDestinatarios;
    }

    public void injectMembers(ActivityEnvioDineroListadoDestinatarios activityEnvioDineroListadoDestinatarios) {
        activityEnvioDineroListadoDestinatarios.p = (AnalyticsManager) this.a.get();
        activityEnvioDineroListadoDestinatarios.q = (IDataManager) this.b.get();
        activityEnvioDineroListadoDestinatarios.r = (SessionManager) this.c.get();
        activityEnvioDineroListadoDestinatarios.s = (Bus) this.d.get();
        this.e.injectMembers(activityEnvioDineroListadoDestinatarios);
    }
}
