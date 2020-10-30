package ar.com.santander.rio.mbanking.inject.modules;

import android.content.Context;
import android.location.LocationManager;
import ar.com.santander.rio.mbanking.app.base.BaseApplication;
import dagger.internal.BindingsGroup;
import dagger.internal.ModuleAdapter;
import dagger.internal.ProvidesBinding;
import javax.inject.Provider;

public final class AndroidModule$$ModuleAdapter extends ModuleAdapter<AndroidModule> {
    private static final String[] a = {"members/ar.com.santander.rio.mbanking.utils.genesyschat.WhatsonChatBackgroundService"};
    private static final Class<?>[] b = new Class[0];
    private static final Class<?>[] c = {EventBusModule.class, NetworkModule.class, ManagersModule.class, PreferencesModule.class};

    public static final class ProvideApplicationContextProvidesAdapter extends ProvidesBinding<Context> implements Provider<Context> {
        private final AndroidModule a;

        public ProvideApplicationContextProvidesAdapter(AndroidModule androidModule) {
            super("@ar.com.santander.rio.mbanking.inject.qualifiers.ForApplication()/android.content.Context", true, "ar.com.santander.rio.mbanking.inject.modules.AndroidModule", "provideApplicationContext");
            this.a = androidModule;
            setLibrary(true);
        }

        public Context get() {
            return this.a.provideApplicationContext();
        }
    }

    public static final class ProvideApplicationProvidesAdapter extends ProvidesBinding<BaseApplication> implements Provider<BaseApplication> {
        private final AndroidModule a;

        public ProvideApplicationProvidesAdapter(AndroidModule androidModule) {
            super("ar.com.santander.rio.mbanking.app.base.BaseApplication", true, "ar.com.santander.rio.mbanking.inject.modules.AndroidModule", "provideApplication");
            this.a = androidModule;
            setLibrary(true);
        }

        public BaseApplication get() {
            return this.a.provideApplication();
        }
    }

    public static final class ProvideLocationManagerProvidesAdapter extends ProvidesBinding<LocationManager> implements Provider<LocationManager> {
        private final AndroidModule a;

        public ProvideLocationManagerProvidesAdapter(AndroidModule androidModule) {
            super("android.location.LocationManager", true, "ar.com.santander.rio.mbanking.inject.modules.AndroidModule", "provideLocationManager");
            this.a = androidModule;
            setLibrary(true);
        }

        public LocationManager get() {
            return this.a.a();
        }
    }

    public AndroidModule$$ModuleAdapter() {
        super(AndroidModule.class, a, b, false, c, false, true);
    }

    public void getBindings(BindingsGroup bindingsGroup, AndroidModule androidModule) {
        bindingsGroup.contributeProvidesBinding("ar.com.santander.rio.mbanking.app.base.BaseApplication", new ProvideApplicationProvidesAdapter(androidModule));
        bindingsGroup.contributeProvidesBinding("@ar.com.santander.rio.mbanking.inject.qualifiers.ForApplication()/android.content.Context", new ProvideApplicationContextProvidesAdapter(androidModule));
        bindingsGroup.contributeProvidesBinding("android.location.LocationManager", new ProvideLocationManagerProvidesAdapter(androidModule));
    }
}
