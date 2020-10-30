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

public final class ActivityEnvioDineroOperacionesRealizadas$$InjectAdapter extends Binding<ActivityEnvioDineroOperacionesRealizadas> implements MembersInjector<ActivityEnvioDineroOperacionesRealizadas>, Provider<ActivityEnvioDineroOperacionesRealizadas> {
    private Binding<Bus> a;
    private Binding<IDataManager> b;
    private Binding<AnalyticsManager> c;
    private Binding<SessionManager> d;
    private Binding<BaseActivity> e;

    public ActivityEnvioDineroOperacionesRealizadas$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroOperacionesRealizadas", "members/ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroOperacionesRealizadas", false, ActivityEnvioDineroOperacionesRealizadas.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("com.squareup.otto.Bus", ActivityEnvioDineroOperacionesRealizadas.class, getClass().getClassLoader());
        this.b = linker.requestBinding("ar.com.santander.rio.mbanking.managers.data.IDataManager", ActivityEnvioDineroOperacionesRealizadas.class, getClass().getClassLoader());
        this.c = linker.requestBinding("ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager", ActivityEnvioDineroOperacionesRealizadas.class, getClass().getClassLoader());
        this.d = linker.requestBinding("ar.com.santander.rio.mbanking.managers.session.SessionManager", ActivityEnvioDineroOperacionesRealizadas.class, getClass().getClassLoader());
        this.e = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.base.BaseActivity", ActivityEnvioDineroOperacionesRealizadas.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
        set2.add(this.c);
        set2.add(this.d);
        set2.add(this.e);
    }

    public ActivityEnvioDineroOperacionesRealizadas get() {
        ActivityEnvioDineroOperacionesRealizadas activityEnvioDineroOperacionesRealizadas = new ActivityEnvioDineroOperacionesRealizadas();
        injectMembers(activityEnvioDineroOperacionesRealizadas);
        return activityEnvioDineroOperacionesRealizadas;
    }

    public void injectMembers(ActivityEnvioDineroOperacionesRealizadas activityEnvioDineroOperacionesRealizadas) {
        activityEnvioDineroOperacionesRealizadas.p = (Bus) this.a.get();
        activityEnvioDineroOperacionesRealizadas.q = (IDataManager) this.b.get();
        activityEnvioDineroOperacionesRealizadas.r = (AnalyticsManager) this.c.get();
        activityEnvioDineroOperacionesRealizadas.s = (SessionManager) this.d.get();
        this.e.injectMembers(activityEnvioDineroOperacionesRealizadas);
    }
}
