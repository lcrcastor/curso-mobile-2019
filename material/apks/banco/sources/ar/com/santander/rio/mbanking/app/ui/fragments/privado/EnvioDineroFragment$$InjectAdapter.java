package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import ar.com.santander.rio.mbanking.app.base.BaseFragment;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;
import javax.inject.Provider;

public final class EnvioDineroFragment$$InjectAdapter extends Binding<EnvioDineroFragment> implements MembersInjector<EnvioDineroFragment>, Provider<EnvioDineroFragment> {
    private Binding<IDataManager> a;
    private Binding<SessionManager> b;
    private Binding<AnalyticsManager> c;
    private Binding<BaseFragment> d;

    public EnvioDineroFragment$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.fragments.privado.EnvioDineroFragment", "members/ar.com.santander.rio.mbanking.app.ui.fragments.privado.EnvioDineroFragment", false, EnvioDineroFragment.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.managers.data.IDataManager", EnvioDineroFragment.class, getClass().getClassLoader());
        this.b = linker.requestBinding("ar.com.santander.rio.mbanking.managers.session.SessionManager", EnvioDineroFragment.class, getClass().getClassLoader());
        this.c = linker.requestBinding("ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager", EnvioDineroFragment.class, getClass().getClassLoader());
        this.d = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.base.BaseFragment", EnvioDineroFragment.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
        set2.add(this.c);
        set2.add(this.d);
    }

    public EnvioDineroFragment get() {
        EnvioDineroFragment envioDineroFragment = new EnvioDineroFragment();
        injectMembers(envioDineroFragment);
        return envioDineroFragment;
    }

    public void injectMembers(EnvioDineroFragment envioDineroFragment) {
        envioDineroFragment.i = (IDataManager) this.a.get();
        envioDineroFragment.ad = (SessionManager) this.b.get();
        envioDineroFragment.ae = (AnalyticsManager) this.c.get();
        this.d.injectMembers(envioDineroFragment);
    }
}
