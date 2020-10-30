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

public final class HomeActivity$$InjectAdapter extends Binding<HomeActivity> implements MembersInjector<HomeActivity>, Provider<HomeActivity> {
    private Binding<Bus> a;
    private Binding<SessionManager> b;
    private Binding<AnalyticsManager> c;
    private Binding<IDataManager> d;
    private Binding<SettingsManager> e;
    private Binding<BaseActivity> f;

    public HomeActivity$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.activities.HomeActivity", "members/ar.com.santander.rio.mbanking.app.ui.activities.HomeActivity", false, HomeActivity.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("com.squareup.otto.Bus", HomeActivity.class, getClass().getClassLoader());
        this.b = linker.requestBinding("ar.com.santander.rio.mbanking.managers.session.SessionManager", HomeActivity.class, getClass().getClassLoader());
        this.c = linker.requestBinding("ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager", HomeActivity.class, getClass().getClassLoader());
        this.d = linker.requestBinding("ar.com.santander.rio.mbanking.managers.data.IDataManager", HomeActivity.class, getClass().getClassLoader());
        this.e = linker.requestBinding("ar.com.santander.rio.mbanking.managers.preferences.SettingsManager", HomeActivity.class, getClass().getClassLoader());
        this.f = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.base.BaseActivity", HomeActivity.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
        set2.add(this.c);
        set2.add(this.d);
        set2.add(this.e);
        set2.add(this.f);
    }

    public HomeActivity get() {
        HomeActivity homeActivity = new HomeActivity();
        injectMembers(homeActivity);
        return homeActivity;
    }

    public void injectMembers(HomeActivity homeActivity) {
        homeActivity.p = (Bus) this.a.get();
        homeActivity.q = (SessionManager) this.b.get();
        homeActivity.r = (AnalyticsManager) this.c.get();
        homeActivity.s = (IDataManager) this.d.get();
        homeActivity.t = (SettingsManager) this.e.get();
        this.f.injectMembers(homeActivity);
    }
}
