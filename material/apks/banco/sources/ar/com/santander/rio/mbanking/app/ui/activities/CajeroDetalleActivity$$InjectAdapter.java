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

public final class CajeroDetalleActivity$$InjectAdapter extends Binding<CajeroDetalleActivity> implements MembersInjector<CajeroDetalleActivity>, Provider<CajeroDetalleActivity> {
    private Binding<IDataManager> a;
    private Binding<SessionManager> b;
    private Binding<AnalyticsManager> c;
    private Binding<Bus> d;
    private Binding<BaseActivity> e;

    public CajeroDetalleActivity$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.activities.CajeroDetalleActivity", "members/ar.com.santander.rio.mbanking.app.ui.activities.CajeroDetalleActivity", false, CajeroDetalleActivity.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.managers.data.IDataManager", CajeroDetalleActivity.class, getClass().getClassLoader());
        this.b = linker.requestBinding("ar.com.santander.rio.mbanking.managers.session.SessionManager", CajeroDetalleActivity.class, getClass().getClassLoader());
        this.c = linker.requestBinding("ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager", CajeroDetalleActivity.class, getClass().getClassLoader());
        this.d = linker.requestBinding("com.squareup.otto.Bus", CajeroDetalleActivity.class, getClass().getClassLoader());
        this.e = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.base.BaseActivity", CajeroDetalleActivity.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
        set2.add(this.c);
        set2.add(this.d);
        set2.add(this.e);
    }

    public CajeroDetalleActivity get() {
        CajeroDetalleActivity cajeroDetalleActivity = new CajeroDetalleActivity();
        injectMembers(cajeroDetalleActivity);
        return cajeroDetalleActivity;
    }

    public void injectMembers(CajeroDetalleActivity cajeroDetalleActivity) {
        cajeroDetalleActivity.p = (IDataManager) this.a.get();
        cajeroDetalleActivity.q = (SessionManager) this.b.get();
        cajeroDetalleActivity.r = (AnalyticsManager) this.c.get();
        cajeroDetalleActivity.s = (Bus) this.d.get();
        this.e.injectMembers(cajeroDetalleActivity);
    }
}
