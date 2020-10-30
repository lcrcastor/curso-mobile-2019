package ar.com.santander.rio.mbanking.inject.modules;

import ar.com.santander.rio.mbanking.app.base.BaseApplication;
import ar.com.santander.rio.mbanking.app.module.accounts.analytics.AccountAnalytics;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.managers.notifications.PushNotificationsManager;
import ar.com.santander.rio.mbanking.managers.preferences.SettingsManager;
import ar.com.santander.rio.mbanking.managers.redessociales.IRedesSocialesManager;
import ar.com.santander.rio.mbanking.managers.security.SoftTokenManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import com.squareup.otto.Bus;
import dagger.internal.Binding;
import dagger.internal.BindingsGroup;
import dagger.internal.Linker;
import dagger.internal.ModuleAdapter;
import dagger.internal.ProvidesBinding;
import java.util.Set;
import javax.inject.Provider;

public final class ManagersModule$$ModuleAdapter extends ModuleAdapter<ManagersModule> {
    private static final String[] a = new String[0];
    private static final Class<?>[] b = new Class[0];
    private static final Class<?>[] c = new Class[0];

    public static final class ProvideAccountAnalyticsProvidesAdapter extends ProvidesBinding<AccountAnalytics> implements Provider<AccountAnalytics> {
        private final ManagersModule a;
        private Binding<BaseApplication> b;
        private Binding<AnalyticsManager> c;

        public ProvideAccountAnalyticsProvidesAdapter(ManagersModule managersModule) {
            super("ar.com.santander.rio.mbanking.app.module.accounts.analytics.AccountAnalytics", true, "ar.com.santander.rio.mbanking.inject.modules.ManagersModule", "provideAccountAnalytics");
            this.a = managersModule;
            setLibrary(true);
        }

        public void attach(Linker linker) {
            this.b = linker.requestBinding("ar.com.santander.rio.mbanking.app.base.BaseApplication", ManagersModule.class, getClass().getClassLoader());
            this.c = linker.requestBinding("ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager", ManagersModule.class, getClass().getClassLoader());
        }

        public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
            set.add(this.b);
            set.add(this.c);
        }

