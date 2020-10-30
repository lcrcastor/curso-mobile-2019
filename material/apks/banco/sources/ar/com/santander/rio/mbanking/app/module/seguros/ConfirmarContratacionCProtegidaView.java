package ar.com.santander.rio.mbanking.app.module.seguros;

import android.content.Context;
import ar.com.santander.rio.mbanking.app.base.IBaseView;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ContratarCompraProtegidaBodyResponseBean;

public interface ConfirmarContratacionCProtegidaView extends IBaseView {
    void endActivity();

    AnalyticsManager getAnalyticsManager();

    Context getContext();

    void gotoComprobanteContratacion(ContratarCompraProtegidaBodyResponseBean contratarCompraProtegidaBodyResponseBean);

    void setConfirmarContratacionView();
}
