package ar.com.santander.rio.mbanking.app.ui.activities;

import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.preferences.SettingsManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import com.squareup.otto.Bus;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;
import javax.inject.Provider;

public final class MiUbicacionActivity$$InjectAdapter extends Binding<MiUbicacionActivity> implements MembersInjector<MiUbicacionActivity>, Provider<MiUbicacionActivity> {
    private Binding<SessionManager> a;
    private Binding<SettingsManager> b;
    private Binding<AnalyticsManager> c;
    private Binding<Bus> d;
    private Binding<BaseActivity> e;

    public MiUbicacionActivity$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.activities.MiUbicacionActivity", "members/ar.com.santander.rio.mbanking.app.ui.activities.MiUbicacionActivity", false, MiUbicacionActivity.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.managers.session.SessionManager", MiUbicacionActivity.class, getClass().getClassLoader());
        this.b = linker.requestBinding("ar.com.santander.rio.mbanking.managers.preferences.SettingsManager", MiUbicacionActivity.class, getClass().getClassLoader());
        this.c = linker.requestBinding("ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager", MiUbicacionActivity.class, getClass().getClassLoader());
        this.d = linker.requestBinding("com.squareup.otto.Bus", MiUbicacionActivity.class, getClass().getClassLoader());
        this.e = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.base.BaseActivity", MiUbicacionActivity.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
        set2.add(this.c);
        set2.add(this.d);
        set2.add(this.e);
    }

    public MiUbicacionActivity get() {
        MiUbicacionActivity miUbicacionActivity = new MiUbicacionActivity();
        injectMembers(miUbicacionActivity);
        return miUbicacionActivity;
    }

    public void injectMembers(MiUbicacionActivity miUbicacionActivity) {
        miUbicacionActivity.q = (SessionManager) this.a.get();
        miUbicacionActivity.r = (SettingsManager) this.b.get();
        miUbicacionActivity.s = (AnalyticsManager) this.c.get();
        miUbicacionActivity.t = (Bus) this.d.get();
        this.e.injectMembers(miUbicacionActivity);
    }
}
