package ar.com.santander.rio.mbanking.app.ui.activities.titulosvalores;

import ar.com.santander.rio.mbanking.app.base.BaseMvpActivity;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;
import javax.inject.Provider;

public final class TitulosValoresActivity$$InjectAdapter extends Binding<TitulosValoresActivity> implements MembersInjector<TitulosValoresActivity>, Provider<TitulosValoresActivity> {
    private Binding<SessionManager> a;
    private Binding<AnalyticsManager> b;
    private Binding<BaseMvpActivity> c;

    public TitulosValoresActivity$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.activities.titulosvalores.TitulosValoresActivity", "members/ar.com.santander.rio.mbanking.app.ui.activities.titulosvalores.TitulosValoresActivity", false, TitulosValoresActivity.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.managers.session.SessionManager", TitulosValoresActivity.class, getClass().getClassLoader());
        this.b = linker.requestBinding("ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager", TitulosValoresActivity.class, getClass().getClassLoader());
        this.c = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.base.BaseMvpActivity", TitulosValoresActivity.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
        set2.add(this.c);
    }

    public TitulosValoresActivity get() {
        TitulosValoresActivity titulosValoresActivity = new TitulosValoresActivity();
        injectMembers(titulosValoresActivity);
        return titulosValoresActivity;
    }

    public void injectMembers(TitulosValoresActivity titulosValoresActivity) {
        titulosValoresActivity.p = (SessionManager) this.a.get();
        titulosValoresActivity.analyticsManager = (AnalyticsManager) this.b.get();
        this.c.injectMembers(titulosValoresActivity);
    }
}
