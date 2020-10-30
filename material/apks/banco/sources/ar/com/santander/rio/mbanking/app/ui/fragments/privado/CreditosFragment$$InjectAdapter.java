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

public final class CreditosFragment$$InjectAdapter extends Binding<CreditosFragment> implements MembersInjector<CreditosFragment>, Provider<CreditosFragment> {
    private Binding<IDataManager> a;
    private Binding<SessionManager> b;
    private Binding<AnalyticsManager> c;
    private Binding<BaseFragment> d;

    public CreditosFragment$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.fragments.privado.CreditosFragment", "members/ar.com.santander.rio.mbanking.app.ui.fragments.privado.CreditosFragment", false, CreditosFragment.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.managers.data.IDataManager", CreditosFragment.class, getClass().getClassLoader());
        this.b = linker.requestBinding("ar.com.santander.rio.mbanking.managers.session.SessionManager", CreditosFragment.class, getClass().getClassLoader());
        this.c = linker.requestBinding("ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager", CreditosFragment.class, getClass().getClassLoader());
        this.d = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.base.BaseFragment", CreditosFragment.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
        set2.add(this.c);
        set2.add(this.d);
    }

    public CreditosFragment get() {
        CreditosFragment creditosFragment = new CreditosFragment();
        injectMembers(creditosFragment);
        return creditosFragment;
    }

    public void injectMembers(CreditosFragment creditosFragment) {
        creditosFragment.a = (IDataManager) this.a.get();
        creditosFragment.b = (SessionManager) this.b.get();
        creditosFragment.c = (AnalyticsManager) this.c.get();
        this.d.injectMembers(creditosFragment);
    }
}
