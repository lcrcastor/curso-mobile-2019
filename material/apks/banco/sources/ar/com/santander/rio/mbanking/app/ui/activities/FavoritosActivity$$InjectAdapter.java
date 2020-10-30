package ar.com.santander.rio.mbanking.app.ui.activities;

import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.preferences.StringPreference;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;
import javax.inject.Provider;

public final class FavoritosActivity$$InjectAdapter extends Binding<FavoritosActivity> implements MembersInjector<FavoritosActivity>, Provider<FavoritosActivity> {
    private Binding<StringPreference> a;
    private Binding<AnalyticsManager> b;
    private Binding<BaseActivity> c;

    public FavoritosActivity$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.activities.FavoritosActivity", "members/ar.com.santander.rio.mbanking.app.ui.activities.FavoritosActivity", false, FavoritosActivity.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("@ar.com.santander.rio.mbanking.inject.qualifiers.FavoritoPreference()/ar.com.santander.rio.mbanking.managers.preferences.StringPreference", FavoritosActivity.class, getClass().getClassLoader());
        this.b = linker.requestBinding("ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager", FavoritosActivity.class, getClass().getClassLoader());
        this.c = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.base.BaseActivity", FavoritosActivity.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
        set2.add(this.c);
    }

    public FavoritosActivity get() {
        FavoritosActivity favoritosActivity = new FavoritosActivity();
        injectMembers(favoritosActivity);
        return favoritosActivity;
    }

    public void injectMembers(FavoritosActivity favoritosActivity) {
        favoritosActivity.q = (StringPreference) this.a.get();
        favoritosActivity.r = (AnalyticsManager) this.b.get();
        this.c.injectMembers(favoritosActivity);
    }
}
