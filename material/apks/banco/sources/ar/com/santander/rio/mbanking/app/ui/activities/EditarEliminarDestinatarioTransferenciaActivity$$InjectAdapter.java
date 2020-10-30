package ar.com.santander.rio.mbanking.app.ui.activities;

import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.security.SoftTokenManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;
import javax.inject.Provider;

public final class EditarEliminarDestinatarioTransferenciaActivity$$InjectAdapter extends Binding<EditarEliminarDestinatarioTransferenciaActivity> implements MembersInjector<EditarEliminarDestinatarioTransferenciaActivity>, Provider<EditarEliminarDestinatarioTransferenciaActivity> {
    private Binding<AnalyticsManager> a;
    private Binding<SoftTokenManager> b;
    private Binding<SessionManager> c;
    private Binding<MvpPrivateMenuActivity> d;

    public EditarEliminarDestinatarioTransferenciaActivity$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.activities.EditarEliminarDestinatarioTransferenciaActivity", "members/ar.com.santander.rio.mbanking.app.ui.activities.EditarEliminarDestinatarioTransferenciaActivity", false, EditarEliminarDestinatarioTransferenciaActivity.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager", EditarEliminarDestinatarioTransferenciaActivity.class, getClass().getClassLoader());
        this.b = linker.requestBinding("ar.com.santander.rio.mbanking.managers.security.SoftTokenManager", EditarEliminarDestinatarioTransferenciaActivity.class, getClass().getClassLoader());
        this.c = linker.requestBinding("ar.com.santander.rio.mbanking.managers.session.SessionManager", EditarEliminarDestinatarioTransferenciaActivity.class, getClass().getClassLoader());
        this.d = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.ui.activities.MvpPrivateMenuActivity", EditarEliminarDestinatarioTransferenciaActivity.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
        set2.add(this.c);
        set2.add(this.d);
    }

    public EditarEliminarDestinatarioTransferenciaActivity get() {
        EditarEliminarDestinatarioTransferenciaActivity editarEliminarDestinatarioTransferenciaActivity = new EditarEliminarDestinatarioTransferenciaActivity();
        injectMembers(editarEliminarDestinatarioTransferenciaActivity);
        return editarEliminarDestinatarioTransferenciaActivity;
    }

    public void injectMembers(EditarEliminarDestinatarioTransferenciaActivity editarEliminarDestinatarioTransferenciaActivity) {
        editarEliminarDestinatarioTransferenciaActivity.p = (AnalyticsManager) this.a.get();
        editarEliminarDestinatarioTransferenciaActivity.q = (SoftTokenManager) this.b.get();
        editarEliminarDestinatarioTransferenciaActivity.t = (SessionManager) this.c.get();
        this.d.injectMembers(editarEliminarDestinatarioTransferenciaActivity);
    }
}
