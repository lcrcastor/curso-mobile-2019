package ar.com.santander.rio.mbanking.app.module.transfers.abmDestinatarioTransf;

import ar.com.santander.rio.mbanking.app.base.IBaseView;
import ar.com.santander.rio.mbanking.services.soap.beans.VerificaDatosInicialesTransfOBResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ABMDestinatarioTransfBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestBSRBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestOBBean;

public interface ComprobanteABMDestinatarioTransferenciaView extends IBaseView {
    void finishConfirmacionABM(Integer num);

    void finishConfirmacionABM(Integer num, Boolean bool, VerificaDatosInicialesTransfOBResponseBean verificaDatosInicialesTransfOBResponseBean);

    void setComprobanteABMDestinatarioView(ABMDestinatarioTransfBodyResponseBean aBMDestinatarioTransfBodyResponseBean);

    void setComprobanteABMDestinatarioView(ABMDestinatarioTransfBodyResponseBean aBMDestinatarioTransfBodyResponseBean, DatosCuentasDestOBBean datosCuentasDestOBBean, DatosCuentasDestBSRBean datosCuentasDestBSRBean);
}
