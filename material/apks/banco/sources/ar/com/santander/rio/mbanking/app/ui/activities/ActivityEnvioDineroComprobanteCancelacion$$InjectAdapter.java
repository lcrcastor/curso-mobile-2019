package ar.com.santander.rio.mbanking.app.ui.activities;

import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;
import javax.inject.Provider;

public final class ActivityEnvioDineroComprobanteCancelacion$$InjectAdapter extends Binding<ActivityEnvioDineroComprobanteCancelacion> implements MembersInjector<ActivityEnvioDineroComprobanteCancelacion>, Provider<ActivityEnvioDineroComprobanteCancelacion> {
    private Binding<AnalyticsManager> a;
    private Binding<PrivateMenuActivity> b;

    public ActivityEnvioDineroComprobanteCancelacion$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroComprobanteCancelacion", "members/ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroComprobanteCancelacion", false, ActivityEnvioDineroComprobanteCancelacion.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager", ActivityEnvioDineroComprobanteCancelacion.class, getClass().getClassLoader());
        this.b = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.ui.activities.PrivateMenuActivity", ActivityEnvioDineroComprobanteCancelacion.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
    }

    public ActivityEnvioDineroComprobanteCancelacion get() {
        ActivityEnvioDineroComprobanteCancelacion activityEnvioDineroComprobanteCancelacion = new ActivityEnvioDineroComprobanteCancelacion();
        injectMembers(activityEnvioDineroComprobanteCancelacion);
        return activityEnvioDineroComprobanteCancelacion;
    }

    public void injectMembers(ActivityEnvioDineroComprobanteCancelacion activityEnvioDineroComprobanteCancelacion) {
        activityEnvioDineroComprobanteCancelacion.p = (AnalyticsManager) this.a.get();
        this.b.injectMembers(activityEnvioDineroComprobanteCancelacion);
    }
}
