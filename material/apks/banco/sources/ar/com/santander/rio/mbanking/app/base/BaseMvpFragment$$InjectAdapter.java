package ar.com.santander.rio.mbanking.app.base;

import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import com.squareup.otto.Bus;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;

public final class BaseMvpFragment$$InjectAdapter extends Binding<BaseMvpFragment> implements MembersInjector<BaseMvpFragment> {
    private Binding<Bus> a;
    private Binding<IDataManager> b;
    private Binding<BaseFragment> c;

    public BaseMvpFragment$$InjectAdapter() {
        super(null, "members/ar.com.santander.rio.mbanking.app.base.BaseMvpFragment", false, BaseMvpFragment.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("com.squareup.otto.Bus", BaseMvpFragment.class, getClass().getClassLoader());
        this.b = linker.requestBinding("ar.com.santander.rio.mbanking.managers.data.IDataManager", BaseMvpFragment.class, getClass().getClassLoader());
        this.c = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.base.BaseFragment", BaseMvpFragment.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
        set2.add(this.c);
    }

    public void injectMembers(BaseMvpFragment baseMvpFragment) {
        baseMvpFragment.mBus = (Bus) this.a.get();
        baseMvpFragment.mDataManager = (IDataManager) this.b.get();
        this.c.injectMembers(baseMvpFragment);
    }
}
