package ar.com.santander.rio.mbanking.app.module.softtoken;

import android.widget.ViewFlipper;
import ar.com.santander.rio.mbanking.view.SlideAnimationViewFlipper;

public abstract class SoftTokenPage implements ISoftTokenPage {
    private ViewFlipper a;

    public SoftTokenPage(ViewFlipper viewFlipper) {
        this.a = viewFlipper;
    }

    private void a(int i) {
        switch (i) {
            case 0:
                this.a.setInAnimation(null);
                this.a.setOutAnimation(null);
                return;
            case 1:
                this.a.setInAnimation(SlideAnimationViewFlipper.inFromLeftAnimation());
                this.a.setOutAnimation(SlideAnimationViewFlipper.outToRightAnimation());
                return;
            case 2:
                this.a.setInAnimation(SlideAnimationViewFlipper.inFromRightAnimation());
                this.a.setOutAnimation(SlideAnimationViewFlipper.outToLeftAnimation());
                return;
            case 3:
                this.a.setInAnimation(SlideAnimationViewFlipper.inFromUpAnimation());
                this.a.setOutAnimation(SlideAnimationViewFlipper.outToDownAnimation());
                return;
            case 4:
                this.a.setInAnimation(SlideAnimationViewFlipper.inFromDownAnimation());
                this.a.setOutAnimation(SlideAnimationViewFlipper.outToUpAnimation());
                return;
            default:
                return;
        }
    }

    public void nextPage() {
        nextPage(2);
    }

    public void nextPage(int i) {
        if (this.a.indexOfChild(this.a.getCurrentView()) + 1 != this.a.getChildCount()) {
            goToPage(Integer.valueOf(this.a.indexOfChild(this.a.getCurrentView()) + 1), i);
        }
    }

    public void previousPage() {
        previousPage(1);
    }

    public void previousPage(int i) {
        if (this.a.indexOfChild(this.a.getCurrentView()) + 1 != 1) {
            goToPage(Integer.valueOf(this.a.indexOfChild(this.a.getCurrentView()) - 1), i);
        }
    }

    public void goToPage(Integer num) {
        goToPage(num, 0);
    }

    public void goToPage(Integer num, int i) {
        a(i);
        this.a.setDisplayedChild(num.intValue());
    }

    public void goToPage(Integer num, ISoftTokenPage iSoftTokenPage, int i) {
        onFinishPage();
        iSoftTokenPage.onCreatePage();
        goToPage(num, i);
    }
}
