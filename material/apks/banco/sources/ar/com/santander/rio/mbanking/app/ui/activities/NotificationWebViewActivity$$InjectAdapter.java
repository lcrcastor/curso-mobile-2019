package ar.com.santander.rio.mbanking.app.ui.activities;

import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;
import javax.inject.Provider;

public final class NotificationWebViewActivity$$InjectAdapter extends Binding<NotificationWebViewActivity> implements MembersInjector<NotificationWebViewActivity>, Provider<NotificationWebViewActivity> {
    private Binding<AnalyticsManager> a;
    private Binding<BaseActivity> b;

    public NotificationWebViewActivity$$InjectAdapter() {
        super("ar.com.santander.rio.mbanking.app.ui.activities.NotificationWebViewActivity", "members/ar.com.santander.rio.mbanking.app.ui.activities.NotificationWebViewActivity", false, NotificationWebViewActivity.class);
    }

    public void attach(Linker linker) {
        this.a = linker.requestBinding("ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager", NotificationWebViewActivity.class, getClass().getClassLoader());
        this.b = linker.requestBinding("members/ar.com.santander.rio.mbanking.app.base.BaseActivity", NotificationWebViewActivity.class, getClass().getClassLoader(), false, true);
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        set2.add(this.a);
        set2.add(this.b);
    }

    public NotificationWebViewActivity get() {
        NotificationWebViewActivity notificationWebViewActivity = new NotificationWebViewActivity();
        injectMembers(notificationWebViewActivity);
        return notificationWebViewActivity;
    }

    public void injectMembers(NotificationWebViewActivity notificationWebViewActivity) {
        notificationWebViewActivity.p = (AnalyticsManager) this.a.get();
        this.b.injectMembers(notificationWebViewActivity);
    }
}
