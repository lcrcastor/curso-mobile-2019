package ar.com.santander.rio.mbanking.app.ui.activities;

import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import com.squareup.otto.Bus;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;
import javax.inject.Provider;

public final class ActivityEnvioDineroPrepararEnvio$$InjectAdapter extends Binding<ActivityEnvioDineroPrepararEnvio> implements MembersInjector<ActivityEnvioDineroPrepararEnvio>, Provider<ActivityEnvioDineroPrepararEnvio> {
    private Binding<Bus> a;
    private Binding<IDataManager> b;
    private Binding<AnalyticsManager> c;
    private Binding<BaseActivity> d;

    public ActivityEnvioDineroPrepararEnvio$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroPrepararEnvio", "members/ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroPrepararEnvio", false, ActivityEnvioDineroPrepararEnvio.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("com.squareup.otto.Bus", ActivityEnvioDineroPrepararEnvio.class, getClass().getClassLoader());
        this.b = linker.requestBinding("ar.com.santander.rio.mbanking.managers.data.IDataManager", ActivityEnvioDineroPrepararEnvio.class, getClass().getClassLoader());
        this.c = linker.requestBinding("ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager", ActivityEnvioDineroPrepararEnvio.class, getClass().getClassLoader());
        this.d = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.base.BaseActivity", ActivityEnvioDineroPrepararEnvio.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
        set2.add(this.c);
        set2.add(this.d);
    }

    public ActivityEnvioDineroPrepararEnvio get() {
        ActivityEnvioDineroPrepararEnvio activityEnvioDineroPrepararEnvio = new ActivityEnvioDineroPrepararEnvio();
        injectMembers(activityEnvioDineroPrepararEnvio);
        return activityEnvioDineroPrepararEnvio;
    }

    public void injectMembers(ActivityEnvioDineroPrepararEnvio activityEnvioDineroPrepararEnvio) {
        activityEnvioDineroPrepararEnvio.p = (Bus) this.a.get();
        activityEnvioDineroPrepararEnvio.q = (IDataManager) this.b.get();
        activityEnvioDineroPrepararEnvio.r = (AnalyticsManager) this.c.get();
        this.d.injectMembers(activityEnvioDineroPrepararEnvio);
    }
}
