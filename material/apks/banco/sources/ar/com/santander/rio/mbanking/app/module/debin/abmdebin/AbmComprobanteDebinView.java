package ar.com.santander.rio.mbanking.app.module.debin.abmdebin;

import ar.com.santander.rio.mbanking.app.base.IBaseView;
import ar.com.santander.rio.mbanking.services.soap.beans.body.AbmDebinCompradorBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.AbmDebinVendedorBodyResponseBean;

public interface AbmComprobanteDebinView extends IBaseView {
    void setAbmComprobanteDebinView(AbmDebinVendedorBodyResponseBean abmDebinVendedorBodyResponseBean, AbmDebinCompradorBodyResponseBean abmDebinCompradorBodyResponseBean);
}
