package ar.com.santander.rio.mbanking.view;

import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

public class SlideAnimationViewFlipper {
    public static Animation inFromRightAnimation() {
        TranslateAnimation translateAnimation = new TranslateAnimation(2, 1.0f, 2, BitmapDescriptorFactory.HUE_RED, 2, BitmapDescriptorFactory.HUE_RED, 2, BitmapDescriptorFactory.HUE_RED);
        translateAnimation.setDuration(500);
        translateAnimation.setInterpolator(new AccelerateInterpolator());
        return translateAnimation;
    }

    public static Animation outToLeftAnimation() {
        TranslateAnimation translateAnimation = new TranslateAnimation(2, BitmapDescriptorFactory.HUE_RED, 2, -1.0f, 2, BitmapDescriptorFactory.HUE_RED, 2, BitmapDescriptorFactory.HUE_RED);
        translateAnimation.setDuration(500);
        translateAnimation.setInterpolator(new AccelerateInterpolator());
        return translateAnimation;
    }

    public static Animation inFromLeftAnimation() {
        TranslateAnimation translateAnimation = new TranslateAnimation(2, -1.0f, 2, BitmapDescriptorFactory.HUE_RED, 2, BitmapDescriptorFactory.HUE_RED, 2, BitmapDescriptorFactory.HUE_RED);
        translateAnimation.setDuration(500);
        translateAnimation.setInterpolator(new AccelerateInterpolator());
        return translateAnimation;
    }

    public static Animation outToRightAnimation() {
        TranslateAnimation translateAnimation = new TranslateAnimation(2, BitmapDescriptorFactory.HUE_RED, 2, 1.0f, 2, BitmapDescriptorFactory.HUE_RED, 2, BitmapDescriptorFactory.HUE_RED);
        translateAnimation.setDuration(500);
        translateAnimation.setInterpolator(new AccelerateInterpolator());
        return translateAnimation;
    }

    public static Animation inFromDownAnimation() {
        TranslateAnimation translateAnimation = new TranslateAnimation(2, BitmapDescriptorFactory.HUE_RED, 2, BitmapDescriptorFactory.HUE_RED, 2, 1.0f, 2, BitmapDescriptorFactory.HUE_RED);
        translateAnimation.setDuration(500);
        translateAnimation.setInterpolator(new AccelerateInterpolator());
        return translateAnimation;
    }

    public static Animation outToUpAnimation() {
        TranslateAnimation translateAnimation = new TranslateAnimation(2, BitmapDescriptorFactory.HUE_RED, 2, BitmapDescriptorFactory.HUE_RED, 2, BitmapDescriptorFactory.HUE_RED, 2, -1.0f);
        translateAnimation.setDuration(500);
        translateAnimation.setInterpolator(new AccelerateInterpolator());
        return translateAnimation;
    }

    public static Animation inFromUpAnimation() {
        TranslateAnimation translateAnimation = new TranslateAnimation(2, BitmapDescriptorFactory.HUE_RED, 2, BitmapDescriptorFactory.HUE_RED, 2, -1.0f, 2, BitmapDescriptorFactory.HUE_RED);
        translateAnimation.setDuration(500);
        translateAnimation.setInterpolator(new AccelerateInterpolator());
        return translateAnimation;
    }

    public static Animation outToDownAnimation() {
        TranslateAnimation translateAnimation = new TranslateAnimation(2, BitmapDescriptorFactory.HUE_RED, 2, BitmapDescriptorFactory.HUE_RED, 2, BitmapDescriptorFactory.HUE_RED, 2, 1.0f);
        translateAnimation.setDuration(500);
        translateAnimation.setInterpolator(new AccelerateInterpolator());
        return translateAnimation;
    }

    public static Animation noAnimation() {
        TranslateAnimation translateAnimation = new TranslateAnimation(2, BitmapDescriptorFactory.HUE_RED, 2, BitmapDescriptorFactory.HUE_RED, 2, BitmapDescriptorFactory.HUE_RED, 2, BitmapDescriptorFactory.HUE_RED);
        translateAnimation.setDuration(500);
        translateAnimation.setInterpolator(new AccelerateInterpolator());
        return translateAnimation;
    }
}
