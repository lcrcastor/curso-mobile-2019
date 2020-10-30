package ar.com.santander.rio.mbanking.app.base;

import com.squareup.otto.Bus;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;

public final class BaseFragment$$InjectAdapter extends Binding<BaseFragment> implements MembersInjector<BaseFragment> {
    private Binding<Bus> a;

    public BaseFragment$$InjectAdapter() {
        super(null, "members/ar.com.santander.rio.mbanking.app.base.BaseFragment", false, BaseFragment.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("com.squareup.otto.Bus", BaseFragment.class, getClass().getClassLoader());
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
    }

    public void injectMembers(BaseFragment baseFragment) {
        baseFragment.bus = (Bus) this.a.get();
    }
}
