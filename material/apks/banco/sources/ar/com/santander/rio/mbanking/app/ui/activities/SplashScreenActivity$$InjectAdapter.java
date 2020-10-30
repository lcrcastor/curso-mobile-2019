package ar.com.santander.rio.mbanking.app.ui.activities;

import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.managers.notifications.PushNotificationsManager;
import ar.com.santander.rio.mbanking.managers.preferences.SettingsManager;
import ar.com.santander.rio.mbanking.managers.redessociales.IRedesSocialesManager;
import ar.com.santander.rio.mbanking.managers.security.SoftTokenManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import com.squareup.otto.Bus;
import dagger.Lazy;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;
import javax.inject.Provider;

public final class SplashScreenActivity$$InjectAdapter extends Binding<SplashScreenActivity> implements MembersInjector<SplashScreenActivity>, Provider<SplashScreenActivity> {
    private Binding<Bus> a;
    private Binding<IRedesSocialesManager> b;
    private Binding<Lazy<IDataManager>> c;
    private Binding<AnalyticsManager> d;
    private Binding<SettingsManager> e;
    private Binding<SessionManager> f;
    private Binding<PushNotificationsManager> g;
    private Binding<SoftTokenManager> h;
    private Binding<BaseActivity> i;

    public SplashScreenActivity$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.activities.SplashScreenActivity", "members/ar.com.santander.rio.mbanking.app.ui.activities.SplashScreenActivity", false, SplashScreenActivity.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("com.squareup.otto.Bus", SplashScreenActivity.class, getClass().getClassLoader());
        this.b = linker.requestBinding("ar.com.santander.rio.mbanking.managers.redessociales.IRedesSocialesManager", SplashScreenActivity.class, getClass().getClassLoader());
        this.c = linker.requestBinding("dagger.Lazy<ar.com.santander.rio.mbanking.managers.data.IDataManager>", SplashScreenActivity.class, getClass().getClassLoader());
        this.d = linker.requestBinding("ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager", SplashScreenActivity.class, getClass().getClassLoader());
        this.e = linker.requestBinding("ar.com.santander.rio.mbanking.managers.preferences.SettingsManager", SplashScreenActivity.class, getClass().getClassLoader());
        this.f = linker.requestBinding("ar.com.santander.rio.mbanking.managers.session.SessionManager", SplashScreenActivity.class, getClass().getClassLoader());
        this.g = linker.requestBinding("ar.com.santander.rio.mbanking.managers.notifications.PushNotificationsManager", SplashScreenActivity.class, getClass().getClassLoader());
        this.h = linker.requestBinding("ar.com.santander.rio.mbanking.managers.security.SoftTokenManager", SplashScreenActivity.class, getClass().getClassLoader());
        this.i = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.base.BaseActivity", SplashScreenActivity.class, getClass().getClassLoader(), false, true);
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
        set2.add(this.i);
    }

    public SplashScreenActivity get() {
        SplashScreenActivity splashScreenActivity = new SplashScreenActivity();
        injectMembers(splashScreenActivity);
        return splashScreenActivity;
    }

    public void injectMembers(SplashScreenActivity splashScreenActivity) {
        splashScreenActivity.p = (Bus) this.a.get();
        splashScreenActivity.q = (IRedesSocialesManager) this.b.get();
        splashScreenActivity.r = (Lazy) this.c.get();
        splashScreenActivity.s = (AnalyticsManager) this.d.get();
        splashScreenActivity.t = (SettingsManager) this.e.get();
        splashScreenActivity.u = (SessionManager) this.f.get();
        splashScreenActivity.v = (PushNotificationsManager) this.g.get();
        splashScreenActivity.w = (SoftTokenManager) this.h.get();
        this.i.injectMembers(splashScreenActivity);
    }
}
