package ar.com.santander.rio.mbanking.app.module.seguros;

import ar.com.santander.rio.mbanking.app.base.BasePresenter;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import com.squareup.otto.Bus;

public class DetalleAsistenciaPresenter extends BasePresenter<DetalleAsistenciaView> {
    public DetalleAsistenciaPresenter(Bus bus, IDataManager iDataManager) {
        super(bus, iDataManager);
    }

    public void onCreatePage(String str) {
        ((DetalleAsistenciaView) getBaseView()).setDetalleAsistenciaView(str);
    }
}
