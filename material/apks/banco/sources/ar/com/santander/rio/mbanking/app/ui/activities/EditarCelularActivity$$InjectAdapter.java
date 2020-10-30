package ar.com.santander.rio.mbanking.app.ui.activities;

import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import com.squareup.otto.Bus;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;
import javax.inject.Provider;

public final class EditarCelularActivity$$InjectAdapter extends Binding<EditarCelularActivity> implements MembersInjector<EditarCelularActivity>, Provider<EditarCelularActivity> {
    private Binding<SessionManager> a;
    private Binding<Bus> b;
    private Binding<AnalyticsManager> c;
    private Binding<BaseActivity> d;

    public EditarCelularActivity$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.activities.EditarCelularActivity", "members/ar.com.santander.rio.mbanking.app.ui.activities.EditarCelularActivity", false, EditarCelularActivity.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.managers.session.SessionManager", EditarCelularActivity.class, getClass().getClassLoader());
        this.b = linker.requestBinding("com.squareup.otto.Bus", EditarCelularActivity.class, getClass().getClassLoader());
        this.c = linker.requestBinding("ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager", EditarCelularActivity.class, getClass().getClassLoader());
        this.d = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.base.BaseActivity", EditarCelularActivity.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
        set2.add(this.c);
        set2.add(this.d);
    }

    public EditarCelularActivity get() {
        EditarCelularActivity editarCelularActivity = new EditarCelularActivity();
        injectMembers(editarCelularActivity);
        return editarCelularActivity;
    }

    public void injectMembers(EditarCelularActivity editarCelularActivity) {
        editarCelularActivity.p = (SessionManager) this.a.get();
        editarCelularActivity.q = (Bus) this.b.get();
        editarCelularActivity.r = (AnalyticsManager) this.c.get();
        this.d.injectMembers(editarCelularActivity);
    }
}
