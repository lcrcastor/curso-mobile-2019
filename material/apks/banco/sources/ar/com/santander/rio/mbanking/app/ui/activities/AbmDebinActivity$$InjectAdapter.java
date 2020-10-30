package ar.com.santander.rio.mbanking.app.ui.activities;

import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;
import javax.inject.Provider;

public final class AbmDebinActivity$$InjectAdapter extends Binding<AbmDebinActivity> implements MembersInjector<AbmDebinActivity>, Provider<AbmDebinActivity> {
    private Binding<AnalyticsManager> a;
    private Binding<MvpPrivateMenuActivity> b;

    public AbmDebinActivity$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.activities.AbmDebinActivity", "members/ar.com.santander.rio.mbanking.app.ui.activities.AbmDebinActivity", false, AbmDebinActivity.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager", AbmDebinActivity.class, getClass().getClassLoader());
        this.b = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.ui.activities.MvpPrivateMenuActivity", AbmDebinActivity.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
    }

    public AbmDebinActivity get() {
        AbmDebinActivity abmDebinActivity = new AbmDebinActivity();
        injectMembers(abmDebinActivity);
        return abmDebinActivity;
    }

    public void injectMembers(AbmDebinActivity abmDebinActivity) {
        abmDebinActivity.p = (AnalyticsManager) this.a.get();
        this.b.injectMembers(abmDebinActivity);
    }
}
