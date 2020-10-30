package ar.com.santander.rio.mbanking.app.module.creditCards;

import ar.com.santander.rio.mbanking.app.commons.StepsView;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetViajesBodyResponseBean;
import java.util.HashMap;

public interface TarjetasView extends StepsView {
    void dismissProgress();

    IDataManager getDataManager();

    SessionManager getSessionManager();

    void goToTarjetas();

    void goToTarjetasDetalleConsumo(String str, HashMap<String, String> hashMap);

    void goToTarjetasLimitesDisponibles();

    void goToTarjetasMarcacionPorViaje(GetViajesBodyResponseBean getViajesBodyResponseBean);

    void goToTarjetasMarcacionPorViajeRes4(String str, String str2);

    void goToTarjetasTerminosCondiciones();

    void goToTarjetasUltimoResumen();

    void goToTarjetasUltimosConsumos();

    void setTarjetasDetalleConsumoView(String str, HashMap<String, String> hashMap);

    void setTarjetasLimitesDisponiblesView();

    void setTarjetasTerminosCondicionesView();

    void setTarjetasUltimoResumenView();

    void setTarjetasUltimosConsumosView();

    void showMessage(String str, String str2);

    void showProgress();
}
