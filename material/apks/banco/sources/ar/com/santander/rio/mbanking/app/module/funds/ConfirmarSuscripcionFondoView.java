package ar.com.santander.rio.mbanking.app.module.funds;

import android.content.Context;
import ar.com.santander.rio.mbanking.app.base.IBaseView;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.services.soap.beans.body.AlertaEvaluacionRiesgoBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.SimularSuscripcionFondoBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.SuscribirFondoBodyResponseBean;

public interface ConfirmarSuscripcionFondoView extends IBaseView {
    AnalyticsManager getAnalyticsManager();

    Context getContext();

    SessionManager getSessionManager();

    void gotoComprobante(SuscribirFondoBodyResponseBean suscribirFondoBodyResponseBean);

    void gotoEvaluacionRiesgoSuscripcionFondo(AlertaEvaluacionRiesgoBean alertaEvaluacionRiesgoBean, String str, String str2);

    void gotoFueraHorarioFondo(String str);

    void setConfirmarSuscripcionView(SimularSuscripcionFondoBodyResponseBean simularSuscripcionFondoBodyResponseBean);

    void showConfirmDialog(String str);
}
