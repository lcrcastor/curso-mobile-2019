package ar.com.santander.rio.mbanking.app.ui.activities;

import ar.com.santander.rio.mbanking.app.base.ITRSABaseActivity;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.preferences.SettingsManager;
import ar.com.santander.rio.mbanking.managers.security.SoftTokenManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;
import javax.inject.Provider;

public final class PreAutorizacionDebinActivity$$InjectAdapter extends Binding<PreAutorizacionDebinActivity> implements MembersInjector<PreAutorizacionDebinActivity>, Provider<PreAutorizacionDebinActivity> {
    private Binding<AnalyticsManager> a;
    private Binding<SessionManager> b;
    private Binding<SettingsManager> c;
    private Binding<SoftTokenManager> d;
    private Binding<ITRSABaseActivity> e;

    public PreAutorizacionDebinActivity$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.activities.PreAutorizacionDebinActivity", "members/ar.com.santander.rio.mbanking.app.ui.activities.PreAutorizacionDebinActivity", false, PreAutorizacionDebinActivity.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager", PreAutorizacionDebinActivity.class, getClass().getClassLoader());
        this.b = linker.requestBinding("ar.com.santander.rio.mbanking.managers.session.SessionManager", PreAutorizacionDebinActivity.class, getClass().getClassLoader());
        this.c = linker.requestBinding("ar.com.santander.rio.mbanking.managers.preferences.SettingsManager", PreAutorizacionDebinActivity.class, getClass().getClassLoader());
        this.d = linker.requestBinding("ar.com.santander.rio.mbanking.managers.security.SoftTokenManager", PreAutorizacionDebinActivity.class, getClass().getClassLoader());
        this.e = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.base.ITRSABaseActivity", PreAutorizacionDebinActivity.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
        set2.add(this.c);
        set2.add(this.d);
        set2.add(this.e);
    }

    public PreAutorizacionDebinActivity get() {
        PreAutorizacionDebinActivity preAutorizacionDebinActivity = new PreAutorizacionDebinActivity();
        injectMembers(preAutorizacionDebinActivity);
        return preAutorizacionDebinActivity;
    }

    public void injectMembers(PreAutorizacionDebinActivity preAutorizacionDebinActivity) {
        preAutorizacionDebinActivity.q = (AnalyticsManager) this.a.get();
        preAutorizacionDebinActivity.r = (SessionManager) this.b.get();
        preAutorizacionDebinActivity.s = (SettingsManager) this.c.get();
        preAutorizacionDebinActivity.t = (SoftTokenManager) this.d.get();
        this.e.injectMembers(preAutorizacionDebinActivity);
    }
}
