package ar.com.santander.rio.mbanking.managers.redessociales;

import android.app.Activity;

public interface IRedesSocialesManager {
    void registerFacebook(String str);

    void registerGooglePlus(String str);

    void registerTwitter(Activity activity, String str);
}
