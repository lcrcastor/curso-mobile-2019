package ar.com.santander.rio.mbanking.app.module.modificacionAlias;

import ar.com.santander.rio.mbanking.app.base.BasePresenter;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import com.squareup.otto.Bus;

public class ModificacionAliasComprobantePresenter extends BasePresenter<ModificacionAliasComprobanteView> {
    public ModificacionAliasComprobantePresenter(Bus bus, IDataManager iDataManager) {
        super(bus, iDataManager);
    }

    public void onCreatePage(String str, String str2, String str3) {
        ((ModificacionAliasComprobanteView) getBaseView()).setModificacionAliasComprobanteView(str, str2, str3);
    }
}
