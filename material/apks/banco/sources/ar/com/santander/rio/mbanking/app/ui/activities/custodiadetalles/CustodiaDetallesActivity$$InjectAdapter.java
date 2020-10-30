package ar.com.santander.rio.mbanking.app.ui.activities.custodiadetalles;

import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;
import javax.inject.Provider;

public final class CustodiaDetallesActivity$$InjectAdapter extends Binding<CustodiaDetallesActivity> implements MembersInjector<CustodiaDetallesActivity>, Provider<CustodiaDetallesActivity> {
    private Binding<AnalyticsManager> a;
    private Binding<BaseActivity> b;

    public CustodiaDetallesActivity$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.activities.custodiadetalles.CustodiaDetallesActivity", "members/ar.com.santander.rio.mbanking.app.ui.activities.custodiadetalles.CustodiaDetallesActivity", false, CustodiaDetallesActivity.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager", CustodiaDetallesActivity.class, getClass().getClassLoader());
        this.b = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.base.BaseActivity", CustodiaDetallesActivity.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
    }

    public CustodiaDetallesActivity get() {
        CustodiaDetallesActivity custodiaDetallesActivity = new CustodiaDetallesActivity();
        injectMembers(custodiaDetallesActivity);
        return custodiaDetallesActivity;
    }

    public void injectMembers(CustodiaDetallesActivity custodiaDetallesActivity) {
        custodiaDetallesActivity.analyticsManager = (AnalyticsManager) this.a.get();
        this.b.injectMembers(custodiaDetallesActivity);
    }
}
