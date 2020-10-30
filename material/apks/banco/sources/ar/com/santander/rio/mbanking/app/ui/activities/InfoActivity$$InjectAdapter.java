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

public final class InfoActivity$$InjectAdapter extends Binding<InfoActivity> implements MembersInjector<InfoActivity>, Provider<InfoActivity> {
    private Binding<Bus> a;
    private Binding<IDataManager> b;
    private Binding<SessionManager> c;
    private Binding<AnalyticsManager> d;
    private Binding<BaseActivity> e;

    public InfoActivity$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.activities.InfoActivity", "members/ar.com.santander.rio.mbanking.app.ui.activities.InfoActivity", false, InfoActivity.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("com.squareup.otto.Bus", InfoActivity.class, getClass().getClassLoader());
        this.b = linker.requestBinding("ar.com.santander.rio.mbanking.managers.data.IDataManager", InfoActivity.class, getClass().getClassLoader());
        this.c = linker.requestBinding("ar.com.santander.rio.mbanking.managers.session.SessionManager", InfoActivity.class, getClass().getClassLoader());
        this.d = linker.requestBinding("ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager", InfoActivity.class, getClass().getClassLoader());
        this.e = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.base.BaseActivity", InfoActivity.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
        set2.add(this.c);
        set2.add(this.d);
        set2.add(this.e);
    }

    public InfoActivity get() {
        InfoActivity infoActivity = new InfoActivity();
        injectMembers(infoActivity);
        return infoActivity;
    }

    public void injectMembers(InfoActivity infoActivity) {
        infoActivity.p = (Bus) this.a.get();
        infoActivity.q = (IDataManager) this.b.get();
        infoActivity.r = (SessionManager) this.c.get();
        infoActivity.s = (AnalyticsManager) this.d.get();
        this.e.injectMembers(infoActivity);
    }
}
