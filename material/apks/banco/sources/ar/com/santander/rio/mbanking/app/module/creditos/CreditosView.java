package ar.com.santander.rio.mbanking.app.module.creditos;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import ar.com.santander.rio.mbanking.app.commons.StepsView;
import ar.com.santander.rio.mbanking.app.module.creditos.model.Inicio;
import ar.com.santander.rio.mbanking.app.module.creditos.model.Resultado;
import ar.com.santander.rio.mbanking.app.module.creditos.model.Simulacion;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.services.model.creditos.DatosPrestamoPermitido;
import ar.com.santander.rio.mbanking.services.model.creditos.DatosSolicitudPrestamo;
import ar.com.santander.rio.mbanking.services.model.general.Cuenta;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ConsultaPrestamosPermitidosBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.SolicitudPrestamoPreacordadoBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.SolicitudPrestamoPreacordadoBodyResponseBean;
import java.util.ArrayList;

public interface CreditosView extends StepsView {
    void addBlockBodySimulacion(View view);

    void clearImporte();

    void dismisProgress();

    Context getActContext();

    Activity getActivity();

    Cuenta getCuenta();

    IDataManager getDataManager();

    SessionManager getSessionManager();

    SolicitudPrestamoPreacordadoBodyRequestBean getSolicitudSimulacion();

    String getTipoCta();

    void goToComprobante(SolicitudPrestamoPreacordadoBodyResponseBean solicitudPrestamoPreacordadoBodyResponseBean, ConsultaPrestamosPermitidosBodyResponseBean consultaPrestamosPermitidosBodyResponseBean);

    void goToConfirmacion(DatosSolicitudPrestamo datosSolicitudPrestamo, ConsultaPrestamosPermitidosBodyResponseBean consultaPrestamosPermitidosBodyResponseBean);

    void goToInicio();

    void goToSimulacion(DatosPrestamoPermitido datosPrestamoPermitido);

    void gotoPage(int i, boolean z);

    void openDestinoSelector();

    void setCantidadCuota(String str);

    void setComprobacionView(Resultado resultado);

    void setConfirmacionView(Resultado resultado);

    void setDestino(String str);

    void setInicioView(Inicio inicio);

    void setPrimeraCuota(String str);

    void setSimulacionView(Simulacion simulacion);

    void setSolicitudSimulacion(SolicitudPrestamoPreacordadoBodyRequestBean solicitudPrestamoPreacordadoBodyRequestBean);

    void setTipoCta(String str);

    void setTitleContentDescription(String str);

    void setTitleLayout(String str);

    void showCantidadCuotaPicker(ArrayList<String> arrayList, String str);

    void showDestinoPicker(ArrayList<String> arrayList, String str);

    void showMessage(String str, String str2);

    void showPrimeraCuotaPicker();

    void showProgress();
}
