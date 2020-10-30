package ar.com.santander.rio.mbanking.app.module.transfers.abmDestinatarioTransf;

import ar.com.santander.rio.mbanking.app.base.IBaseView;
import ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestBSRBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestOBBean;

public interface AltaManualDestinatarioTransferenciaView extends IBaseView {
    void goToConfirmarABMDestinatario(DatosCuentasDestOBBean datosCuentasDestOBBean, DatosCuentasDestBSRBean datosCuentasDestBSRBean);

    void setAltaManualDestinatarioView();
}
