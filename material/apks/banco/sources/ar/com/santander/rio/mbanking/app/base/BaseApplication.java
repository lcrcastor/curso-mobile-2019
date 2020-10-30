package ar.com.santander.rio.mbanking.app.base;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Build.VERSION;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.ui.Constants;
import ar.com.santander.rio.mbanking.inject.GraphProvider;
import ar.com.santander.rio.mbanking.inject.Modules;
import ar.com.santander.rio.mbanking.managers.preferences.SecuredPreferences;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent;
import com.appsflyer.AppsFlyerConversionListener;
import com.appsflyer.AppsFlyerLib;
import com.crashlytics.android.Crashlytics;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import dagger.ObjectGraph;
import io.fabric.sdk.android.Fabric;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;
import net.danlew.android.joda.JodaTimeAndroid;

public class BaseApplication extends MultiDexApplication implements GraphProvider {
    private static boolean a;
    private static ArrayList<WebServiceEvent> b;
    private ObjectGraph c;

    public static boolean isActivityVisible() {
        return a;
    }

    public static void activityResumed() {
        a = true;
    }

    public static void activityPaused() {
        a = false;
    }

    public static void addPendingEvents(WebServiceEvent webServiceEvent) {
        if (b == null) {
            b = new ArrayList<>();
        }
        b.add(webServiceEvent);
    }

    public static ArrayList<WebServiceEvent> getPendingEvents() {
        return b;
    }

    public static void setPendingEvents(ArrayList<WebServiceEvent> arrayList) {
        b = arrayList;
    }

    public static BaseApplication get(Context context) {
        return (BaseApplication) context.getApplicationContext();
    }

    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        Crashlytics.setString("exception", "CAUGHT");
        a(Constants.LOCALE_DEFAULT_APP);
        AppsFlyerLib.getInstance().init(getApplicationContext().getString(R.string.APPSFLYER_MOBEXT_KEY), new AppsFlyerConversionListener() {
            public void onAppOpenAttribution(Map<String, String> map) {
            }

            public void onAttributionFailure(String str) {
            }

            public void onInstallConversionDataLoaded(Map<String, String> map) {
            }

            public void onInstallConversionFailure(String str) {
            }
        }, getApplicationContext());
        AppsFlyerLib.getInstance().startTracking(this);
        JodaTimeAndroid.init(this);
        Thread.setDefaultUncaughtExceptionHandler(new BaseUncaughtExceptionHandler(this));
        Logger.addLogAdapter(new AndroidLogAdapter());
        a();
    }

    /* access modifiers changed from: 0000 */
    public void a(Locale locale) {
        Locale.setDefault(locale);
        a(getBaseContext().getResources().getConfiguration(), locale);
    }

    /* access modifiers changed from: 0000 */
    public void a(Configuration configuration, Locale locale) {
        if (VERSION.SDK_INT >= 17) {
            configuration.setLocale(locale);
        } else {
            configuration.locale = locale;
        }
    }

    public ObjectGraph getGraph() {
        if (this.c == null) {
            this.c = ObjectGraph.create(Modules.listModulesForApplication(this));
        }
        return this.c;
    }

    public synchronized Tracker getTracker() {
        return GoogleAnalytics.getInstance(this).newTracker((int) R.xml.analytics_release);
    }

    /* access modifiers changed from: protected */
    public void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);
    }

    private void a() {
        SecuredPreferences instance = SecuredPreferences.getInstance(this);
        if (instance.isEmpty()) {
            instance.performMigration(this);
        }
        instance.publicDateMigration(this);
    }
}
