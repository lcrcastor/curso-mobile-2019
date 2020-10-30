package ar.com.santander.rio.mbanking.app.module.seguros;

import android.content.Context;
import ar.com.santander.rio.mbanking.app.base.IBaseView;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ContratarSeguroMovilBodyResponseBean;

public interface ConfirmarContratacionView extends IBaseView {
    void endActivity();

    AnalyticsManager getAnalyticsManager();

    Context getContext();

    void gotoComprobanteContratacion(ContratarSeguroMovilBodyResponseBean contratarSeguroMovilBodyResponseBean);

    void setConfirmarContratacionView();
}
