package ar.com.santander.rio.mbanking.app.module.funds;

import android.content.Context;
import ar.com.santander.rio.mbanking.app.base.IBaseView;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.services.soap.beans.body.AlertaEvaluacionRiesgoBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.SimularTransferenciaFondoBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.TransferirFondoBodyResponseBean;

public interface ConfirmarTransferirFondoView extends IBaseView {
    AnalyticsManager getAnalyticsManager();

    Context getContext();

    String getSelectedAmountType();

    void gotoComprobante(TransferirFondoBodyResponseBean transferirFondoBodyResponseBean);

    void gotoEvaluacionRiesgoTransferirFondo(AlertaEvaluacionRiesgoBean alertaEvaluacionRiesgoBean, String str, String str2);

    void gotoFueraHorarioFondo(String str);

    void setConfirmarTransferirFondoView(SimularTransferenciaFondoBodyResponseBean simularTransferenciaFondoBodyResponseBean);
}
