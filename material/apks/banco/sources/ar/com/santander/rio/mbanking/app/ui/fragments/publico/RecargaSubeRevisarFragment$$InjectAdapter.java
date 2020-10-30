package ar.com.santander.rio.mbanking.app.ui.fragments.publico;

import ar.com.santander.rio.mbanking.app.base.BaseFragment;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.managers.preferences.SettingsManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import com.squareup.otto.Bus;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;
import javax.inject.Provider;

public final class RecargaSubeRevisarFragment$$InjectAdapter extends Binding<RecargaSubeRevisarFragment> implements MembersInjector<RecargaSubeRevisarFragment>, Provider<RecargaSubeRevisarFragment> {
    private Binding<SessionManager> a;
    private Binding<IDataManager> b;
    private Binding<Bus> c;
    private Binding<AnalyticsManager> d;
    private Binding<SettingsManager> e;
    private Binding<BaseFragment> f;

    public RecargaSubeRevisarFragment$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.fragments.publico.RecargaSubeRevisarFragment", "members/ar.com.santander.rio.mbanking.app.ui.fragments.publico.RecargaSubeRevisarFragment", false, RecargaSubeRevisarFragment.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.managers.session.SessionManager", RecargaSubeRevisarFragment.class, getClass().getClassLoader());
        this.b = linker.requestBinding("ar.com.santander.rio.mbanking.managers.data.IDataManager", RecargaSubeRevisarFragment.class, getClass().getClassLoader());
        this.c = linker.requestBinding("com.squareup.otto.Bus", RecargaSubeRevisarFragment.class, getClass().getClassLoader());
        this.d = linker.requestBinding("ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager", RecargaSubeRevisarFragment.class, getClass().getClassLoader());
        this.e = linker.requestBinding("ar.com.santander.rio.mbanking.managers.preferences.SettingsManager", RecargaSubeRevisarFragment.class, getClass().getClassLoader());
        this.f = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.base.BaseFragment", RecargaSubeRevisarFragment.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
        set2.add(this.c);
        set2.add(this.d);
        set2.add(this.e);
        set2.add(this.f);
    }

    public RecargaSubeRevisarFragment get() {
        RecargaSubeRevisarFragment recargaSubeRevisarFragment = new RecargaSubeRevisarFragment();
        injectMembers(recargaSubeRevisarFragment);
        return recargaSubeRevisarFragment;
    }

    public void injectMembers(RecargaSubeRevisarFragment recargaSubeRevisarFragment) {
        recargaSubeRevisarFragment.sessionManager = (SessionManager) this.a.get();
        recargaSubeRevisarFragment.dataManager = (IDataManager) this.b.get();
        recargaSubeRevisarFragment.mBus = (Bus) this.c.get();
        recargaSubeRevisarFragment.a = (AnalyticsManager) this.d.get();
        recargaSubeRevisarFragment.b = (SettingsManager) this.e.get();
        this.f.injectMembers(recargaSubeRevisarFragment);
    }
}
