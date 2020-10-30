package ar.com.santander.rio.mbanking.app.ui.activities;

import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.managers.security.SoftTokenManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import com.squareup.otto.Bus;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;
import javax.inject.Provider;

public final class ActivityEnvioDineroNuevoContacto$$InjectAdapter extends Binding<ActivityEnvioDineroNuevoContacto> implements MembersInjector<ActivityEnvioDineroNuevoContacto>, Provider<ActivityEnvioDineroNuevoContacto> {
    private Binding<IDataManager> a;
    private Binding<Bus> b;
    private Binding<SessionManager> c;
    private Binding<SoftTokenManager> d;
    private Binding<AnalyticsManager> e;
    private Binding<BaseActivity> f;

    public ActivityEnvioDineroNuevoContacto$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroNuevoContacto", "members/ar.com.santander.rio.mbanking.app.ui.activities.ActivityEnvioDineroNuevoContacto", false, ActivityEnvioDineroNuevoContacto.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.managers.data.IDataManager", ActivityEnvioDineroNuevoContacto.class, getClass().getClassLoader());
        this.b = linker.requestBinding("com.squareup.otto.Bus", ActivityEnvioDineroNuevoContacto.class, getClass().getClassLoader());
        this.c = linker.requestBinding("ar.com.santander.rio.mbanking.managers.session.SessionManager", ActivityEnvioDineroNuevoContacto.class, getClass().getClassLoader());
        this.d = linker.requestBinding("ar.com.santander.rio.mbanking.managers.security.SoftTokenManager", ActivityEnvioDineroNuevoContacto.class, getClass().getClassLoader());
        this.e = linker.requestBinding("ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager", ActivityEnvioDineroNuevoContacto.class, getClass().getClassLoader());
        this.f = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.base.BaseActivity", ActivityEnvioDineroNuevoContacto.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
        set2.add(this.c);
        set2.add(this.d);
        set2.add(this.e);
        set2.add(this.f);
    }

    public ActivityEnvioDineroNuevoContacto get() {
        ActivityEnvioDineroNuevoContacto activityEnvioDineroNuevoContacto = new ActivityEnvioDineroNuevoContacto();
        injectMembers(activityEnvioDineroNuevoContacto);
        return activityEnvioDineroNuevoContacto;
    }

    public void injectMembers(ActivityEnvioDineroNuevoContacto activityEnvioDineroNuevoContacto) {
        activityEnvioDineroNuevoContacto.p = (IDataManager) this.a.get();
        activityEnvioDineroNuevoContacto.q = (Bus) this.b.get();
        activityEnvioDineroNuevoContacto.r = (SessionManager) this.c.get();
        activityEnvioDineroNuevoContacto.s = (SoftTokenManager) this.d.get();
        activityEnvioDineroNuevoContacto.t = (AnalyticsManager) this.e.get();
        this.f.injectMembers(activityEnvioDineroNuevoContacto);
    }
}
