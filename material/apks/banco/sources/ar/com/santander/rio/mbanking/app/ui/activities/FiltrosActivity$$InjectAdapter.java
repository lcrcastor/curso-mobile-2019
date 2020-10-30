package ar.com.santander.rio.mbanking.app.ui.activities;

import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;
import javax.inject.Provider;

public final class FiltrosActivity$$InjectAdapter extends Binding<FiltrosActivity> implements MembersInjector<FiltrosActivity>, Provider<FiltrosActivity> {
    private Binding<SessionManager> a;
    private Binding<AnalyticsManager> b;
    private Binding<BaseActivity> c;

    public FiltrosActivity$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.activities.FiltrosActivity", "members/ar.com.santander.rio.mbanking.app.ui.activities.FiltrosActivity", false, FiltrosActivity.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.managers.session.SessionManager", FiltrosActivity.class, getClass().getClassLoader());
        this.b = linker.requestBinding("ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager", FiltrosActivity.class, getClass().getClassLoader());
        this.c = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.base.BaseActivity", FiltrosActivity.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
        set2.add(this.c);
    }

    public FiltrosActivity get() {
        FiltrosActivity filtrosActivity = new FiltrosActivity();
        injectMembers(filtrosActivity);
        return filtrosActivity;
    }

    public void injectMembers(FiltrosActivity filtrosActivity) {
        filtrosActivity.p = (SessionManager) this.a.get();
        filtrosActivity.q = (AnalyticsManager) this.b.get();
        this.c.injectMembers(filtrosActivity);
    }
}
