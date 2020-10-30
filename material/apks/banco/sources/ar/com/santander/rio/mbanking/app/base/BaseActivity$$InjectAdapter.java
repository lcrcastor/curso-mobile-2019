package ar.com.santander.rio.mbanking.app.base;

import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import com.squareup.otto.Bus;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;
import javax.inject.Provider;

public final class BaseActivity$$InjectAdapter extends Binding<BaseActivity> implements MembersInjector<BaseActivity>, Provider<BaseActivity> {
    private Binding<Bus> a;
    private Binding<SessionManager> b;

    public BaseActivity$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.base.BaseActivity", "members/ar.com.santander.rio.mbanking.app.base.BaseActivity", false, BaseActivity.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("com.squareup.otto.Bus", BaseActivity.class, getClass().getClassLoader());
        this.b = linker.requestBinding("ar.com.santander.rio.mbanking.managers.session.SessionManager", BaseActivity.class, getClass().getClassLoader());
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
    }

    public BaseActivity get() {
        BaseActivity baseActivity = new BaseActivity();
        injectMembers(baseActivity);
        return baseActivity;
    }

    public void injectMembers(BaseActivity baseActivity) {
        baseActivity.n = (Bus) this.a.get();
        baseActivity.o = (SessionManager) this.b.get();
    }
}
