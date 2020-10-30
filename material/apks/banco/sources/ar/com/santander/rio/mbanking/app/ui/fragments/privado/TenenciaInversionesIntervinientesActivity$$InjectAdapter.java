package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;
import javax.inject.Provider;

public final class TenenciaInversionesIntervinientesActivity$$InjectAdapter extends Binding<TenenciaInversionesIntervinientesActivity> implements MembersInjector<TenenciaInversionesIntervinientesActivity>, Provider<TenenciaInversionesIntervinientesActivity> {
    private Binding<SessionManager> a;
    private Binding<IDataManager> b;
    private Binding<BaseActivity> c;

    public TenenciaInversionesIntervinientesActivity$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.fragments.privado.TenenciaInversionesIntervinientesActivity", "members/ar.com.santander.rio.mbanking.app.ui.fragments.privado.TenenciaInversionesIntervinientesActivity", false, TenenciaInversionesIntervinientesActivity.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.managers.session.SessionManager", TenenciaInversionesIntervinientesActivity.class, getClass().getClassLoader());
        this.b = linker.requestBinding("ar.com.santander.rio.mbanking.managers.data.IDataManager", TenenciaInversionesIntervinientesActivity.class, getClass().getClassLoader());
        this.c = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.base.BaseActivity", TenenciaInversionesIntervinientesActivity.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
        set2.add(this.c);
    }

    public TenenciaInversionesIntervinientesActivity get() {
        TenenciaInversionesIntervinientesActivity tenenciaInversionesIntervinientesActivity = new TenenciaInversionesIntervinientesActivity();
        injectMembers(tenenciaInversionesIntervinientesActivity);
        return tenenciaInversionesIntervinientesActivity;
    }

    public void injectMembers(TenenciaInversionesIntervinientesActivity tenenciaInversionesIntervinientesActivity) {
        tenenciaInversionesIntervinientesActivity.p = (SessionManager) this.a.get();
        tenenciaInversionesIntervinientesActivity.q = (IDataManager) this.b.get();
        this.c.injectMembers(tenenciaInversionesIntervinientesActivity);
    }
}
