package ar.com.santander.rio.mbanking.app.ui.activities;

import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;
import javax.inject.Provider;

public final class DetalleFondoActivity$$InjectAdapter extends Binding<DetalleFondoActivity> implements MembersInjector<DetalleFondoActivity>, Provider<DetalleFondoActivity> {
    private Binding<AnalyticsManager> a;
    private Binding<MvpPrivateMenuActivity> b;

    public DetalleFondoActivity$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.activities.DetalleFondoActivity", "members/ar.com.santander.rio.mbanking.app.ui.activities.DetalleFondoActivity", false, DetalleFondoActivity.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager", DetalleFondoActivity.class, getClass().getClassLoader());
        this.b = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.ui.activities.MvpPrivateMenuActivity", DetalleFondoActivity.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
    }

    public DetalleFondoActivity get() {
        DetalleFondoActivity detalleFondoActivity = new DetalleFondoActivity();
        injectMembers(detalleFondoActivity);
        return detalleFondoActivity;
    }

    public void injectMembers(DetalleFondoActivity detalleFondoActivity) {
        detalleFondoActivity.p = (AnalyticsManager) this.a.get();
        this.b.injectMembers(detalleFondoActivity);
    }
}
