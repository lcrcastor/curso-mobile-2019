package ar.com.santander.rio.mbanking.app.module.debin.adhesion;

import ar.com.santander.rio.mbanking.app.base.BasePresenter;
import ar.com.santander.rio.mbanking.services.soap.beans.body.AbmAdhesionVendedorBodyResponseBean;
import com.squareup.otto.Bus;

public class ComprobanteGestionAdhesionDebinPresenter extends BasePresenter<ComprobanteGestionAdhesionDebinView> {
    public ComprobanteGestionAdhesionDebinPresenter(Bus bus) {
        super(bus);
    }

    public void onCreatePage(AbmAdhesionVendedorBodyResponseBean abmAdhesionVendedorBodyResponseBean, Integer num) {
        ((ComprobanteGestionAdhesionDebinView) getBaseView()).setComprobanteGestionAdhesionDebinView(abmAdhesionVendedorBodyResponseBean, num);
    }
}
