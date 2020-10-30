package ar.com.santander.rio.mbanking.app.base;

import com.android.volley.RequestQueue;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;

public final class BaseInteractor$$InjectAdapter extends Binding<BaseInteractor> implements MembersInjector<BaseInteractor> {
    private Binding<RequestQueue> a;

    public BaseInteractor$$InjectAdapter() {
        super(null, "members/ar.com.santander.rio.mbanking.app.base.BaseInteractor", false, BaseInteractor.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("com.android.volley.RequestQueue", BaseInteractor.class, getClass().getClassLoader());
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
    }

    public void injectMembers(BaseInteractor baseInteractor) {
        baseInteractor.a = (RequestQueue) this.a.get();
    }
}
