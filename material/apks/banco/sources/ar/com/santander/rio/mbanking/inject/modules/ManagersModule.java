package ar.com.santander.rio.mbanking.inject.modules;

import ar.com.santander.rio.mbanking.app.base.BaseApplication;
import ar.com.santander.rio.mbanking.app.module.accounts.analytics.AccountAnalytics;
import ar.com.santander.rio.mbanking.app.module.accounts.analytics.AccountAnalyticsImpl;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.data.DataManager;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.managers.data.MockableDataManager;
import ar.com.santander.rio.mbanking.managers.notifications.PushNotificationsManager;
import ar.com.santander.rio.mbanking.managers.preferences.SettingsManager;
import ar.com.santander.rio.mbanking.managers.redessociales.IRedesSocialesManager;
import ar.com.santander.rio.mbanking.managers.redessociales.RedesSocialesManager;
import ar.com.santander.rio.mbanking.managers.security.SoftTokenManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.services.soap.environments.ManagerTypeEnvironment;
import ar.com.santander.rio.mbanking.services.soap.environments.ManagerTypeEnvironment.Environment;
import com.squareup.otto.Bus;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module(complete = false, library = true)
public class ManagersModule {
    /* access modifiers changed from: 0000 */
    @Singleton
    @Provides
    public IDataManager a(BaseApplication baseApplication, Bus bus, SessionManager sessionManager, SettingsManager settingsManager) {
        return ManagerTypeEnvironment.getCurrentEnvironment(baseApplication.getApplicationContext()).getEnvironmentType().equals(Environment.MOCKS) ? new MockableDataManager(baseApplication, bus, sessionManager, settingsManager) : new DataManager(baseApplication, bus, sessionManager, settingsManager);
    }

    /* access modifiers changed from: 0000 */
    @Singleton
    @Provides
    public IRedesSocialesManager a(BaseApplication baseApplication) {
        return new RedesSocialesManager(baseApplication);
    }

    /* access modifiers changed from: 0000 */
    @Singleton
    @Provides
    public SettingsManager b(BaseApplication baseApplication) {
        return new SettingsManager(baseApplication.getApplicationContext());
    }

    /* access modifiers changed from: 0000 */
    @Singleton
    @Provides
    public SessionManager a(BaseApplication baseApplication, SettingsManager settingsManager) {
        return new SessionManager(baseApplication, settingsManager);
    }

    /* access modifiers changed from: 0000 */
    @Singleton
    @Provides
    public PushNotificationsManager c(BaseApplication baseApplication) {
        return new PushNotificationsManager(baseApplication);
    }

    /* access modifiers changed from: 0000 */
    @Singleton
    @Provides
    public AnalyticsManager a(BaseApplication baseApplication, SessionManager sessionManager) {
        return new AnalyticsManager(baseApplication, sessionManager);
    }

    /* access modifiers changed from: 0000 */
    @Singleton
    @Provides
    public AccountAnalytics a(BaseApplication baseApplication, AnalyticsManager analyticsManager) {
        return new AccountAnalyticsImpl(baseApplication, analyticsManager);
    }

    /* access modifiers changed from: 0000 */
    @Singleton
    @Provides
    public SoftTokenManager d(BaseApplication baseApplication) {
        return new SoftTokenManager(baseApplication);
    }
}
