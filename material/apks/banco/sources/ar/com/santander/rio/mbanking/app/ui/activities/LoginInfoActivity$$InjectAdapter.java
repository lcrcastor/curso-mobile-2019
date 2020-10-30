package ar.com.santander.rio.mbanking.app.ui.activities;

import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.managers.preferences.SettingsManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import com.squareup.otto.Bus;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;
import javax.inject.Provider;

public final class LoginInfoActivity$$InjectAdapter extends Binding<LoginInfoActivity> implements MembersInjector<LoginInfoActivity>, Provider<LoginInfoActivity> {
    private Binding<AnalyticsManager> a;
    private Binding<Bus> b;
    private Binding<IDataManager> c;
    private Binding<SettingsManager> d;
    private Binding<SessionManager> e;
    private Binding<BaseActivity> f;

    public LoginInfoActivity$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.activities.LoginInfoActivity", "members/ar.com.santander.rio.mbanking.app.ui.activities.LoginInfoActivity", false, LoginInfoActivity.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager", LoginInfoActivity.class, getClass().getClassLoader());
        this.b = linker.requestBinding("com.squareup.otto.Bus", LoginInfoActivity.class, getClass().getClassLoader());
        this.c = linker.requestBinding("ar.com.santander.rio.mbanking.managers.data.IDataManager", LoginInfoActivity.class, getClass().getClassLoader());
        this.d = linker.requestBinding("ar.com.santander.rio.mbanking.managers.preferences.SettingsManager", LoginInfoActivity.class, getClass().getClassLoader());
        this.e = linker.requestBinding("ar.com.santander.rio.mbanking.managers.session.SessionManager", LoginInfoActivity.class, getClass().getClassLoader());
        this.f = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.base.BaseActivity", LoginInfoActivity.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
        set2.add(this.c);
        set2.add(this.d);
        set2.add(this.e);
        set2.add(this.f);
    }

    public LoginInfoActivity get() {
        LoginInfoActivity loginInfoActivity = new LoginInfoActivity();
        injectMembers(loginInfoActivity);
        return loginInfoActivity;
    }

    public void injectMembers(LoginInfoActivity loginInfoActivity) {
        loginInfoActivity.p = (AnalyticsManager) this.a.get();
        loginInfoActivity.q = (Bus) this.b.get();
        loginInfoActivity.r = (IDataManager) this.c.get();
        loginInfoActivity.s = (SettingsManager) this.d.get();
        loginInfoActivity.t = (SessionManager) this.e.get();
        this.f.injectMembers(loginInfoActivity);
    }
}
