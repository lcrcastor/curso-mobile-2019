package ar.com.santander.rio.mbanking.app.ui.activities;

import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;
import javax.inject.Provider;

public final class ActivityEnvioDineroComprobanteEnvio$$InjectAdapter extends Binding<ActivityEnvioDineroComprobanteEnvio> implements MembersInjector<ActivityEnvioDineroComprobanteEnvio>, Provider<ActivityEnvioDineroComprobanteEnvio> {
    private Binding<AnalyticsManager> a;
    private Binding<PrivateMenuActivity> b;

    public ActivityEnvioDineroComprobanteEnvio$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroComprobanteEnvio", "members/ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroComprobanteEnvio", false, ActivityEnvioDineroComprobanteEnvio.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager", ActivityEnvioDineroComprobanteEnvio.class, getClass().getClassLoader());
        this.b = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.ui.activities.PrivateMenuActivity", ActivityEnvioDineroComprobanteEnvio.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
    }

    public ActivityEnvioDineroComprobanteEnvio get() {
        ActivityEnvioDineroComprobanteEnvio activityEnvioDineroComprobanteEnvio = new ActivityEnvioDineroComprobanteEnvio();
        injectMembers(activityEnvioDineroComprobanteEnvio);
        return activityEnvioDineroComprobanteEnvio;
    }

    public void injectMembers(ActivityEnvioDineroComprobanteEnvio activityEnvioDineroComprobanteEnvio) {
        activityEnvioDineroComprobanteEnvio.p = (AnalyticsManager) this.a.get();
        this.b.injectMembers(activityEnvioDineroComprobanteEnvio);
    }
}
