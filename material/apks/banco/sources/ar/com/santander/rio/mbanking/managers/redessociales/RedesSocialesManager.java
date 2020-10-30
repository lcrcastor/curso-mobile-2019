package ar.com.santander.rio.mbanking.managers.redessociales;

import android.app.Activity;
import ar.com.santander.rio.mbanking.app.base.BaseApplication;
import com.facebook.AppEventsLogger;
import com.grab.Grab.Grab;

public class RedesSocialesManager implements IRedesSocialesManager {
    BaseApplication a;

    public void registerGooglePlus(String str) {
    }

    public RedesSocialesManager(BaseApplication baseApplication) {
        this.a = baseApplication;
    }

    public void registerFacebook(String str) {
        AppEventsLogger.activateApp(this.a.getApplicationContext(), str);
    }

    public void registerTwitter(Activity activity, String str) {
        Grab.init(activity, str, false);
    }
}
