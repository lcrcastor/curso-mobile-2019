package ar.com.santander.rio.mbanking.app.module.funds;

import android.content.Context;
import ar.com.santander.rio.mbanking.app.base.IBaseView;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.services.soap.beans.body.RescatarFondoBodyResponseBean;

public interface ConfirmarRescateFondoView extends IBaseView {
    AnalyticsManager getAnalyticsManager();

    Context getContext();

    void gotoComprobante(RescatarFondoBodyResponseBean rescatarFondoBodyResponseBean);

    void gotoFueraHorarioFondo(String str);

    void setConfirmarRescateFondoView(RescatarFondoBodyResponseBean rescatarFondoBodyResponseBean);
}
