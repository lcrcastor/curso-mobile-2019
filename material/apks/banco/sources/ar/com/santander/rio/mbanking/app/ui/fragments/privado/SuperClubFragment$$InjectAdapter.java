package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import ar.com.santander.rio.mbanking.app.base.BaseMvpFragment;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;
import javax.inject.Provider;

public final class SuperClubFragment$$InjectAdapter extends Binding<SuperClubFragment> implements MembersInjector<SuperClubFragment>, Provider<SuperClubFragment> {
    private Binding<SessionManager> a;
    private Binding<AnalyticsManager> b;
    private Binding<BaseMvpFragment> c;

    public SuperClubFragment$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.fragments.privado.SuperClubFragment", "members/ar.com.santander.rio.mbanking.app.ui.fragments.privado.SuperClubFragment", false, SuperClubFragment.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.managers.session.SessionManager", SuperClubFragment.class, getClass().getClassLoader());
        this.b = linker.requestBinding("ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager", SuperClubFragment.class, getClass().getClassLoader());
        this.c = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.base.BaseMvpFragment", SuperClubFragment.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
        set2.add(this.c);
    }

    public SuperClubFragment get() {
        SuperClubFragment superClubFragment = new SuperClubFragment();
        injectMembers(superClubFragment);
        return superClubFragment;
    }

    public void injectMembers(SuperClubFragment superClubFragment) {
        superClubFragment.a = (SessionManager) this.a.get();
        superClubFragment.b = (AnalyticsManager) this.b.get();
        this.c.injectMembers(superClubFragment);
    }
}
