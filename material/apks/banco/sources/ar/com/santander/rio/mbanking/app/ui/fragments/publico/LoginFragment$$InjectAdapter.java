package ar.com.santander.rio.mbanking.app.ui.fragments.publico;

import ar.com.santander.rio.mbanking.app.base.BaseFragment;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;
import javax.inject.Provider;

public final class LoginFragment$$InjectAdapter extends Binding<LoginFragment> implements MembersInjector<LoginFragment>, Provider<LoginFragment> {
    private Binding<IDataManager> a;
    private Binding<AnalyticsManager> b;
    private Binding<SessionManager> c;
    private Binding<BaseFragment> d;

    public LoginFragment$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.fragments.publico.LoginFragment", "members/ar.com.santander.rio.mbanking.app.ui.fragments.publico.LoginFragment", false, LoginFragment.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.managers.data.IDataManager", LoginFragment.class, getClass().getClassLoader());
        this.b = linker.requestBinding("ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager", LoginFragment.class, getClass().getClassLoader());
        this.c = linker.requestBinding("ar.com.santander.rio.mbanking.managers.session.SessionManager", LoginFragment.class, getClass().getClassLoader());
        this.d = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.base.BaseFragment", LoginFragment.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
        set2.add(this.c);
        set2.add(this.d);
    }

    public LoginFragment get() {
        LoginFragment loginFragment = new LoginFragment();
        injectMembers(loginFragment);
        return loginFragment;
    }

    public void injectMembers(LoginFragment loginFragment) {
        loginFragment.a = (IDataManager) this.a.get();
        loginFragment.b = (AnalyticsManager) this.b.get();
        loginFragment.c = (SessionManager) this.c.get();
        this.d.injectMembers(loginFragment);
    }
}
