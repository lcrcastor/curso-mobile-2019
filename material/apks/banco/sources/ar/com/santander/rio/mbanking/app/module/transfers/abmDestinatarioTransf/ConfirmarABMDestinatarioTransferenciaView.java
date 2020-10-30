package ar.com.santander.rio.mbanking.app.module.transfers.abmDestinatarioTransf;

import android.app.Activity;
import ar.com.santander.rio.mbanking.app.base.IBaseView;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ABMDestinatarioTransfBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestBSRBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestOBBean;

public interface ConfirmarABMDestinatarioTransferenciaView extends IBaseView {
    Activity getActivity();

    void goToComprobanteABMDestinatario(ABMDestinatarioTransfBodyResponseBean aBMDestinatarioTransfBodyResponseBean);

    void setConfirmarABMDestinatarioView(DatosCuentasDestOBBean datosCuentasDestOBBean, DatosCuentasDestBSRBean datosCuentasDestBSRBean);
}
