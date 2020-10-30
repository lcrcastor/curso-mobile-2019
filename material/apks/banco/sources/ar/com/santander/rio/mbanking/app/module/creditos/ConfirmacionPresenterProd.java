package ar.com.santander.rio.mbanking.app.module.creditos;

import ar.com.santander.rio.mbanking.services.events.SolicitudPrestamoPreacordadoEventProd;
import ar.com.santander.rio.mbanking.services.model.creditos.DatosSolicitudPrestamoProd;

public interface ConfirmacionPresenterProd {
    void getResponseConfirmacionPrestamoPermitido(SolicitudPrestamoPreacordadoEventProd solicitudPrestamoPreacordadoEventProd);

    void onCreatePage(DatosSolicitudPrestamoProd datosSolicitudPrestamoProd);

    void sendRequestConfirmacionPrestamoPermitido();
}
