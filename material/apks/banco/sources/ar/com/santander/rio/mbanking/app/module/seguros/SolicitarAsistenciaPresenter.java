package ar.com.santander.rio.mbanking.app.module.seguros;

import ar.com.santander.rio.mbanking.app.base.BasePresenter;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.services.soap.beans.body.AsistenciasSeguroBean;
import com.squareup.otto.Bus;

public class SolicitarAsistenciaPresenter extends BasePresenter<SolicitarAsistenciaView> {
    public SolicitarAsistenciaPresenter(Bus bus, IDataManager iDataManager) {
        super(bus, iDataManager);
    }

    public void onCreatePage(AsistenciasSeguroBean asistenciasSeguroBean) {
        ((SolicitarAsistenciaView) getBaseView()).setSolicitarAsistenciaView(asistenciasSeguroBean);
    }
}
