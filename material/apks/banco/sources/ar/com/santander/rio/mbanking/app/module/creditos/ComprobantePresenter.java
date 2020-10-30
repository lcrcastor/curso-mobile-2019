package ar.com.santander.rio.mbanking.app.module.creditos;

import ar.com.santander.rio.mbanking.services.soap.beans.body.AccountRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ConsultaPrestamosPermitidosBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.SolicitudPrestamoPreacordadoBodyResponseBean;

public interface ComprobantePresenter {
    void onCreatePage(SolicitudPrestamoPreacordadoBodyResponseBean solicitudPrestamoPreacordadoBodyResponseBean, AccountRequestBean accountRequestBean, ConsultaPrestamosPermitidosBodyResponseBean consultaPrestamosPermitidosBodyResponseBean);
}
