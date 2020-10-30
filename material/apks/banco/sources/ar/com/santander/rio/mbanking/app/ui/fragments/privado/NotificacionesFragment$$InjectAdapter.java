package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import ar.com.santander.rio.mbanking.app.base.BaseFragment;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;
import javax.inject.Provider;

public final class NotificacionesFragment$$InjectAdapter extends Binding<NotificacionesFragment> implements MembersInjector<NotificacionesFragment>, Provider<NotificacionesFragment> {
    private Binding<SessionManager> a;
    private Binding<AnalyticsManager> b;
    private Binding<BaseFragment> c;

    public NotificacionesFragment$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.fragments.privado.NotificacionesFragment", "members/ar.com.santander.rio.mbanking.app.ui.fragments.privado.NotificacionesFragment", false, NotificacionesFragment.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.managers.session.SessionManager", NotificacionesFragment.class, getClass().getClassLoader());
        this.b = linker.requestBinding("ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager", NotificacionesFragment.class, getClass().getClassLoader());
        this.c = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.base.BaseFragment", NotificacionesFragment.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
        set2.add(this.c);
    }

    public NotificacionesFragment get() {
        NotificacionesFragment notificacionesFragment = new NotificacionesFragment();
        injectMembers(notificacionesFragment);
        return notificacionesFragment;
    }

    public void injectMembers(NotificacionesFragment notificacionesFragment) {
        notificacionesFragment.a = (SessionManager) this.a.get();
        notificacionesFragment.b = (AnalyticsManager) this.b.get();
        this.c.injectMembers(notificacionesFragment);
    }
}
