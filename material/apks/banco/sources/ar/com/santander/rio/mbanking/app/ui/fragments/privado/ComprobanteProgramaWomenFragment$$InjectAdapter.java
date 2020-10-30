package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import ar.com.santander.rio.mbanking.app.base.ITRSABaseFragment;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;
import javax.inject.Provider;

public final class ComprobanteProgramaWomenFragment$$InjectAdapter extends Binding<ComprobanteProgramaWomenFragment> implements MembersInjector<ComprobanteProgramaWomenFragment>, Provider<ComprobanteProgramaWomenFragment> {
    private Binding<AnalyticsManager> a;
    private Binding<ITRSABaseFragment> b;

    public ComprobanteProgramaWomenFragment$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.fragments.privado.ComprobanteProgramaWomenFragment", "members/ar.com.santander.rio.mbanking.app.ui.fragments.privado.ComprobanteProgramaWomenFragment", false, ComprobanteProgramaWomenFragment.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager", ComprobanteProgramaWomenFragment.class, getClass().getClassLoader());
        this.b = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.base.ITRSABaseFragment", ComprobanteProgramaWomenFragment.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
    }

    public ComprobanteProgramaWomenFragment get() {
        ComprobanteProgramaWomenFragment comprobanteProgramaWomenFragment = new ComprobanteProgramaWomenFragment();
        injectMembers(comprobanteProgramaWomenFragment);
        return comprobanteProgramaWomenFragment;
    }

    public void injectMembers(ComprobanteProgramaWomenFragment comprobanteProgramaWomenFragment) {
        comprobanteProgramaWomenFragment.a = (AnalyticsManager) this.a.get();
        this.b.injectMembers(comprobanteProgramaWomenFragment);
    }
}
