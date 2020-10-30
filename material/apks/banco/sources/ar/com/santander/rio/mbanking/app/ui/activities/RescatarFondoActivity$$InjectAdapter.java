package ar.com.santander.rio.mbanking.app.ui.activities;

import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;
import javax.inject.Provider;

public final class RescatarFondoActivity$$InjectAdapter extends Binding<RescatarFondoActivity> implements MembersInjector<RescatarFondoActivity>, Provider<RescatarFondoActivity> {
    private Binding<AnalyticsManager> a;
    private Binding<MvpPrivateMenuActivity> b;

    public RescatarFondoActivity$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.activities.RescatarFondoActivity", "members/ar.com.santander.rio.mbanking.app.ui.activities.RescatarFondoActivity", false, RescatarFondoActivity.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager", RescatarFondoActivity.class, getClass().getClassLoader());
        this.b = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.ui.activities.MvpPrivateMenuActivity", RescatarFondoActivity.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
    }

    public RescatarFondoActivity get() {
        RescatarFondoActivity rescatarFondoActivity = new RescatarFondoActivity();
        injectMembers(rescatarFondoActivity);
        return rescatarFondoActivity;
    }

    public void injectMembers(RescatarFondoActivity rescatarFondoActivity) {
        rescatarFondoActivity.p = (AnalyticsManager) this.a.get();
        this.b.injectMembers(rescatarFondoActivity);
    }
}
