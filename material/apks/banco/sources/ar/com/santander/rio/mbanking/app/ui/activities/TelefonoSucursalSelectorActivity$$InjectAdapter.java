package ar.com.santander.rio.mbanking.app.ui.activities;

import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import com.squareup.otto.Bus;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;
import javax.inject.Provider;

public final class TelefonoSucursalSelectorActivity$$InjectAdapter extends Binding<TelefonoSucursalSelectorActivity> implements MembersInjector<TelefonoSucursalSelectorActivity>, Provider<TelefonoSucursalSelectorActivity> {
    private Binding<Bus> a;
    private Binding<AnalyticsManager> b;
    private Binding<BaseActivity> c;

    public TelefonoSucursalSelectorActivity$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.activities.TelefonoSucursalSelectorActivity", "members/ar.com.santander.rio.mbanking.app.ui.activities.TelefonoSucursalSelectorActivity", false, TelefonoSucursalSelectorActivity.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("com.squareup.otto.Bus", TelefonoSucursalSelectorActivity.class, getClass().getClassLoader());
        this.b = linker.requestBinding("ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager", TelefonoSucursalSelectorActivity.class, getClass().getClassLoader());
        this.c = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.base.BaseActivity", TelefonoSucursalSelectorActivity.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
        set2.add(this.c);
    }

    public TelefonoSucursalSelectorActivity get() {
        TelefonoSucursalSelectorActivity telefonoSucursalSelectorActivity = new TelefonoSucursalSelectorActivity();
        injectMembers(telefonoSucursalSelectorActivity);
        return telefonoSucursalSelectorActivity;
    }

    public void injectMembers(TelefonoSucursalSelectorActivity telefonoSucursalSelectorActivity) {
        telefonoSucursalSelectorActivity.p = (Bus) this.a.get();
        telefonoSucursalSelectorActivity.q = (AnalyticsManager) this.b.get();
        this.c.injectMembers(telefonoSucursalSelectorActivity);
    }
}
