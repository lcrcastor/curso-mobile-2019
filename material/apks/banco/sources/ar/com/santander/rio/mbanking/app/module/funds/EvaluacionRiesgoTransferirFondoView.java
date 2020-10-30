package ar.com.santander.rio.mbanking.app.module.funds;

import android.content.Context;
import ar.com.santander.rio.mbanking.app.base.IBaseView;
import ar.com.santander.rio.mbanking.services.soap.beans.body.AlertaEvaluacionRiesgoBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.TransferirFondoBodyResponseBean;

public interface EvaluacionRiesgoTransferirFondoView extends IBaseView {
    Context getContext();

    void gotoComprobante(TransferirFondoBodyResponseBean transferirFondoBodyResponseBean);

    void setEvaluacionRiesgoTransferenciaView(AlertaEvaluacionRiesgoBean alertaEvaluacionRiesgoBean, String str, String str2);
}
