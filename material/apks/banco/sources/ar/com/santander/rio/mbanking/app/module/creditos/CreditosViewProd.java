package ar.com.santander.rio.mbanking.app.module.creditos;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import ar.com.santander.rio.mbanking.app.commons.StepsView;
import ar.com.santander.rio.mbanking.app.module.creditos.model.InicioProd;
import ar.com.santander.rio.mbanking.app.module.creditos.model.ResultadoProd;
import ar.com.santander.rio.mbanking.app.module.creditos.model.SimulacionProd;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.services.model.creditos.DatosPrestamoPermitidoProd;
import ar.com.santander.rio.mbanking.services.model.creditos.DatosSolicitudPrestamoProd;
import ar.com.santander.rio.mbanking.services.model.general.Cuenta;
import ar.com.santander.rio.mbanking.services.soap.beans.body.SolicitudPrestamoPreacordadoBodyRequestBeanProd;
import java.util.ArrayList;

public interface CreditosViewProd extends StepsView {
    void addBlockBodySimulacion(View view);

    void clearImporte();

    void dismisProgress();

    Context getActContext();

    Activity getActivity();

    Cuenta getCuenta();

    IDataManager getDataManager();

    SessionManager getSessionManager();

    SolicitudPrestamoPreacordadoBodyRequestBeanProd getSolicitudSimulacion();

    void goToComprobante(DatosSolicitudPrestamoProd datosSolicitudPrestamoProd);

    void goToConfirmacion(DatosSolicitudPrestamoProd datosSolicitudPrestamoProd);

    void goToInicio();

    void goToSimulacion(DatosPrestamoPermitidoProd datosPrestamoPermitidoProd);

    void gotoPage(int i, boolean z);

    void openDestinoSelector();

    void setCantidadCuota(String str);

    void setComprobacionView(ResultadoProd resultadoProd);

    void setConfirmacionView(ResultadoProd resultadoProd);

    void setDestino(String str);

    void setInicioProdView(InicioProd inicioProd);

    void setPrimeraCuota(String str);

    void setSimulacionProdView(SimulacionProd simulacionProd);

    void setSolicitudSimulacion(SolicitudPrestamoPreacordadoBodyRequestBeanProd solicitudPrestamoPreacordadoBodyRequestBeanProd);

    void setTitleLayout(String str);

    void showCantidadCuotaPicker(ArrayList<String> arrayList, String str);

    void showDestinoPicker(ArrayList<String> arrayList, String str);

    void showMessage(String str, String str2);

    void showPrimeraCuotaPicker();

    void showProgress();
}