        public AccountAnalytics get() {
            return this.a.a((BaseApplication) this.b.get(), (AnalyticsManager) this.c.get());
        }
    }

    public static final class ProvideAnalyticsManagerProvidesAdapter extends ProvidesBinding<AnalyticsManager> implements Provider<AnalyticsManager> {
        private final ManagersModule a;
        private Binding<BaseApplication> b;
        private Binding<SessionManager> c;

        public ProvideAnalyticsManagerProvidesAdapter(ManagersModule managersModule) {
            super("ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager", true, "ar.com.santander.rio.mbanking.inject.modules.ManagersModule", "provideAnalyticsManager");
            this.a = managersModule;
            setLibrary(true);
        }

        public void attach(Linker linker) {
            this.b = linker.requestBinding("ar.com.santander.rio.mbanking.app.base.BaseApplication", ManagersModule.class, getClass().getClassLoader());
            this.c = linker.requestBinding("ar.com.santander.rio.mbanking.managers.session.SessionManager", ManagersModule.class, getClass().getClassLoader());
        }

        public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
            set.add(this.b);
            set.add(this.c);
        }

        public AnalyticsManager get() {
            return this.a.a((BaseApplication) this.b.get(), (SessionManager) this.c.get());
        }
    }

    public static final class ProvideDataManagerProvidesAdapter extends ProvidesBinding<IDataManager> implements Provider<IDataManager> {
        private final ManagersModule a;
        private Binding<BaseApplication> b;
        private Binding<Bus> c;
        private Binding<SessionManager> d;
        private Binding<SettingsManager> e;

        public ProvideDataManagerProvidesAdapter(ManagersModule managersModule) {
            super("ar.com.santander.rio.mbanking.managers.data.IDataManager", true, "ar.com.santander.rio.mbanking.inject.modules.ManagersModule", "provideDataManager");
            this.a = managersModule;
            setLibrary(true);
        }

        public void attach(Linker linker) {
            this.b = linker.requestBinding("ar.com.santander.rio.mbanking.app.base.BaseApplication", ManagersModule.class, getClass().getClassLoader());
            this.c = linker.requestBinding("com.squareup.otto.Bus", ManagersModule.class, getClass().getClassLoader());
            this.d = linker.requestBinding("ar.com.santander.rio.mbanking.managers.session.SessionManager", ManagersModule.class, getClass().getClassLoader());
            this.e = linker.requestBinding("ar.com.santander.rio.mbanking.managers.preferences.SettingsManager", ManagersModule.class, getClass().getClassLoader());
        }

        public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
            set.add(this.b);
            set.add(this.c);
            set.add(this.d);
            set.add(this.e);
        }

        public IDataManager get() {
            return this.a.a((BaseApplication) this.b.get(), (Bus) this.c.get(), (SessionManager) this.d.get(), (SettingsManager) this.e.get());
        }
    }

    public static final class ProvidePushNotificationsManagerProvidesAdapter extends ProvidesBinding<PushNotificationsManager> implements Provider<PushNotificationsManager> {
        private final ManagersModule a;
        private Binding<BaseApplication> b;

        public ProvidePushNotificationsManagerProvidesAdapter(ManagersModule managersModule) {
            super("ar.com.santander.rio.mbanking.managers.notifications.PushNotificationsManager", true, "ar.com.santander.rio.mbanking.inject.modules.ManagersModule", "providePushNotificationsManager");
            this.a = managersModule;
            setLibrary(true);
        }

        public void attach(Linker linker) {
            this.b = linker.requestBinding("ar.com.santander.rio.mbanking.app.base.BaseApplication", ManagersModule.class, getClass().getClassLoader());
        }

        public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
            set.add(this.b);
        }

        public PushNotificationsManager get() {
            return this.a.c((BaseApplication) this.b.get());
        }
    }

    public static final class ProvideRedesSocialesManagerProvidesAdapter extends ProvidesBinding<IRedesSocialesManager> implements Provider<IRedesSocialesManager> {
        private final ManagersModule a;
        private Binding<BaseApplication> b;

        public ProvideRedesSocialesManagerProvidesAdapter(ManagersModule managersModule) {
            super("ar.com.santander.rio.mbanking.managers.redessociales.IRedesSocialesManager", true, "ar.com.santander.rio.mbanking.inject.modules.ManagersModule", "provideRedesSocialesManager");
            this.a = managersModule;
            setLibrary(true);
        }

        public void attach(Linker linker) {
            this.b = linker.requestBinding("ar.com.santander.rio.mbanking.app.base.BaseApplication", ManagersModule.class, getClass().getClassLoader());
        }

        public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
            set.add(this.b);
        }

        public IRedesSocialesManager get() {
            return this.a.a((BaseApplication) this.b.get());
        }
    }

    public static final class ProvideSessionManagerProvidesAdapter extends ProvidesBinding<SessionManager> implements Provider<SessionManager> {
        private final ManagersModule a;
        private Binding<BaseApplication> b;
        private Binding<SettingsManager> c;

        public ProvideSessionManagerProvidesAdapter(ManagersModule managersModule) {
            super("ar.com.santander.rio.mbanking.managers.session.SessionManager", true, "ar.com.santander.rio.mbanking.inject.modules.ManagersModule", "provideSessionManager");
            this.a = managersModule;
            setLibrary(true);
        }

        public void attach(Linker linker) {
            this.b = linker.requestBinding("ar.com.santander.rio.mbanking.app.base.BaseApplication", ManagersModule.class, getClass().getClassLoader());
            this.c = linker.requestBinding("ar.com.santander.rio.mbanking.managers.preferences.SettingsManager", ManagersModule.class, getClass().getClassLoader());
        }

        public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
            set.add(this.b);
            set.add(this.c);
        }

        public SessionManager get() {
            return this.a.a((BaseApplication) this.b.get(), (SettingsManager) this.c.get());
        }
    }

    public static final class ProvideSettingsManagerProvidesAdapter extends ProvidesBinding<SettingsManager> implements Provider<SettingsManager> {
        private final ManagersModule a;
        private Binding<BaseApplication> b;

        public ProvideSettingsManagerProvidesAdapter(ManagersModule managersModule) {
            super("ar.com.santander.rio.mbanking.managers.preferences.SettingsManager", true, "ar.com.santander.rio.mbanking.inject.modules.ManagersModule", "provideSettingsManager");
            this.a = managersModule;
            setLibrary(true);
        }

        public void attach(Linker linker) {
            this.b = linker.requestBinding("ar.com.santander.rio.mbanking.app.base.BaseApplication", ManagersModule.class, getClass().getClassLoader());
        }

        public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
            set.add(this.b);
        }

        public SettingsManager get() {
            return this.a.b((BaseApplication) this.b.get());
        }
    }

    public static final class ProvideSoftTokenManagerProvidesAdapter extends ProvidesBinding<SoftTokenManager> implements Provider<SoftTokenManager> {
        private final ManagersModule a;
        private Binding<BaseApplication> b;

        public ProvideSoftTokenManagerProvidesAdapter(ManagersModule managersModule) {
            super("ar.com.santander.rio.mbanking.managers.security.SoftTokenManager", true, "ar.com.santander.rio.mbanking.inject.modules.ManagersModule", "provideSoftTokenManager");
            this.a = managersModule;
            setLibrary(true);
        }

        public void attach(Linker linker) {
            this.b = linker.requestBinding("ar.com.santander.rio.mbanking.app.base.BaseApplication", ManagersModule.class, getClass().getClassLoader());
        }

        public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
            set.add(this.b);
        }

        public SoftTokenManager get() {
            return this.a.d((BaseApplication) this.b.get());
        }
    }

    public ManagersModule$$ModuleAdapter() {
        super(ManagersModule.class, a, b, false, c, false, true);
    }

    public ManagersModule newModule() {
        return new ManagersModule();
    }

    public void getBindings(BindingsGroup bindingsGroup, ManagersModule managersModule) {
        bindingsGroup.contributeProvidesBinding("ar.com.santander.rio.mbanking.managers.data.IDataManager", new ProvideDataManagerProvidesAdapter(managersModule));
        bindingsGroup.contributeProvidesBinding("ar.com.santander.rio.mbanking.managers.redessociales.IRedesSocialesManager", new ProvideRedesSocialesManagerProvidesAdapter(managersModule));
        bindingsGroup.contributeProvidesBinding("ar.com.santander.rio.mbanking.managers.preferences.SettingsManager", new ProvideSettingsManagerProvidesAdapter(managersModule));
        bindingsGroup.contributeProvidesBinding("ar.com.santander.rio.mbanking.managers.session.SessionManager", new ProvideSessionManagerProvidesAdapter(managersModule));
        bindingsGroup.contributeProvidesBinding("ar.com.santander.rio.mbanking.managers.notifications.PushNotificationsManager", new ProvidePushNotificationsManagerProvidesAdapter(managersModule));
        bindingsGroup.contributeProvidesBinding("ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager", new ProvideAnalyticsManagerProvidesAdapter(managersModule));
        bindingsGroup.contributeProvidesBinding("ar.com.santander.rio.mbanking.app.module.accounts.analytics.AccountAnalytics", new ProvideAccountAnalyticsProvidesAdapter(managersModule));
        bindingsGroup.contributeProvidesBinding("ar.com.santander.rio.mbanking.managers.security.SoftTokenManager", new ProvideSoftTokenManagerProvidesAdapter(managersModule));
    }
}
