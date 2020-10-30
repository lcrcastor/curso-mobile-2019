package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import ar.com.santander.rio.mbanking.app.base.BaseFragment;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;
import javax.inject.Provider;

public final class ProgramaWomenFragment$$InjectAdapter extends Binding<ProgramaWomenFragment> implements MembersInjector<ProgramaWomenFragment>, Provider<ProgramaWomenFragment> {
    private Binding<AnalyticsManager> a;
    private Binding<BaseFragment> b;

    public ProgramaWomenFragment$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.fragments.privado.ProgramaWomenFragment", "members/ar.com.santander.rio.mbanking.app.ui.fragments.privado.ProgramaWomenFragment", false, ProgramaWomenFragment.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager", ProgramaWomenFragment.class, getClass().getClassLoader());
        this.b = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.base.BaseFragment", ProgramaWomenFragment.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
    }

    public ProgramaWomenFragment get() {
        ProgramaWomenFragment programaWomenFragment = new ProgramaWomenFragment();
        injectMembers(programaWomenFragment);
        return programaWomenFragment;
    }

    public void injectMembers(ProgramaWomenFragment programaWomenFragment) {
        programaWomenFragment.a = (AnalyticsManager) this.a.get();
        this.b.injectMembers(programaWomenFragment);
    }
}
