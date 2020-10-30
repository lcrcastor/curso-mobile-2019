package ar.com.santander.rio.mbanking.app.ui.activities;

import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import com.squareup.otto.Bus;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;

public final class MvpPrivateMenuActivity$$InjectAdapter extends Binding<MvpPrivateMenuActivity> implements MembersInjector<MvpPrivateMenuActivity> {
    private Binding<Bus> a;
    private Binding<IDataManager> b;
    private Binding<PrivateMenuActivity> c;

    public MvpPrivateMenuActivity$$InjectAdapter() {
        super(null, "members/ar.com.santander.rio.mbanking.app.ui.activities.MvpPrivateMenuActivity", false, MvpPrivateMenuActivity.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("com.squareup.otto.Bus", MvpPrivateMenuActivity.class, getClass().getClassLoader());
        this.b = linker.requestBinding("ar.com.santander.rio.mbanking.managers.data.IDataManager", MvpPrivateMenuActivity.class, getClass().getClassLoader());
        this.c = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.ui.activities.PrivateMenuActivity", MvpPrivateMenuActivity.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
        set2.add(this.c);
    }

    public void injectMembers(MvpPrivateMenuActivity mvpPrivateMenuActivity) {
        mvpPrivateMenuActivity.mBus = (Bus) this.a.get();
        mvpPrivateMenuActivity.mDataManager = (IDataManager) this.b.get();
        this.c.injectMembers(mvpPrivateMenuActivity);
    }
}
