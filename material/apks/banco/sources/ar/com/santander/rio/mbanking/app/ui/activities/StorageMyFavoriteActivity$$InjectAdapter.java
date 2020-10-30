package ar.com.santander.rio.mbanking.app.ui.activities;

import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.preferences.StringPreference;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;
import javax.inject.Provider;

public final class StorageMyFavoriteActivity$$InjectAdapter extends Binding<StorageMyFavoriteActivity> implements MembersInjector<StorageMyFavoriteActivity>, Provider<StorageMyFavoriteActivity> {
    private Binding<StringPreference> a;
    private Binding<AnalyticsManager> b;
    private Binding<BaseActivity> c;

    public StorageMyFavoriteActivity$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.activities.StorageMyFavoriteActivity", "members/ar.com.santander.rio.mbanking.app.ui.activities.StorageMyFavoriteActivity", false, StorageMyFavoriteActivity.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("@ar.com.santander.rio.mbanking.inject.qualifiers.FavoritoPreference()/ar.com.santander.rio.mbanking.managers.preferences.StringPreference", StorageMyFavoriteActivity.class, getClass().getClassLoader());
        this.b = linker.requestBinding("ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager", StorageMyFavoriteActivity.class, getClass().getClassLoader());
        this.c = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.base.BaseActivity", StorageMyFavoriteActivity.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
        set2.add(this.c);
    }

    public StorageMyFavoriteActivity get() {
        StorageMyFavoriteActivity storageMyFavoriteActivity = new StorageMyFavoriteActivity();
        injectMembers(storageMyFavoriteActivity);
        return storageMyFavoriteActivity;
    }

    public void injectMembers(StorageMyFavoriteActivity storageMyFavoriteActivity) {
        storageMyFavoriteActivity.p = (StringPreference) this.a.get();
        storageMyFavoriteActivity.r = (AnalyticsManager) this.b.get();
        this.c.injectMembers(storageMyFavoriteActivity);
    }
}
