package ar.com.santander.rio.mbanking.app.ui.activities;

import ar.com.santander.rio.mbanking.app.base.BaseMvpActivity;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;

public final class PrepararPagoServicioActivity$$InjectAdapter extends Binding<PrepararPagoServicioActivity> implements MembersInjector<PrepararPagoServicioActivity> {
    private Binding<SessionManager> a;
    private Binding<BaseMvpActivity> b;

    public PrepararPagoServicioActivity$$InjectAdapter() {
        super(null, "members/ar.com.santander.rio.mbanking.app.ui.activities.PrepararPagoServicioActivity", false, PrepararPagoServicioActivity.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.managers.session.SessionManager", PrepararPagoServicioActivity.class, getClass().getClassLoader());
        this.b = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.base.BaseMvpActivity", PrepararPagoServicioActivity.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
    }

    public void injectMembers(PrepararPagoServicioActivity prepararPagoServicioActivity) {
        prepararPagoServicioActivity.p = (SessionManager) this.a.get();
        this.b.injectMembers(prepararPagoServicioActivity);
    }
}
