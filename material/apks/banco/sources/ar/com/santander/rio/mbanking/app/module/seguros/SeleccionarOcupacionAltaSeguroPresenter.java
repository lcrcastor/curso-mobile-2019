package ar.com.santander.rio.mbanking.app.module.seguros;

import ar.com.santander.rio.mbanking.app.base.BasePresenter;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetOcupacionesBodyResponseBean;
import com.squareup.otto.Bus;

public class SeleccionarOcupacionAltaSeguroPresenter extends BasePresenter<SeleccionarOcupacionAltaSeguroView> {
    public SeleccionarOcupacionAltaSeguroPresenter(Bus bus) {
        super(bus);
    }

    public void onCreatePage(GetOcupacionesBodyResponseBean getOcupacionesBodyResponseBean) {
        ((SeleccionarOcupacionAltaSeguroView) getBaseView()).setSeleccionarOcupacionView();
    }
}
