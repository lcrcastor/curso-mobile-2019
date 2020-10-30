package ar.com.santander.rio.mbanking.app.ui.activities;

import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.preferences.SettingsManager;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;
import javax.inject.Provider;

public final class SeguroMovilActivity$$InjectAdapter extends Binding<SeguroMovilActivity> implements MembersInjector<SeguroMovilActivity>, Provider<SeguroMovilActivity> {
    private Binding<AnalyticsManager> a;
    private Binding<SettingsManager> b;
    private Binding<SettingsManager> c;
    private Binding<MvpPrivateMenuActivity> d;

    public SeguroMovilActivity$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.activities.SeguroMovilActivity", "members/ar.com.santander.rio.mbanking.app.ui.activities.SeguroMovilActivity", false, SeguroMovilActivity.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager", SeguroMovilActivity.class, getClass().getClassLoader());
        this.b = linker.requestBinding("ar.com.santander.rio.mbanking.managers.preferences.SettingsManager", SeguroMovilActivity.class, getClass().getClassLoader());
        this.c = linker.requestBinding("ar.com.santander.rio.mbanking.managers.preferences.SettingsManager", SeguroMovilActivity.class, getClass().getClassLoader());
        this.d = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.ui.activities.MvpPrivateMenuActivity", SeguroMovilActivity.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
        set2.add(this.c);
        set2.add(this.d);
    }

    public SeguroMovilActivity get() {
        SeguroMovilActivity seguroMovilActivity = new SeguroMovilActivity();
        injectMembers(seguroMovilActivity);
        return seguroMovilActivity;
    }

    public void injectMembers(SeguroMovilActivity seguroMovilActivity) {
        seguroMovilActivity.p = (AnalyticsManager) this.a.get();
        seguroMovilActivity.q = (SettingsManager) this.b.get();
        seguroMovilActivity.w = (SettingsManager) this.c.get();
        this.d.injectMembers(seguroMovilActivity);
    }
}
