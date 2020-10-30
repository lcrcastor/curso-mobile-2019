package ar.com.santander.rio.mbanking.app.ui.activities;

import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;
import javax.inject.Provider;

public final class BajaAliasActivity$$InjectAdapter extends Binding<BajaAliasActivity> implements MembersInjector<BajaAliasActivity>, Provider<BajaAliasActivity> {
    private Binding<AnalyticsManager> a;
    private Binding<MvpPrivateMenuActivity> b;

    public BajaAliasActivity$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.activities.BajaAliasActivity", "members/ar.com.santander.rio.mbanking.app.ui.activities.BajaAliasActivity", false, BajaAliasActivity.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager", BajaAliasActivity.class, getClass().getClassLoader());
        this.b = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.ui.activities.MvpPrivateMenuActivity", BajaAliasActivity.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
    }

    public BajaAliasActivity get() {
        BajaAliasActivity bajaAliasActivity = new BajaAliasActivity();
        injectMembers(bajaAliasActivity);
        return bajaAliasActivity;
    }

    public void injectMembers(BajaAliasActivity bajaAliasActivity) {
        bajaAliasActivity.p = (AnalyticsManager) this.a.get();
        this.b.injectMembers(bajaAliasActivity);
    }
}
