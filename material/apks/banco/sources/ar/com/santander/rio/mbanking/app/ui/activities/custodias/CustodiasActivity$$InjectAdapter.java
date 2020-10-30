package ar.com.santander.rio.mbanking.app.ui.activities.custodias;

import ar.com.santander.rio.mbanking.app.base.BaseMvpActivity;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;
import javax.inject.Provider;

public final class CustodiasActivity$$InjectAdapter extends Binding<CustodiasActivity> implements MembersInjector<CustodiasActivity>, Provider<CustodiasActivity> {
    private Binding<SessionManager> a;
    private Binding<AnalyticsManager> b;
    private Binding<BaseMvpActivity> c;

    public CustodiasActivity$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.activities.custodias.CustodiasActivity", "members/ar.com.santander.rio.mbanking.app.ui.activities.custodias.CustodiasActivity", false, CustodiasActivity.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.managers.session.SessionManager", CustodiasActivity.class, getClass().getClassLoader());
        this.b = linker.requestBinding("ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager", CustodiasActivity.class, getClass().getClassLoader());
        this.c = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.base.BaseMvpActivity", CustodiasActivity.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
        set2.add(this.c);
    }

    public CustodiasActivity get() {
        CustodiasActivity custodiasActivity = new CustodiasActivity();
        injectMembers(custodiasActivity);
        return custodiasActivity;
    }

    public void injectMembers(CustodiasActivity custodiasActivity) {
        custodiasActivity.p = (SessionManager) this.a.get();
        custodiasActivity.analyticsManager = (AnalyticsManager) this.b.get();
        this.c.injectMembers(custodiasActivity);
    }
}
