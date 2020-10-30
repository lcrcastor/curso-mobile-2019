package ar.com.santander.rio.mbanking.inject.modules;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import ar.com.santander.rio.mbanking.app.base.BaseApplication;
import ar.com.santander.rio.mbanking.inject.qualifiers.ExamplePreference;
import ar.com.santander.rio.mbanking.inject.qualifiers.FavoritoPreference;
import ar.com.santander.rio.mbanking.managers.preferences.Preferences;
import ar.com.santander.rio.mbanking.managers.preferences.StringPreference;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module(complete = false, library = true)
public class PreferencesModule {
    /* access modifiers changed from: 0000 */
    @Singleton
    @Provides
    public SharedPreferences a(BaseApplication baseApplication) {
        return PreferenceManager.getDefaultSharedPreferences(baseApplication);
    }

    /* access modifiers changed from: 0000 */
    @Singleton
    @ExamplePreference
    @Provides
    public StringPreference a(SharedPreferences sharedPreferences) {
        return new StringPreference(sharedPreferences, Preferences.PREFERENCE_ID, "");
    }

    /* access modifiers changed from: 0000 */
    @Singleton
    @FavoritoPreference
    @Provides
    public StringPreference b(SharedPreferences sharedPreferences) {
        return new StringPreference(sharedPreferences, Preferences.PREFERENCE_ID_FAVORITOS, "");
    }
}
