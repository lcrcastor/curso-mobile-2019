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

public final class ActivityEnvioDineroDetalleOperacion$$InjectAdapter extends Binding<ActivityEnvioDineroDetalleOperacion> implements MembersInjector<ActivityEnvioDineroDetalleOperacion>, Provider<ActivityEnvioDineroDetalleOperacion> {
    private Binding<IDataManager> a;
    private Binding<SessionManager> b;
    private Binding<SoftTokenManager> c;
    private Binding<AnalyticsManager> d;
    private Binding<Bus> e;
    private Binding<BaseActivity> f;

    public ActivityEnvioDineroDetalleOperacion$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroDetalleOperacion", "members/ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroDetalleOperacion", false, ActivityEnvioDineroDetalleOperacion.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.managers.data.IDataManager", ActivityEnvioDineroDetalleOperacion.class, getClass().getClassLoader());
        this.b = linker.requestBinding("ar.com.santander.rio.mbanking.managers.session.SessionManager", ActivityEnvioDineroDetalleOperacion.class, getClass().getClassLoader());
        this.c = linker.requestBinding("ar.com.santander.rio.mbanking.managers.security.SoftTokenManager", ActivityEnvioDineroDetalleOperacion.class, getClass().getClassLoader());
        this.d = linker.requestBinding("ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager", ActivityEnvioDineroDetalleOperacion.class, getClass().getClassLoader());
        this.e = linker.requestBinding("com.squareup.otto.Bus", ActivityEnvioDineroDetalleOperacion.class, getClass().getClassLoader());
        this.f = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.base.BaseActivity", ActivityEnvioDineroDetalleOperacion.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
        set2.add(this.c);
        set2.add(this.d);
        set2.add(this.e);
        set2.add(this.f);
    }

    public ActivityEnvioDineroDetalleOperacion get() {
        ActivityEnvioDineroDetalleOperacion activityEnvioDineroDetalleOperacion = new ActivityEnvioDineroDetalleOperacion();
        injectMembers(activityEnvioDineroDetalleOperacion);
        return activityEnvioDineroDetalleOperacion;
    }

    public void injectMembers(ActivityEnvioDineroDetalleOperacion activityEnvioDineroDetalleOperacion) {
        activityEnvioDineroDetalleOperacion.p = (IDataManager) this.a.get();
        activityEnvioDineroDetalleOperacion.q = (SessionManager) this.b.get();
        activityEnvioDineroDetalleOperacion.r = (SoftTokenManager) this.c.get();
        activityEnvioDineroDetalleOperacion.s = (AnalyticsManager) this.d.get();
        activityEnvioDineroDetalleOperacion.t = (Bus) this.e.get();
        this.f.injectMembers(activityEnvioDineroDetalleOperacion);
    }
}
