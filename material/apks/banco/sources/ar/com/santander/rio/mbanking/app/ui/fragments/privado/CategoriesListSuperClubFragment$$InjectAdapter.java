package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import ar.com.santander.rio.mbanking.app.base.BaseMvpFragment;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;
import javax.inject.Provider;

public final class CategoriesListSuperClubFragment$$InjectAdapter extends Binding<CategoriesListSuperClubFragment> implements MembersInjector<CategoriesListSuperClubFragment>, Provider<CategoriesListSuperClubFragment> {
    private Binding<AnalyticsManager> a;
    private Binding<BaseMvpFragment> b;

    public CategoriesListSuperClubFragment$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.fragments.privado.CategoriesListSuperClubFragment", "members/ar.com.santander.rio.mbanking.app.ui.fragments.privado.CategoriesListSuperClubFragment", false, CategoriesListSuperClubFragment.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager", CategoriesListSuperClubFragment.class, getClass().getClassLoader());
        this.b = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.base.BaseMvpFragment", CategoriesListSuperClubFragment.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
    }

    public CategoriesListSuperClubFragment get() {
        CategoriesListSuperClubFragment categoriesListSuperClubFragment = new CategoriesListSuperClubFragment();
        injectMembers(categoriesListSuperClubFragment);
        return categoriesListSuperClubFragment;
    }

    public void injectMembers(CategoriesListSuperClubFragment categoriesListSuperClubFragment) {
        categoriesListSuperClubFragment.a = (AnalyticsManager) this.a.get();
        this.b.injectMembers(categoriesListSuperClubFragment);
    }
}
