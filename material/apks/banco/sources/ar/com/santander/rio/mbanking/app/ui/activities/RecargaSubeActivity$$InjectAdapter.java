package ar.com.santander.rio.mbanking.app.ui.activities;

import ar.com.santander.rio.mbanking.app.base.BaseMvpActivity;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.managers.preferences.SettingsManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import com.squareup.otto.Bus;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;
import javax.inject.Provider;

public final class RecargaSubeActivity$$InjectAdapter extends Binding<RecargaSubeActivity> implements MembersInjector<RecargaSubeActivity>, Provider<RecargaSubeActivity> {
    private Binding<Bus> a;
    private Binding<IDataManager> b;
    private Binding<SessionManager> c;
    private Binding<SettingsManager> d;
    private Binding<BaseMvpActivity> e;

    public RecargaSubeActivity$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.activities.RecargaSubeActivity", "members/ar.com.santander.rio.mbanking.app.ui.activities.RecargaSubeActivity", false, RecargaSubeActivity.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("com.squareup.otto.Bus", RecargaSubeActivity.class, getClass().getClassLoader());
        this.b = linker.requestBinding("ar.com.santander.rio.mbanking.managers.data.IDataManager", RecargaSubeActivity.class, getClass().getClassLoader());
        this.c = linker.requestBinding("ar.com.santander.rio.mbanking.managers.session.SessionManager", RecargaSubeActivity.class, getClass().getClassLoader());
        this.d = linker.requestBinding("ar.com.santander.rio.mbanking.managers.preferences.SettingsManager", RecargaSubeActivity.class, getClass().getClassLoader());
        this.e = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.base.BaseMvpActivity", RecargaSubeActivity.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
        set2.add(this.c);
        set2.add(this.d);
        set2.add(this.e);
    }

    public RecargaSubeActivity get() {
        RecargaSubeActivity recargaSubeActivity = new RecargaSubeActivity();
        injectMembers(recargaSubeActivity);
        return recargaSubeActivity;
    }

    public void injectMembers(RecargaSubeActivity recargaSubeActivity) {
        recargaSubeActivity.p = (Bus) this.a.get();
        recargaSubeActivity.mDataManager = (IDataManager) this.b.get();
        recargaSubeActivity.q = (SessionManager) this.c.get();
        recargaSubeActivity.r = (SettingsManager) this.d.get();
        this.e.injectMembers(recargaSubeActivity);
    }
}
