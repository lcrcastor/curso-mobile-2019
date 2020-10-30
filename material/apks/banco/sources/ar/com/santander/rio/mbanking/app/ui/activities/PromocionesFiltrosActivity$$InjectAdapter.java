package ar.com.santander.rio.mbanking.app.ui.activities;

import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;
import javax.inject.Provider;

public final class PromocionesFiltrosActivity$$InjectAdapter extends Binding<PromocionesFiltrosActivity> implements MembersInjector<PromocionesFiltrosActivity>, Provider<PromocionesFiltrosActivity> {
    private Binding<SessionManager> a;
    private Binding<AnalyticsManager> b;
    private Binding<BaseActivity> c;

    public PromocionesFiltrosActivity$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.activities.PromocionesFiltrosActivity", "members/ar.com.santander.rio.mbanking.app.ui.activities.PromocionesFiltrosActivity", false, PromocionesFiltrosActivity.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.managers.session.SessionManager", PromocionesFiltrosActivity.class, getClass().getClassLoader());
        this.b = linker.requestBinding("ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager", PromocionesFiltrosActivity.class, getClass().getClassLoader());
        this.c = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.base.BaseActivity", PromocionesFiltrosActivity.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
        set2.add(this.c);
    }

    public PromocionesFiltrosActivity get() {
        PromocionesFiltrosActivity promocionesFiltrosActivity = new PromocionesFiltrosActivity();
        injectMembers(promocionesFiltrosActivity);
        return promocionesFiltrosActivity;
    }

    public void injectMembers(PromocionesFiltrosActivity promocionesFiltrosActivity) {
        promocionesFiltrosActivity.q = (SessionManager) this.a.get();
        promocionesFiltrosActivity.r = (AnalyticsManager) this.b.get();
        this.c.injectMembers(promocionesFiltrosActivity);
    }
}
