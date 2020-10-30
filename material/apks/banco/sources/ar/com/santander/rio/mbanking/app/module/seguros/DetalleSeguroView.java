package ar.com.santander.rio.mbanking.app.module.seguros;

import android.support.v4.app.FragmentManager;
import ar.com.santander.rio.mbanking.app.base.IBaseView;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.services.soap.beans.body.AsistenciasSeguroBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.SeguroBean;

public interface DetalleSeguroView extends IBaseView {
    AnalyticsManager getAnalyticsManager();

    FragmentManager getFragmentManager();

    void goToSolicitarAsistencia(AsistenciasSeguroBean asistenciasSeguroBean);

    void setDetalleSeguroView(String str, SeguroBean seguroBean);
}
