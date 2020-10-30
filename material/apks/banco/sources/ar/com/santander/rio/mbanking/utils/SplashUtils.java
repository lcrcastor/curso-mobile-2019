package ar.com.santander.rio.mbanking.utils;

import android.content.Intent;
import android.text.TextUtils;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import ar.com.santander.rio.mbanking.app.ui.Constants.CUSTOM_URL_DOMAIN;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

public class SplashUtils {
    public static boolean hasDeepLink(Intent intent) {
        return intent.getData() != null && !TextUtils.isEmpty(intent.getData().getHost());
    }

    public static String getHostDomain(Intent intent) {
        return hasDeepLink(intent) ? intent.getData().getHost().split("&")[0].toUpperCase() : "";
    }

    public static boolean hasToken(String str) {
        return str.equals(CUSTOM_URL_DOMAIN.TOKENOBP);
    }

    public static boolean hasToken(Intent intent) {
        return getHostDomain(intent).equals(CUSTOM_URL_DOMAIN.TOKENOBP);
    }

    public static Animation getEndAnimation(AnimationListener animationListener, float f) {
        TranslateAnimation translateAnimation = new TranslateAnimation(BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, f);
        translateAnimation.setFillAfter(true);
        translateAnimation.setFillEnabled(true);
        translateAnimation.setAnimationListener(animationListener);
        translateAnimation.setDuration(800);
        return translateAnimation;
    }
}
