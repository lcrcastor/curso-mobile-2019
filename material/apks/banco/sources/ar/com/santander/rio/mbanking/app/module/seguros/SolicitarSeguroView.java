package ar.com.santander.rio.mbanking.app.module.seguros;

import android.content.Context;
import ar.com.santander.rio.mbanking.app.base.IBaseView;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CotizacionBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.PlanSeguroBean;

public interface SolicitarSeguroView extends IBaseView {
    Context getContext();

    void gotoContratacionSeguro(PlanSeguroBean planSeguroBean);

    void setSolicitarSeguroView(CotizacionBean cotizacionBean);
}
