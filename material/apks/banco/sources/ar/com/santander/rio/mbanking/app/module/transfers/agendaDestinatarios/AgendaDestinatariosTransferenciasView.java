package ar.com.santander.rio.mbanking.app.module.transfers.agendaDestinatarios;

import ar.com.santander.rio.mbanking.app.base.IBaseView;
import ar.com.santander.rio.mbanking.services.model.general.AgendaDestinatarios;
import ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestBSRBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestOBBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.VerificaDatosSalidaOBBean;

public interface AgendaDestinatariosTransferenciasView extends IBaseView {
    void gotoNextFlow(AgendaDestinatarios agendaDestinatarios, VerificaDatosSalidaOBBean verificaDatosSalidaOBBean, DatosCuentasDestBSRBean datosCuentasDestBSRBean, DatosCuentasDestOBBean datosCuentasDestOBBean);

    void resetSelectedFromList();
}
