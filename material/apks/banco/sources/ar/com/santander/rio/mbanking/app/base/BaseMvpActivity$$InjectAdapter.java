package ar.com.santander.rio.mbanking.app.base;

import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import com.squareup.otto.Bus;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;

public final class BaseMvpActivity$$InjectAdapter extends Binding<BaseMvpActivity> implements MembersInjector<BaseMvpActivity> {
    private Binding<Bus> a;
    private Binding<IDataManager> b;
    private Binding<BaseActivity> c;

    public BaseMvpActivity$$InjectAdapter() {
        super(null, "members/ar.com.santander.rio.mbanking.app.base.BaseMvpActivity", false, BaseMvpActivity.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("com.squareup.otto.Bus", BaseMvpActivity.class, getClass().getClassLoader());
        this.b = linker.requestBinding("ar.com.santander.rio.mbanking.managers.data.IDataManager", BaseMvpActivity.class, getClass().getClassLoader());
        this.c = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.base.BaseActivity", BaseMvpActivity.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
        set2.add(this.c);
    }

    public void injectMembers(BaseMvpActivity baseMvpActivity) {
        baseMvpActivity.mBus = (Bus) this.a.get();
        baseMvpActivity.mDataManager = (IDataManager) this.b.get();
        this.c.injectMembers(baseMvpActivity);
    }
}
