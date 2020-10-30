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

public final class BusquedaAvanzadaActivity$$InjectAdapter extends Binding<BusquedaAvanzadaActivity> implements MembersInjector<BusquedaAvanzadaActivity>, Provider<BusquedaAvanzadaActivity> {
    private Binding<Bus> a;
    private Binding<SessionManager> b;
    private Binding<IDataManager> c;
    private Binding<AnalyticsManager> d;
    private Binding<BaseActivity> e;

    public BusquedaAvanzadaActivity$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.activities.BusquedaAvanzadaActivity", "members/ar.com.santander.rio.mbanking.app.ui.activities.BusquedaAvanzadaActivity", false, BusquedaAvanzadaActivity.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("com.squareup.otto.Bus", BusquedaAvanzadaActivity.class, getClass().getClassLoader());
        this.b = linker.requestBinding("ar.com.santander.rio.mbanking.managers.session.SessionManager", BusquedaAvanzadaActivity.class, getClass().getClassLoader());
        this.c = linker.requestBinding("ar.com.santander.rio.mbanking.managers.data.IDataManager", BusquedaAvanzadaActivity.class, getClass().getClassLoader());
        this.d = linker.requestBinding("ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager", BusquedaAvanzadaActivity.class, getClass().getClassLoader());
        this.e = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.base.BaseActivity", BusquedaAvanzadaActivity.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
        set2.add(this.c);
        set2.add(this.d);
        set2.add(this.e);
    }

    public BusquedaAvanzadaActivity get() {
        BusquedaAvanzadaActivity busquedaAvanzadaActivity = new BusquedaAvanzadaActivity();
        injectMembers(busquedaAvanzadaActivity);
        return busquedaAvanzadaActivity;
    }

    public void injectMembers(BusquedaAvanzadaActivity busquedaAvanzadaActivity) {
        busquedaAvanzadaActivity.x = (Bus) this.a.get();
        busquedaAvanzadaActivity.y = (SessionManager) this.b.get();
        busquedaAvanzadaActivity.z = (IDataManager) this.c.get();
        busquedaAvanzadaActivity.A = (AnalyticsManager) this.d.get();
        this.e.injectMembers(busquedaAvanzadaActivity);
    }
}
