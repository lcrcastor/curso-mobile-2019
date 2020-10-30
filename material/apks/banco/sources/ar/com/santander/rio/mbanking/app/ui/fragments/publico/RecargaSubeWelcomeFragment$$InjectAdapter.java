package ar.com.santander.rio.mbanking.app.ui.fragments.publico;

import ar.com.santander.rio.mbanking.app.base.BaseFragment;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.security.SoftTokenManager;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;
import javax.inject.Provider;

public final class RecargaSubeWelcomeFragment$$InjectAdapter extends Binding<RecargaSubeWelcomeFragment> implements MembersInjector<RecargaSubeWelcomeFragment>, Provider<RecargaSubeWelcomeFragment> {
    private Binding<SoftTokenManager> a;
    private Binding<AnalyticsManager> b;
    private Binding<BaseFragment> c;

    public RecargaSubeWelcomeFragment$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.fragments.publico.RecargaSubeWelcomeFragment", "members/ar.com.santander.rio.mbanking.app.ui.fragments.publico.RecargaSubeWelcomeFragment", false, RecargaSubeWelcomeFragment.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.managers.security.SoftTokenManager", RecargaSubeWelcomeFragment.class, getClass().getClassLoader());
        this.b = linker.requestBinding("ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager", RecargaSubeWelcomeFragment.class, getClass().getClassLoader());
        this.c = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.base.BaseFragment", RecargaSubeWelcomeFragment.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
        set2.add(this.c);
    }

    public RecargaSubeWelcomeFragment get() {
        RecargaSubeWelcomeFragment recargaSubeWelcomeFragment = new RecargaSubeWelcomeFragment();
        injectMembers(recargaSubeWelcomeFragment);
        return recargaSubeWelcomeFragment;
    }

    public void injectMembers(RecargaSubeWelcomeFragment recargaSubeWelcomeFragment) {
        recargaSubeWelcomeFragment.a = (SoftTokenManager) this.a.get();
        recargaSubeWelcomeFragment.b = (AnalyticsManager) this.b.get();
        this.c.injectMembers(recargaSubeWelcomeFragment);
    }
}
