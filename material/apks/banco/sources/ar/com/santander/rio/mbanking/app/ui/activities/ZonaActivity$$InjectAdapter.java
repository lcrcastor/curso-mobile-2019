package ar.com.santander.rio.mbanking.app.ui.activities;

import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;
import javax.inject.Provider;

public final class ZonaActivity$$InjectAdapter extends Binding<ZonaActivity> implements MembersInjector<ZonaActivity>, Provider<ZonaActivity> {
    private Binding<SessionManager> a;
    private Binding<AnalyticsManager> b;
    private Binding<BaseActivity> c;

    public ZonaActivity$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.activities.ZonaActivity", "members/ar.com.santander.rio.mbanking.app.ui.activities.ZonaActivity", false, ZonaActivity.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.managers.session.SessionManager", ZonaActivity.class, getClass().getClassLoader());
        this.b = linker.requestBinding("ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager", ZonaActivity.class, getClass().getClassLoader());
        this.c = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.base.BaseActivity", ZonaActivity.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
        set2.add(this.c);
    }

    public ZonaActivity get() {
        ZonaActivity zonaActivity = new ZonaActivity();
        injectMembers(zonaActivity);
        return zonaActivity;
    }

    public void injectMembers(ZonaActivity zonaActivity) {
        zonaActivity.p = (SessionManager) this.a.get();
        zonaActivity.q = (AnalyticsManager) this.b.get();
        this.c.injectMembers(zonaActivity);
    }
}
