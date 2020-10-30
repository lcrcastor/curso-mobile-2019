package ar.com.santander.rio.mbanking.app.ui.activities;

import ar.com.santander.rio.mbanking.app.base.BaseMvpActivity;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;
import javax.inject.Provider;

public final class SeleccionarTarjetasMarcacionActivity$$InjectAdapter extends Binding<SeleccionarTarjetasMarcacionActivity> implements MembersInjector<SeleccionarTarjetasMarcacionActivity>, Provider<SeleccionarTarjetasMarcacionActivity> {
    private Binding<SessionManager> a;
    private Binding<AnalyticsManager> b;
    private Binding<BaseMvpActivity> c;

    public SeleccionarTarjetasMarcacionActivity$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.activities.SeleccionarTarjetasMarcacionActivity", "members/ar.com.santander.rio.mbanking.app.ui.activities.SeleccionarTarjetasMarcacionActivity", false, SeleccionarTarjetasMarcacionActivity.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.managers.session.SessionManager", SeleccionarTarjetasMarcacionActivity.class, getClass().getClassLoader());
        this.b = linker.requestBinding("ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager", SeleccionarTarjetasMarcacionActivity.class, getClass().getClassLoader());
        this.c = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.base.BaseMvpActivity", SeleccionarTarjetasMarcacionActivity.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
        set2.add(this.c);
    }

    public SeleccionarTarjetasMarcacionActivity get() {
        SeleccionarTarjetasMarcacionActivity seleccionarTarjetasMarcacionActivity = new SeleccionarTarjetasMarcacionActivity();
        injectMembers(seleccionarTarjetasMarcacionActivity);
        return seleccionarTarjetasMarcacionActivity;
    }

    public void injectMembers(SeleccionarTarjetasMarcacionActivity seleccionarTarjetasMarcacionActivity) {
        seleccionarTarjetasMarcacionActivity.p = (SessionManager) this.a.get();
        seleccionarTarjetasMarcacionActivity.q = (AnalyticsManager) this.b.get();
        this.c.injectMembers(seleccionarTarjetasMarcacionActivity);
    }
}
