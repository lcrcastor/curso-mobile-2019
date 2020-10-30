package ar.com.santander.rio.mbanking.app.ui.activities;

import ar.com.santander.rio.mbanking.app.base.ITRSABaseActivity;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.managers.preferences.SettingsManager;
import ar.com.santander.rio.mbanking.managers.security.SoftTokenManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;
import javax.inject.Provider;

public final class ContratarSeguroAccidenteActivity$$InjectAdapter extends Binding<ContratarSeguroAccidenteActivity> implements MembersInjector<ContratarSeguroAccidenteActivity>, Provider<ContratarSeguroAccidenteActivity> {
    private Binding<AnalyticsManager> a;
    private Binding<SessionManager> b;
    private Binding<SettingsManager> c;
    private Binding<SoftTokenManager> d;
    private Binding<IDataManager> e;
    private Binding<ITRSABaseActivity> f;

    public ContratarSeguroAccidenteActivity$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.activities.ContratarSeguroAccidenteActivity", "members/ar.com.santander.rio.mbanking.app.ui.activities.ContratarSeguroAccidenteActivity", false, ContratarSeguroAccidenteActivity.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager", ContratarSeguroAccidenteActivity.class, getClass().getClassLoader());
        this.b = linker.requestBinding("ar.com.santander.rio.mbanking.managers.session.SessionManager", ContratarSeguroAccidenteActivity.class, getClass().getClassLoader());
        this.c = linker.requestBinding("ar.com.santander.rio.mbanking.managers.preferences.SettingsManager", ContratarSeguroAccidenteActivity.class, getClass().getClassLoader());
        this.d = linker.requestBinding("ar.com.santander.rio.mbanking.managers.security.SoftTokenManager", ContratarSeguroAccidenteActivity.class, getClass().getClassLoader());
        this.e = linker.requestBinding("ar.com.santander.rio.mbanking.managers.data.IDataManager", ContratarSeguroAccidenteActivity.class, getClass().getClassLoader());
        this.f = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.base.ITRSABaseActivity", ContratarSeguroAccidenteActivity.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
        set2.add(this.c);
        set2.add(this.d);
        set2.add(this.e);
        set2.add(this.f);
    }

    public ContratarSeguroAccidenteActivity get() {
        ContratarSeguroAccidenteActivity contratarSeguroAccidenteActivity = new ContratarSeguroAccidenteActivity();
        injectMembers(contratarSeguroAccidenteActivity);
        return contratarSeguroAccidenteActivity;
    }

    public void injectMembers(ContratarSeguroAccidenteActivity contratarSeguroAccidenteActivity) {
        contratarSeguroAccidenteActivity.analyticsManager = (AnalyticsManager) this.a.get();
        contratarSeguroAccidenteActivity.p = (SessionManager) this.b.get();
        contratarSeguroAccidenteActivity.q = (SettingsManager) this.c.get();
        contratarSeguroAccidenteActivity.r = (SoftTokenManager) this.d.get();
        contratarSeguroAccidenteActivity.dataManager = (IDataManager) this.e.get();
        this.f.injectMembers(contratarSeguroAccidenteActivity);
    }
}
