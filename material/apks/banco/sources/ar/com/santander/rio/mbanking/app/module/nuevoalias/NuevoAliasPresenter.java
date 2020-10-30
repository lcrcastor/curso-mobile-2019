package ar.com.santander.rio.mbanking.app.module.nuevoalias;

import ar.com.santander.rio.mbanking.app.base.BasePresenter;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import com.squareup.otto.Bus;

public class NuevoAliasPresenter extends BasePresenter<NuevoAliasView> {
    private NuevoAliasView a;

    public NuevoAliasPresenter(Bus bus, IDataManager iDataManager) {
        super(bus, iDataManager);
    }

    public void onCreatePage(NuevoAliasView nuevoAliasView) {
        this.a = nuevoAliasView;
    }
}
