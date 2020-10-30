package ar.com.santander.rio.mbanking.inject.modules;

import android.content.Context;
import android.location.LocationManager;
import ar.com.santander.rio.mbanking.app.base.BaseApplication;
import ar.com.santander.rio.mbanking.inject.qualifiers.ForApplication;
import ar.com.santander.rio.mbanking.utils.genesyschat.WhatsonChatBackgroundService;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module(complete = false, includes = {EventBusModule.class, NetworkModule.class, ManagersModule.class, PreferencesModule.class}, injects = {WhatsonChatBackgroundService.class}, library = true)
public class AndroidModule {
    private final BaseApplication a;

    public AndroidModule(BaseApplication baseApplication) {
        this.a = baseApplication;
    }

    @Singleton
    @Provides
    public BaseApplication provideApplication() {
        return this.a;
    }

    @Singleton
    @ForApplication
    @Provides
    public Context provideApplicationContext() {
        return this.a.getApplicationContext();
    }

    /* access modifiers changed from: 0000 */
    @Singleton
    @Provides
    public LocationManager a() {
        return (LocationManager) this.a.getSystemService("location");
    }
}
