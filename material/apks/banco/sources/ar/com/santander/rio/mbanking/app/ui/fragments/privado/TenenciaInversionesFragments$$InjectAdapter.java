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

public final class TenenciaInversionesFragments$$InjectAdapter extends Binding<TenenciaInversionesFragments> implements MembersInjector<TenenciaInversionesFragments>, Provider<TenenciaInversionesFragments> {
    private Binding<SessionManager> a;
    private Binding<IDataManager> b;
    private Binding<AnalyticsManager> c;
    private Binding<BaseFragment> d;

    public TenenciaInversionesFragments$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.fragments.privado.TenenciaInversionesFragments", "members/ar.com.santander.rio.mbanking.app.ui.fragments.privado.TenenciaInversionesFragments", false, TenenciaInversionesFragments.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.managers.session.SessionManager", TenenciaInversionesFragments.class, getClass().getClassLoader());
        this.b = linker.requestBinding("ar.com.santander.rio.mbanking.managers.data.IDataManager", TenenciaInversionesFragments.class, getClass().getClassLoader());
        this.c = linker.requestBinding("ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager", TenenciaInversionesFragments.class, getClass().getClassLoader());
        this.d = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.base.BaseFragment", TenenciaInversionesFragments.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
        set2.add(this.c);
        set2.add(this.d);
    }

    public TenenciaInversionesFragments get() {
        TenenciaInversionesFragments tenenciaInversionesFragments = new TenenciaInversionesFragments();
        injectMembers(tenenciaInversionesFragments);
        return tenenciaInversionesFragments;
    }

    public void injectMembers(TenenciaInversionesFragments tenenciaInversionesFragments) {
        tenenciaInversionesFragments.sessionManager = (SessionManager) this.a.get();
        tenenciaInversionesFragments.iDataManager = (IDataManager) this.b.get();
        tenenciaInversionesFragments.analyticsManager = (AnalyticsManager) this.c.get();
        this.d.injectMembers(tenenciaInversionesFragments);
    }
}
