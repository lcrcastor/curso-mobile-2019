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

public final class ActivityExtraccionSinTarjetaOperacionesRealizadas$$InjectAdapter extends Binding<ActivityExtraccionSinTarjetaOperacionesRealizadas> implements MembersInjector<ActivityExtraccionSinTarjetaOperacionesRealizadas>, Provider<ActivityExtraccionSinTarjetaOperacionesRealizadas> {
    private Binding<Bus> a;
    private Binding<IDataManager> b;
    private Binding<SessionManager> c;
    private Binding<AnalyticsManager> d;
    private Binding<BaseActivity> e;

    public ActivityExtraccionSinTarjetaOperacionesRealizadas$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.activities.ActivityExtraccionSinTarjetaOperacionesRealizadas", "members/ar.com.santander.rio.mbanking.app.ui.activities.ActivityExtraccionSinTarjetaOperacionesRealizadas", false, ActivityExtraccionSinTarjetaOperacionesRealizadas.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("com.squareup.otto.Bus", ActivityExtraccionSinTarjetaOperacionesRealizadas.class, getClass().getClassLoader());
        this.b = linker.requestBinding("ar.com.santander.rio.mbanking.managers.data.IDataManager", ActivityExtraccionSinTarjetaOperacionesRealizadas.class, getClass().getClassLoader());
        this.c = linker.requestBinding("ar.com.santander.rio.mbanking.managers.session.SessionManager", ActivityExtraccionSinTarjetaOperacionesRealizadas.class, getClass().getClassLoader());
        this.d = linker.requestBinding("ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager", ActivityExtraccionSinTarjetaOperacionesRealizadas.class, getClass().getClassLoader());
        this.e = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.base.BaseActivity", ActivityExtraccionSinTarjetaOperacionesRealizadas.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
        set2.add(this.c);
        set2.add(this.d);
        set2.add(this.e);
    }

    public ActivityExtraccionSinTarjetaOperacionesRealizadas get() {
        ActivityExtraccionSinTarjetaOperacionesRealizadas activityExtraccionSinTarjetaOperacionesRealizadas = new ActivityExtraccionSinTarjetaOperacionesRealizadas();
        injectMembers(activityExtraccionSinTarjetaOperacionesRealizadas);
        return activityExtraccionSinTarjetaOperacionesRealizadas;
    }

    public void injectMembers(ActivityExtraccionSinTarjetaOperacionesRealizadas activityExtraccionSinTarjetaOperacionesRealizadas) {
        activityExtraccionSinTarjetaOperacionesRealizadas.p = (Bus) this.a.get();
        activityExtraccionSinTarjetaOperacionesRealizadas.q = (IDataManager) this.b.get();
        activityExtraccionSinTarjetaOperacionesRealizadas.r = (SessionManager) this.c.get();
        activityExtraccionSinTarjetaOperacionesRealizadas.s = (AnalyticsManager) this.d.get();
        this.e.injectMembers(activityExtraccionSinTarjetaOperacionesRealizadas);
    }
}
