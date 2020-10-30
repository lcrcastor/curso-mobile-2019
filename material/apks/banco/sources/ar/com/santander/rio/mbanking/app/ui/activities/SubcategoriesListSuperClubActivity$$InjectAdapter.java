package ar.com.santander.rio.mbanking.app.ui.activities;

import ar.com.santander.rio.mbanking.app.base.BaseMvpActivity;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;
import javax.inject.Provider;

public final class SubcategoriesListSuperClubActivity$$InjectAdapter extends Binding<SubcategoriesListSuperClubActivity> implements MembersInjector<SubcategoriesListSuperClubActivity>, Provider<SubcategoriesListSuperClubActivity> {
    private Binding<AnalyticsManager> a;
    private Binding<BaseMvpActivity> b;

    public SubcategoriesListSuperClubActivity$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.activities.SubcategoriesListSuperClubActivity", "members/ar.com.santander.rio.mbanking.app.ui.activities.SubcategoriesListSuperClubActivity", false, SubcategoriesListSuperClubActivity.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager", SubcategoriesListSuperClubActivity.class, getClass().getClassLoader());
        this.b = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.base.BaseMvpActivity", SubcategoriesListSuperClubActivity.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
    }

    public SubcategoriesListSuperClubActivity get() {
        SubcategoriesListSuperClubActivity subcategoriesListSuperClubActivity = new SubcategoriesListSuperClubActivity();
        injectMembers(subcategoriesListSuperClubActivity);
        return subcategoriesListSuperClubActivity;
    }

    public void injectMembers(SubcategoriesListSuperClubActivity subcategoriesListSuperClubActivity) {
        subcategoriesListSuperClubActivity.r = (AnalyticsManager) this.a.get();
        this.b.injectMembers(subcategoriesListSuperClubActivity);
    }
}
