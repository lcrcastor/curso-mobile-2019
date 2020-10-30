package ar.com.santander.rio.mbanking.app.module.creditos;

import android.view.View;
import ar.com.santander.rio.mbanking.app.module.creditos.model.Simulacion;
import ar.com.santander.rio.mbanking.services.events.SolicitudPrestamoPreacordadoEvent;
import ar.com.santander.rio.mbanking.services.model.creditos.DatosPrestamoPermitido;
import ar.com.santander.rio.mbanking.services.soap.beans.body.AccountRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ConsultaPrestamosPermitidosBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ConsultaSolicitudCrediticiaBodyResponseBean;
import java.util.Date;

public interface SimulacionPresenter {
    String getDefaultDate();

    String getDestinoSelected();

    void getResponseSimulacionPrestamoPermitido(SolicitudPrestamoPreacordadoEvent solicitudPrestamoPreacordadoEvent, ConsultaPrestamosPermitidosBodyResponseBean consultaPrestamosPermitidosBodyResponseBean);

    View getViewForm(Simulacion simulacion);

    void onCantidadCuotasClicked();

    void onCreatePage(ConsultaSolicitudCrediticiaBodyResponseBean consultaSolicitudCrediticiaBodyResponseBean, ConsultaPrestamosPermitidosBodyResponseBean consultaPrestamosPermitidosBodyResponseBean, DatosPrestamoPermitido datosPrestamoPermitido);

    void onDestinoSelected(String str);

    void onDestinosClicked();

    void onNumCuotasSelected(String str);

    void onPrimeraCuotaClicked();

    void onPrimeraCuotaSelected(Date date);

    void sendRequestSimulacionPrestamoPermitido();

    void setCuentaSelected(AccountRequestBean accountRequestBean);

    void validate(String str);
}
