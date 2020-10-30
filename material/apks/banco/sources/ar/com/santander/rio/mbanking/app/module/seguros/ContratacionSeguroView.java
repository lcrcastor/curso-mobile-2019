package ar.com.santander.rio.mbanking.app.module.seguros;

import ar.com.santander.rio.mbanking.app.base.IBaseView;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetOcupacionesBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.PlanSeguroBean;

public interface ContratacionSeguroView extends IBaseView {
    void gotoConfirmarContratacion();

    void setContratacionSeguroView(PlanSeguroBean planSeguroBean);

    void showSeleccionarOcupacion(GetOcupacionesBodyResponseBean getOcupacionesBodyResponseBean, String str);
}
