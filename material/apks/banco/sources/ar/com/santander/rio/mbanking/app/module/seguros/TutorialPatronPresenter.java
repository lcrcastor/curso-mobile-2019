package ar.com.santander.rio.mbanking.app.module.seguros;

import ar.com.santander.rio.mbanking.app.base.BasePresenter;
import com.squareup.otto.Bus;

public class TutorialPatronPresenter extends BasePresenter<TutorialPatronView> {
    public TutorialPatronPresenter(Bus bus) {
        super(bus);
    }

    public void onCreatePage() {
        ((TutorialPatronView) getBaseView()).initTutorial();
    }
}
