package ar.com.santander.rio.mbanking.app.module.creditos;

import ar.com.santander.rio.mbanking.services.events.SolicitudPrestamoPreacordadoEvent;
import ar.com.santander.rio.mbanking.services.model.creditos.DatosSolicitudPrestamo;
import ar.com.santander.rio.mbanking.services.soap.beans.body.AccountRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ConsultaPrestamosPermitidosBodyResponseBean;

public interface ConfirmacionPresenter {
    void getResponseConfirmacionPrestamoPermitido(SolicitudPrestamoPreacordadoEvent solicitudPrestamoPreacordadoEvent, ConsultaPrestamosPermitidosBodyResponseBean consultaPrestamosPermitidosBodyResponseBean);

    void onCreatePage(DatosSolicitudPrestamo datosSolicitudPrestamo, AccountRequestBean accountRequestBean, ConsultaPrestamosPermitidosBodyResponseBean consultaPrestamosPermitidosBodyResponseBean);

    void sendRequestConfirmacionPrestamoPermitido();
}
