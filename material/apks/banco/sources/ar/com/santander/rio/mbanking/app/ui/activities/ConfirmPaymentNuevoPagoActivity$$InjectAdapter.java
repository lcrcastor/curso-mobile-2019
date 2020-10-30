package ar.com.santander.rio.mbanking.app.ui.activities;

import ar.com.santander.rio.mbanking.app.base.BaseMvpActivity;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.security.SoftTokenManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;

public final class ConfirmPaymentNuevoPagoActivity$$InjectAdapter extends Binding<ConfirmPaymentNuevoPagoActivity> implements MembersInjector<ConfirmPaymentNuevoPagoActivity> {
    private Binding<SessionManager> a;
    private Binding<SoftTokenManager> b;
    private Binding<AnalyticsManager> c;
    private Binding<BaseMvpActivity> d;

    public ConfirmPaymentNuevoPagoActivity$$InjectAdapter() {
        super(null, "members/ar.com.santander.rio.mbanking.app.ui.activities.ConfirmPaymentNuevoPagoActivity", false, ConfirmPaymentNuevoPagoActivity.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.managers.session.SessionManager", ConfirmPaymentNuevoPagoActivity.class, getClass().getClassLoader());
        this.b = linker.requestBinding("ar.com.santander.rio.mbanking.managers.security.SoftTokenManager", ConfirmPaymentNuevoPagoActivity.class, getClass().getClassLoader());
        this.c = linker.requestBinding("ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager", ConfirmPaymentNuevoPagoActivity.class, getClass().getClassLoader());
        this.d = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.base.BaseMvpActivity", ConfirmPaymentNuevoPagoActivity.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
        set2.add(this.c);
        set2.add(this.d);
    }

    public void injectMembers(ConfirmPaymentNuevoPagoActivity confirmPaymentNuevoPagoActivity) {
        confirmPaymentNuevoPagoActivity.p = (SessionManager) this.a.get();
        confirmPaymentNuevoPagoActivity.q = (SoftTokenManager) this.b.get();
        confirmPaymentNuevoPagoActivity.r = (AnalyticsManager) this.c.get();
        this.d.injectMembers(confirmPaymentNuevoPagoActivity);
    }
}
