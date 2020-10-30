package ar.com.santander.rio.mbanking.app.module.modificacionAlias;

import ar.com.santander.rio.mbanking.app.base.BasePresenter;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import com.squareup.otto.Bus;

public class ModificacionAliasPresenter extends BasePresenter<ModificacionAliasView> {
    private ModificacionAliasView a;

    public ModificacionAliasPresenter(Bus bus, IDataManager iDataManager) {
        super(bus, iDataManager);
    }

    public void onCreatePage(ModificacionAliasView modificacionAliasView) {
        this.a = modificacionAliasView;
    }
}
