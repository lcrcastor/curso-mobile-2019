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

public final class CreditosFragmentProd$$InjectAdapter extends Binding<CreditosFragmentProd> implements MembersInjector<CreditosFragmentProd>, Provider<CreditosFragmentProd> {
    private Binding<IDataManager> a;
    private Binding<SessionManager> b;
    private Binding<AnalyticsManager> c;
    private Binding<BaseFragment> d;

    public CreditosFragmentProd$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.fragments.privado.CreditosFragmentProd", "members/ar.com.santander.rio.mbanking.app.ui.fragments.privado.CreditosFragmentProd", false, CreditosFragmentProd.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.managers.data.IDataManager", CreditosFragmentProd.class, getClass().getClassLoader());
        this.b = linker.requestBinding("ar.com.santander.rio.mbanking.managers.session.SessionManager", CreditosFragmentProd.class, getClass().getClassLoader());
        this.c = linker.requestBinding("ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager", CreditosFragmentProd.class, getClass().getClassLoader());
        this.d = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.base.BaseFragment", CreditosFragmentProd.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
        set2.add(this.c);
        set2.add(this.d);
    }

    public CreditosFragmentProd get() {
        CreditosFragmentProd creditosFragmentProd = new CreditosFragmentProd();
        injectMembers(creditosFragmentProd);
        return creditosFragmentProd;
    }

    public void injectMembers(CreditosFragmentProd creditosFragmentProd) {
        creditosFragmentProd.a = (IDataManager) this.a.get();
        creditosFragmentProd.b = (SessionManager) this.b.get();
        creditosFragmentProd.c = (AnalyticsManager) this.c.get();
        this.d.injectMembers(creditosFragmentProd);
    }
}
