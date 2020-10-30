package ar.com.santander.rio.mbanking.app.ui.activities;

import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;
import javax.inject.Provider;

public final class StoresSuperClubActivity$$InjectAdapter extends Binding<StoresSuperClubActivity> implements MembersInjector<StoresSuperClubActivity>, Provider<StoresSuperClubActivity> {
    private Binding<AnalyticsManager> a;
    private Binding<BaseActivity> b;

    public StoresSuperClubActivity$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.activities.StoresSuperClubActivity", "members/ar.com.santander.rio.mbanking.app.ui.activities.StoresSuperClubActivity", false, StoresSuperClubActivity.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager", StoresSuperClubActivity.class, getClass().getClassLoader());
        this.b = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.base.BaseActivity", StoresSuperClubActivity.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
    }

    public StoresSuperClubActivity get() {
        StoresSuperClubActivity storesSuperClubActivity = new StoresSuperClubActivity();
        injectMembers(storesSuperClubActivity);
        return storesSuperClubActivity;
    }

    public void injectMembers(StoresSuperClubActivity storesSuperClubActivity) {
        storesSuperClubActivity.r = (AnalyticsManager) this.a.get();
        this.b.injectMembers(storesSuperClubActivity);
    }
}
