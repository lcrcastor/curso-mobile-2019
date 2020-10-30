package ar.com.santander.rio.mbanking.app.ui.activities;

import ar.com.santander.rio.mbanking.app.base.BaseMvpActivity;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;
import javax.inject.Provider;

public final class BuscadorDebinActivity$$InjectAdapter extends Binding<BuscadorDebinActivity> implements MembersInjector<BuscadorDebinActivity>, Provider<BuscadorDebinActivity> {
    private Binding<SessionManager> a;
    private Binding<AnalyticsManager> b;
    private Binding<BaseMvpActivity> c;

    public BuscadorDebinActivity$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.activities.BuscadorDebinActivity", "members/ar.com.santander.rio.mbanking.app.ui.activities.BuscadorDebinActivity", false, BuscadorDebinActivity.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.managers.session.SessionManager", BuscadorDebinActivity.class, getClass().getClassLoader());
        this.b = linker.requestBinding("ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager", BuscadorDebinActivity.class, getClass().getClassLoader());
        this.c = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.base.BaseMvpActivity", BuscadorDebinActivity.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
        set2.add(this.c);
    }

    public BuscadorDebinActivity get() {
        BuscadorDebinActivity buscadorDebinActivity = new BuscadorDebinActivity();
        injectMembers(buscadorDebinActivity);
        return buscadorDebinActivity;
    }

    public void injectMembers(BuscadorDebinActivity buscadorDebinActivity) {
        buscadorDebinActivity.s = (SessionManager) this.a.get();
        buscadorDebinActivity.t = (AnalyticsManager) this.b.get();
        this.c.injectMembers(buscadorDebinActivity);
    }
}
