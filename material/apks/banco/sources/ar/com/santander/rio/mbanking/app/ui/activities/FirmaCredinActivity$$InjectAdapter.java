package ar.com.santander.rio.mbanking.app.ui.activities;

import ar.com.santander.rio.mbanking.app.base.ITRSABaseActivity;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import com.squareup.otto.Bus;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;
import javax.inject.Provider;

public final class FirmaCredinActivity$$InjectAdapter extends Binding<FirmaCredinActivity> implements MembersInjector<FirmaCredinActivity>, Provider<FirmaCredinActivity> {
    private Binding<IDataManager> a;
    private Binding<SessionManager> b;
    private Binding<AnalyticsManager> c;
    private Binding<Bus> d;
    private Binding<ITRSABaseActivity> e;

    public FirmaCredinActivity$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.activities.FirmaCredinActivity", "members/ar.com.santander.rio.mbanking.app.ui.activities.FirmaCredinActivity", false, FirmaCredinActivity.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.managers.data.IDataManager", FirmaCredinActivity.class, getClass().getClassLoader());
        this.b = linker.requestBinding("ar.com.santander.rio.mbanking.managers.session.SessionManager", FirmaCredinActivity.class, getClass().getClassLoader());
        this.c = linker.requestBinding("ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager", FirmaCredinActivity.class, getClass().getClassLoader());
        this.d = linker.requestBinding("com.squareup.otto.Bus", FirmaCredinActivity.class, getClass().getClassLoader());
        this.e = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.base.ITRSABaseActivity", FirmaCredinActivity.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
        set2.add(this.c);
        set2.add(this.d);
        set2.add(this.e);
    }

    public FirmaCredinActivity get() {
        FirmaCredinActivity firmaCredinActivity = new FirmaCredinActivity();
        injectMembers(firmaCredinActivity);
        return firmaCredinActivity;
    }

    public void injectMembers(FirmaCredinActivity firmaCredinActivity) {
        firmaCredinActivity.p = (IDataManager) this.a.get();
        firmaCredinActivity.q = (SessionManager) this.b.get();
        firmaCredinActivity.r = (AnalyticsManager) this.c.get();
        firmaCredinActivity.t = (Bus) this.d.get();
        this.e.injectMembers(firmaCredinActivity);
    }
}
