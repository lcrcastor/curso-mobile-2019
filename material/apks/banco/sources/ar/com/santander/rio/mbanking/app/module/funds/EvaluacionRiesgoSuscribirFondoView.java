package ar.com.santander.rio.mbanking.app.module.funds;

import android.app.FragmentManager;
import android.content.Context;
import ar.com.santander.rio.mbanking.app.base.IBaseView;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.services.soap.beans.body.AlertaEvaluacionRiesgoBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.SuscribirFondoBodyResponseBean;

public interface EvaluacionRiesgoSuscribirFondoView extends IBaseView {
    AnalyticsManager getAnalyticsManager();

    Context getContext();

    FragmentManager getFragmentManager();

    SessionManager getSessionManager();

    void gotoComprobante(SuscribirFondoBodyResponseBean suscribirFondoBodyResponseBean);

    void setEvaluacionRiesgoSuscripcionView(AlertaEvaluacionRiesgoBean alertaEvaluacionRiesgoBean, String str, String str2);
}
