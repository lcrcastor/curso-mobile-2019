package ar.com.santander.rio.mbanking.app.ui.activities;

import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;
import javax.inject.Provider;

public final class PrivateMenuActivity$$InjectAdapter extends Binding<PrivateMenuActivity> implements MembersInjector<PrivateMenuActivity>, Provider<PrivateMenuActivity> {
    private Binding<SessionManager> a;
    private Binding<AbstractNavDrawerActivity> b;

    public PrivateMenuActivity$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.activities.PrivateMenuActivity", "members/ar.com.santander.rio.mbanking.app.ui.activities.PrivateMenuActivity", false, PrivateMenuActivity.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.managers.session.SessionManager", PrivateMenuActivity.class, getClass().getClassLoader());
        this.b = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.ui.activities.AbstractNavDrawerActivity", PrivateMenuActivity.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
    }

    public PrivateMenuActivity get() {
        PrivateMenuActivity privateMenuActivity = new PrivateMenuActivity();
        injectMembers(privateMenuActivity);
        return privateMenuActivity;
    }

    public void injectMembers(PrivateMenuActivity privateMenuActivity) {
        privateMenuActivity.sessionManager = (SessionManager) this.a.get();
        this.b.injectMembers(privateMenuActivity);
    }
}
