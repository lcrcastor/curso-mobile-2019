package ar.com.santander.rio.mbanking.app.module.funds;

import ar.com.santander.rio.mbanking.app.base.BasePresenter;
import com.squareup.otto.Bus;

public class FueraHorarioFondoPresenter extends BasePresenter<FueraHorarioFondoView> {
    public FueraHorarioFondoPresenter(Bus bus) {
        super(bus);
    }

    public void onCreatePage(String str) {
        ((FueraHorarioFondoView) getBaseView()).setFueraHorarioView(str);
    }
}
