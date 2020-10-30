package ar.com.santander.rio.mbanking.app.ui.activities;

import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;
import javax.inject.Provider;

public final class SuscribirFondoActivity$$InjectAdapter extends Binding<SuscribirFondoActivity> implements MembersInjector<SuscribirFondoActivity>, Provider<SuscribirFondoActivity> {
    private Binding<AnalyticsManager> a;
    private Binding<MvpPrivateMenuActivity> b;

    public SuscribirFondoActivity$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.activities.SuscribirFondoActivity", "members/ar.com.santander.rio.mbanking.app.ui.activities.SuscribirFondoActivity", false, SuscribirFondoActivity.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager", SuscribirFondoActivity.class, getClass().getClassLoader());
        this.b = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.ui.activities.MvpPrivateMenuActivity", SuscribirFondoActivity.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
    }

    public SuscribirFondoActivity get() {
        SuscribirFondoActivity suscribirFondoActivity = new SuscribirFondoActivity();
        injectMembers(suscribirFondoActivity);
        return suscribirFondoActivity;
    }

    public void injectMembers(SuscribirFondoActivity suscribirFondoActivity) {
        suscribirFondoActivity.p = (AnalyticsManager) this.a.get();
        this.b.injectMembers(suscribirFondoActivity);
    }
}
