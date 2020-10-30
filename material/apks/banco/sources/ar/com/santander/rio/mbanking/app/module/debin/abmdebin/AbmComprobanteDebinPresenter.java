package ar.com.santander.rio.mbanking.app.module.debin.abmdebin;

import ar.com.santander.rio.mbanking.app.base.BasePresenter;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.services.soap.beans.body.AbmDebinCompradorBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.AbmDebinVendedorBodyResponseBean;
import com.squareup.otto.Bus;

public class AbmComprobanteDebinPresenter extends BasePresenter<AbmComprobanteDebinView> {
    public AbmComprobanteDebinPresenter(Bus bus, IDataManager iDataManager) {
        super(bus, iDataManager);
    }

    public void onCreatePage(AbmDebinVendedorBodyResponseBean abmDebinVendedorBodyResponseBean, AbmDebinCompradorBodyResponseBean abmDebinCompradorBodyResponseBean) {
        ((AbmComprobanteDebinView) getBaseView()).setAbmComprobanteDebinView(abmDebinVendedorBodyResponseBean, abmDebinCompradorBodyResponseBean);
    }
}
