package ar.com.santander.rio.mbanking.app.ui.fragments.publico.recargasubeconfirmacionpago;

import ar.com.santander.rio.mbanking.app.base.BaseMvpFragment;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;
import javax.inject.Provider;

public final class RecargaSubeConfirmacionFragment$$InjectAdapter extends Binding<RecargaSubeConfirmacionFragment> implements MembersInjector<RecargaSubeConfirmacionFragment>, Provider<RecargaSubeConfirmacionFragment> {
    private Binding<AnalyticsManager> a;
    private Binding<BaseMvpFragment> b;

    public RecargaSubeConfirmacionFragment$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.fragments.publico.recargasubeconfirmacionpago.RecargaSubeConfirmacionFragment", "members/ar.com.santander.rio.mbanking.app.ui.fragments.publico.recargasubeconfirmacionpago.RecargaSubeConfirmacionFragment", false, RecargaSubeConfirmacionFragment.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager", RecargaSubeConfirmacionFragment.class, getClass().getClassLoader());
        this.b = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.base.BaseMvpFragment", RecargaSubeConfirmacionFragment.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
    }

    public RecargaSubeConfirmacionFragment get() {
        RecargaSubeConfirmacionFragment recargaSubeConfirmacionFragment = new RecargaSubeConfirmacionFragment();
        injectMembers(recargaSubeConfirmacionFragment);
        return recargaSubeConfirmacionFragment;
    }

    public void injectMembers(RecargaSubeConfirmacionFragment recargaSubeConfirmacionFragment) {
        recargaSubeConfirmacionFragment.a = (AnalyticsManager) this.a.get();
        this.b.injectMembers(recargaSubeConfirmacionFragment);
    }
}
