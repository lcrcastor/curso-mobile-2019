package ar.com.santander.rio.mbanking.app.ui.activities;

import ar.com.santander.rio.mbanking.app.base.BaseMvpActivity;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;
import javax.inject.Provider;

public final class AgendaDestinatariosTransferenciasActivity$$InjectAdapter extends Binding<AgendaDestinatariosTransferenciasActivity> implements MembersInjector<AgendaDestinatariosTransferenciasActivity>, Provider<AgendaDestinatariosTransferenciasActivity> {
    private Binding<AnalyticsManager> a;
    private Binding<SessionManager> b;
    private Binding<IDataManager> c;
    private Binding<BaseMvpActivity> d;

    public AgendaDestinatariosTransferenciasActivity$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.activities.AgendaDestinatariosTransferenciasActivity", "members/ar.com.santander.rio.mbanking.app.ui.activities.AgendaDestinatariosTransferenciasActivity", false, AgendaDestinatariosTransferenciasActivity.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager", AgendaDestinatariosTransferenciasActivity.class, getClass().getClassLoader());
        this.b = linker.requestBinding("ar.com.santander.rio.mbanking.managers.session.SessionManager", AgendaDestinatariosTransferenciasActivity.class, getClass().getClassLoader());
        this.c = linker.requestBinding("ar.com.santander.rio.mbanking.managers.data.IDataManager", AgendaDestinatariosTransferenciasActivity.class, getClass().getClassLoader());
        this.d = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.base.BaseMvpActivity", AgendaDestinatariosTransferenciasActivity.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
        set2.add(this.c);
        set2.add(this.d);
    }

    public AgendaDestinatariosTransferenciasActivity get() {
        AgendaDestinatariosTransferenciasActivity agendaDestinatariosTransferenciasActivity = new AgendaDestinatariosTransferenciasActivity();
        injectMembers(agendaDestinatariosTransferenciasActivity);
        return agendaDestinatariosTransferenciasActivity;
    }

    public void injectMembers(AgendaDestinatariosTransferenciasActivity agendaDestinatariosTransferenciasActivity) {
        agendaDestinatariosTransferenciasActivity.p = (AnalyticsManager) this.a.get();
        agendaDestinatariosTransferenciasActivity.q = (SessionManager) this.b.get();
        agendaDestinatariosTransferenciasActivity.r = (IDataManager) this.c.get();
        this.d.injectMembers(agendaDestinatariosTransferenciasActivity);
    }
}
