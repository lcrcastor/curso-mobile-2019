package ar.com.santander.rio.mbanking.app.ui.activities;

import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.managers.notifications.PushNotificationsManager;
import ar.com.santander.rio.mbanking.managers.preferences.SettingsManager;
import ar.com.santander.rio.mbanking.managers.security.SoftTokenManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import com.squareup.otto.Bus;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;
import javax.inject.Provider;

public final class SantanderRioMainActivity$$InjectAdapter extends Binding<SantanderRioMainActivity> implements MembersInjector<SantanderRioMainActivity>, Provider<SantanderRioMainActivity> {
    private Binding<SessionManager> a;
    private Binding<Bus> b;
    private Binding<IDataManager> c;
    private Binding<SettingsManager> d;
    private Binding<PushNotificationsManager> e;
    private Binding<AnalyticsManager> f;
    private Binding<SoftTokenManager> g;
    private Binding<BaseActivity> h;

    public SantanderRioMainActivity$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity", "members/ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity", false, SantanderRioMainActivity.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.managers.session.SessionManager", SantanderRioMainActivity.class, getClass().getClassLoader());
        this.b = linker.requestBinding("com.squareup.otto.Bus", SantanderRioMainActivity.class, getClass().getClassLoader());
        this.c = linker.requestBinding("ar.com.santander.rio.mbanking.managers.data.IDataManager", SantanderRioMainActivity.class, getClass().getClassLoader());
        this.d = linker.requestBinding("ar.com.santander.rio.mbanking.managers.preferences.SettingsManager", SantanderRioMainActivity.class, getClass().getClassLoader());
        this.e = linker.requestBinding("ar.com.santander.rio.mbanking.managers.notifications.PushNotificationsManager", SantanderRioMainActivity.class, getClass().getClassLoader());
        this.f = linker.requestBinding("ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager", SantanderRioMainActivity.class, getClass().getClassLoader());
        this.g = linker.requestBinding("ar.com.santander.rio.mbanking.managers.security.SoftTokenManager", SantanderRioMainActivity.class, getClass().getClassLoader());
        this.h = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.base.BaseActivity", SantanderRioMainActivity.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
        set2.add(this.c);
        set2.add(this.d);
        set2.add(this.e);
        set2.add(this.f);
        set2.add(this.g);
        set2.add(this.h);
    }

    public SantanderRioMainActivity get() {
        SantanderRioMainActivity santanderRioMainActivity = new SantanderRioMainActivity();
        injectMembers(santanderRioMainActivity);
        return santanderRioMainActivity;
    }

    public void injectMembers(SantanderRioMainActivity santanderRioMainActivity) {
        santanderRioMainActivity.sessionManager = (SessionManager) this.a.get();
        santanderRioMainActivity.p = (Bus) this.b.get();
        santanderRioMainActivity.q = (IDataManager) this.c.get();
        santanderRioMainActivity.r = (SettingsManager) this.d.get();
        santanderRioMainActivity.s = (PushNotificationsManager) this.e.get();
        santanderRioMainActivity.t = (AnalyticsManager) this.f.get();
        santanderRioMainActivity.u = (SoftTokenManager) this.g.get();
        this.h.injectMembers(santanderRioMainActivity);
    }
}
