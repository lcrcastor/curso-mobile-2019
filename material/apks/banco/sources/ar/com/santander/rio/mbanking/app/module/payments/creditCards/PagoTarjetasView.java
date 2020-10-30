package ar.com.santander.rio.mbanking.app.module.payments.creditCards;

import ar.com.santander.rio.mbanking.app.commons.StepsView;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;

public interface PagoTarjetasView extends StepsView {
    void dismissProgress();

    IDataManager getDataManager();

    SessionManager getSessionManager();

    void goToAyudaTarjeta();

    void goToComprobantePago(String str);

    void goToComprobanteStopDebit(String str, String str2);

    void goToConfirmarPago();

    void goToConfirmarStopDebit();

    void goToDetalleTarjetaPagoTotalDolares();

    void goToDetalleTarjetaPagoTotalPesos();

    void goToPagoTarjetas();

    void goToPrepararPago(int i);

    void setComprobantePagoView(String str);

    void setComprobanteStopDebitView(String str, String str2);

    void setConfirmarPagoView();

    void setConfirmarStopDebitView();

    void setDetalleTarjetaPagoTotalDolaresView();

    void setDetalleTarjetaPagoTotalPesosView();

    void setPrepararPagoView(int i);

    void showMessage(String str, String str2);

    void showProgress();
}
