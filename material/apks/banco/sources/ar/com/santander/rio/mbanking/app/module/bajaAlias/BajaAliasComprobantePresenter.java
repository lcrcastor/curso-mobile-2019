package ar.com.santander.rio.mbanking.app.module.bajaAlias;

import ar.com.santander.rio.mbanking.app.base.BasePresenter;
import com.squareup.otto.Bus;

public class BajaAliasComprobantePresenter extends BasePresenter<BajaAliasComprobanteView> {
    public BajaAliasComprobantePresenter(Bus bus) {
        super(bus);
    }

    public void onCreatePage(String str, String str2, String str3) {
        ((BajaAliasComprobanteView) getBaseView()).setBajaAliasComprobanteView(str, str2, str3);
    }
}
