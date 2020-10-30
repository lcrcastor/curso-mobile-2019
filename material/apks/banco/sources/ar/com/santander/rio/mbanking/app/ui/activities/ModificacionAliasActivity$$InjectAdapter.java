package ar.com.santander.rio.mbanking.app.ui.activities;

import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;
import javax.inject.Provider;

public final class ModificacionAliasActivity$$InjectAdapter extends Binding<ModificacionAliasActivity> implements MembersInjector<ModificacionAliasActivity>, Provider<ModificacionAliasActivity> {
    private Binding<AnalyticsManager> a;
    private Binding<SessionManager> b;
    private Binding<MvpPrivateMenuActivity> c;

    public ModificacionAliasActivity$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.activities.ModificacionAliasActivity", "members/ar.com.santander.rio.mbanking.app.ui.activities.ModificacionAliasActivity", false, ModificacionAliasActivity.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager", ModificacionAliasActivity.class, getClass().getClassLoader());
        this.b = linker.requestBinding("ar.com.santander.rio.mbanking.managers.session.SessionManager", ModificacionAliasActivity.class, getClass().getClassLoader());
        this.c = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.ui.activities.MvpPrivateMenuActivity", ModificacionAliasActivity.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
        set2.add(this.c);
    }

    public ModificacionAliasActivity get() {
        ModificacionAliasActivity modificacionAliasActivity = new ModificacionAliasActivity();
        injectMembers(modificacionAliasActivity);
        return modificacionAliasActivity;
    }

    public void injectMembers(ModificacionAliasActivity modificacionAliasActivity) {
        modificacionAliasActivity.q = (AnalyticsManager) this.a.get();
        modificacionAliasActivity.s = (SessionManager) this.b.get();
        this.c.injectMembers(modificacionAliasActivity);
    }
}
