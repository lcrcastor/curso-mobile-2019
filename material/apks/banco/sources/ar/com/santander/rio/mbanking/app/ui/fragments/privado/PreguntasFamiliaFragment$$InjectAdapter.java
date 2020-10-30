package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import ar.com.santander.rio.mbanking.app.base.BaseFragment;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;
import javax.inject.Provider;

public final class PreguntasFamiliaFragment$$InjectAdapter extends Binding<PreguntasFamiliaFragment> implements MembersInjector<PreguntasFamiliaFragment>, Provider<PreguntasFamiliaFragment> {
    private Binding<IDataManager> a;
    private Binding<AnalyticsManager> b;
    private Binding<BaseFragment> c;

    public PreguntasFamiliaFragment$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.fragments.privado.PreguntasFamiliaFragment", "members/ar.com.santander.rio.mbanking.app.ui.fragments.privado.PreguntasFamiliaFragment", false, PreguntasFamiliaFragment.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.managers.data.IDataManager", PreguntasFamiliaFragment.class, getClass().getClassLoader());
        this.b = linker.requestBinding("ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager", PreguntasFamiliaFragment.class, getClass().getClassLoader());
        this.c = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.base.BaseFragment", PreguntasFamiliaFragment.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
        set2.add(this.c);
    }

    public PreguntasFamiliaFragment get() {
        PreguntasFamiliaFragment preguntasFamiliaFragment = new PreguntasFamiliaFragment();
        injectMembers(preguntasFamiliaFragment);
        return preguntasFamiliaFragment;
    }

    public void injectMembers(PreguntasFamiliaFragment preguntasFamiliaFragment) {
        preguntasFamiliaFragment.mDataManager = (IDataManager) this.a.get();
        preguntasFamiliaFragment.analyticsManager = (AnalyticsManager) this.b.get();
        this.c.injectMembers(preguntasFamiliaFragment);
    }
}
