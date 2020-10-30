package ar.com.santander.rio.mbanking.app.commons;

import android.view.animation.Animation;
import ar.com.santander.rio.mbanking.view.SlideAnimationViewFlipper;

public class CPageAnimationViewFlipper {
    public static Animation getNextInAnimation() {
        return SlideAnimationViewFlipper.inFromRightAnimation();
    }

    public static Animation getNextOutAnimation() {
        return SlideAnimationViewFlipper.outToLeftAnimation();
    }

    public static Animation getPreviusInAnimation() {
        return SlideAnimationViewFlipper.inFromLeftAnimation();
    }

    public static Animation getPreviusOutAnimation() {
        return SlideAnimationViewFlipper.outToRightAnimation();
    }
}
