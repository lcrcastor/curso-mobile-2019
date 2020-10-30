package ar.com.santander.rio.mbanking.app.ui.activities;

import ar.com.santander.rio.mbanking.app.base.BaseMvpActivity;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;
import javax.inject.Provider;

public final class ContratarSeguroActivity$$InjectAdapter extends Binding<ContratarSeguroActivity> implements MembersInjector<ContratarSeguroActivity>, Provider<ContratarSeguroActivity> {
    private Binding<AnalyticsManager> a;
    private Binding<SessionManager> b;
    private Binding<BaseMvpActivity> c;

    public ContratarSeguroActivity$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.activities.ContratarSeguroActivity", "members/ar.com.santander.rio.mbanking.app.ui.activities.ContratarSeguroActivity", false, ContratarSeguroActivity.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager", ContratarSeguroActivity.class, getClass().getClassLoader());
        this.b = linker.requestBinding("ar.com.santander.rio.mbanking.managers.session.SessionManager", ContratarSeguroActivity.class, getClass().getClassLoader());
        this.c = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.base.BaseMvpActivity", ContratarSeguroActivity.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
        set2.add(this.c);
    }

    public ContratarSeguroActivity get() {
        ContratarSeguroActivity contratarSeguroActivity = new ContratarSeguroActivity();
        injectMembers(contratarSeguroActivity);
        return contratarSeguroActivity;
    }

    public void injectMembers(ContratarSeguroActivity contratarSeguroActivity) {
        contratarSeguroActivity.analyticsManager = (AnalyticsManager) this.a.get();
        contratarSeguroActivity.sessionManager = (SessionManager) this.b.get();
        this.c.injectMembers(contratarSeguroActivity);
    }
}
