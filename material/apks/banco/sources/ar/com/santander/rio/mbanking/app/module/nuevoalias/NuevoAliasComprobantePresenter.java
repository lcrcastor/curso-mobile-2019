package ar.com.santander.rio.mbanking.app.module.nuevoalias;

import ar.com.santander.rio.mbanking.app.base.BasePresenter;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import com.squareup.otto.Bus;

public class NuevoAliasComprobantePresenter extends BasePresenter<NuevoAliasComprobanteView> {
    public NuevoAliasComprobantePresenter(Bus bus, IDataManager iDataManager) {
        super(bus, iDataManager);
    }

    public void onCreatePage(String str, String str2, String str3) {
        ((NuevoAliasComprobanteView) getBaseView()).setNuevoAliasComprobanteView(str, str2, str3);
    }
}
