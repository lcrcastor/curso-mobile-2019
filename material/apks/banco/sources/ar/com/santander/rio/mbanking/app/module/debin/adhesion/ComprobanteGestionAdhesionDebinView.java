package ar.com.santander.rio.mbanking.app.module.debin.adhesion;

import ar.com.santander.rio.mbanking.app.base.IBaseView;
import ar.com.santander.rio.mbanking.services.soap.beans.body.AbmAdhesionVendedorBodyResponseBean;

public interface ComprobanteGestionAdhesionDebinView extends IBaseView {
    void setComprobanteGestionAdhesionDebinView(AbmAdhesionVendedorBodyResponseBean abmAdhesionVendedorBodyResponseBean, Integer num);
}
