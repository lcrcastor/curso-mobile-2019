package ar.com.santander.rio.mbanking.app.base;

import com.squareup.otto.Bus;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;

public final class BaseDialogFragment$$InjectAdapter extends Binding<BaseDialogFragment> implements MembersInjector<BaseDialogFragment> {
    private Binding<Bus> a;

    public BaseDialogFragment$$InjectAdapter() {
        super(null, "members/ar.com.santander.rio.mbanking.app.base.BaseDialogFragment", false, BaseDialogFragment.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("com.squareup.otto.Bus", BaseDialogFragment.class, getClass().getClassLoader());
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
    }

    public void injectMembers(BaseDialogFragment baseDialogFragment) {
        baseDialogFragment.bus = (Bus) this.a.get();
    }
}
