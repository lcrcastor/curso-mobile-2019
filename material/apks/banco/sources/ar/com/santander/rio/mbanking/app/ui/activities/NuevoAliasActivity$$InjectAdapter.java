package ar.com.santander.rio.mbanking.app.ui.activities;

import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;
import javax.inject.Provider;

public final class NuevoAliasActivity$$InjectAdapter extends Binding<NuevoAliasActivity> implements MembersInjector<NuevoAliasActivity>, Provider<NuevoAliasActivity> {
    private Binding<SessionManager> a;
    private Binding<MvpPrivateMenuActivity> b;

    public NuevoAliasActivity$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.activities.NuevoAliasActivity", "members/ar.com.santander.rio.mbanking.app.ui.activities.NuevoAliasActivity", false, NuevoAliasActivity.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.managers.session.SessionManager", NuevoAliasActivity.class, getClass().getClassLoader());
        this.b = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.ui.activities.MvpPrivateMenuActivity", NuevoAliasActivity.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
    }

    public NuevoAliasActivity get() {
        NuevoAliasActivity nuevoAliasActivity = new NuevoAliasActivity();
        injectMembers(nuevoAliasActivity);
        return nuevoAliasActivity;
    }

    public void injectMembers(NuevoAliasActivity nuevoAliasActivity) {
        nuevoAliasActivity.r = (SessionManager) this.a.get();
        this.b.injectMembers(nuevoAliasActivity);
    }
}
