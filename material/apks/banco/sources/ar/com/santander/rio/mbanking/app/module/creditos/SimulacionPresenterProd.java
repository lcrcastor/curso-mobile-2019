package ar.com.santander.rio.mbanking.app.module.creditos;

import android.view.View;
import ar.com.santander.rio.mbanking.app.module.creditos.model.SimulacionProd;
import ar.com.santander.rio.mbanking.services.events.SolicitudPrestamoPreacordadoEventProd;
import ar.com.santander.rio.mbanking.services.model.creditos.DatosPrestamoPermitidoProd;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ConsultaPrestamosPermitidosBodyResponseBeanProd;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ConsultaSolicitudCrediticiaBodyResponseBeanProd;
import java.util.Date;

public interface SimulacionPresenterProd {
    String getDefaultDate();

    void getResponseSimulacionPrestamoPermitido(SolicitudPrestamoPreacordadoEventProd solicitudPrestamoPreacordadoEventProd);

    View getViewForm(SimulacionProd simulacionProd);

    void onCantidadCuotasClicked();

    void onCreatePage(ConsultaSolicitudCrediticiaBodyResponseBeanProd consultaSolicitudCrediticiaBodyResponseBeanProd, ConsultaPrestamosPermitidosBodyResponseBeanProd consultaPrestamosPermitidosBodyResponseBeanProd, DatosPrestamoPermitidoProd datosPrestamoPermitidoProd);

    void onDestinoSelected(String str);

    void onDestinosClicked();

    void onNumCuotasSelected(String str);

    void onPrimeraCuotaClicked();

    void onPrimeraCuotaSelected(Date date);

    void sendRequestSimulacionPrestamoPermitido();

    void validate(String str);
}
