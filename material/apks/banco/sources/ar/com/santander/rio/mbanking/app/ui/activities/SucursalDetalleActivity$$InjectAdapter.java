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

public final class SucursalDetalleActivity$$InjectAdapter extends Binding<SucursalDetalleActivity> implements MembersInjector<SucursalDetalleActivity>, Provider<SucursalDetalleActivity> {
    private Binding<IDataManager> a;
    private Binding<SessionManager> b;
    private Binding<Bus> c;
    private Binding<AnalyticsManager> d;
    private Binding<BaseActivity> e;

    public SucursalDetalleActivity$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.activities.SucursalDetalleActivity", "members/ar.com.santander.rio.mbanking.app.ui.activities.SucursalDetalleActivity", false, SucursalDetalleActivity.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.managers.data.IDataManager", SucursalDetalleActivity.class, getClass().getClassLoader());
        this.b = linker.requestBinding("ar.com.santander.rio.mbanking.managers.session.SessionManager", SucursalDetalleActivity.class, getClass().getClassLoader());
        this.c = linker.requestBinding("com.squareup.otto.Bus", SucursalDetalleActivity.class, getClass().getClassLoader());
        this.d = linker.requestBinding("ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager", SucursalDetalleActivity.class, getClass().getClassLoader());
        this.e = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.base.BaseActivity", SucursalDetalleActivity.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
        set2.add(this.c);
        set2.add(this.d);
        set2.add(this.e);
    }

    public SucursalDetalleActivity get() {
        SucursalDetalleActivity sucursalDetalleActivity = new SucursalDetalleActivity();
        injectMembers(sucursalDetalleActivity);
        return sucursalDetalleActivity;
    }

    public void injectMembers(SucursalDetalleActivity sucursalDetalleActivity) {
        sucursalDetalleActivity.p = (IDataManager) this.a.get();
        sucursalDetalleActivity.q = (SessionManager) this.b.get();
        sucursalDetalleActivity.r = (Bus) this.c.get();
        sucursalDetalleActivity.s = (AnalyticsManager) this.d.get();
        this.e.injectMembers(sucursalDetalleActivity);
    }
}
