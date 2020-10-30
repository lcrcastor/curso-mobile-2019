package ar.com.santander.rio.mbanking.app.module.seguros;

import ar.com.santander.rio.mbanking.app.base.IBaseView;
import ar.com.santander.rio.mbanking.services.soap.beans.body.AsistenciasSeguroBean;

public interface SolicitarAsistenciaView extends IBaseView {
    void goToDetalleAsistencia(String str);

    void setSolicitarAsistenciaView(AsistenciasSeguroBean asistenciasSeguroBean);
}
