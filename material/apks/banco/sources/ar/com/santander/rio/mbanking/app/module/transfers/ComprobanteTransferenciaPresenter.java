package ar.com.santander.rio.mbanking.app.module.transfers;

import ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestBSRBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.DatosTransferenciaSalidaBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.LeyendasBean;
import java.util.ArrayList;

public interface ComprobanteTransferenciaPresenter {
    void onCreatePage(ArrayList<String> arrayList, DatosCuentasBean datosCuentasBean, DatosCuentasBean datosCuentasBean2, DatosCuentasDestBSRBean datosCuentasDestBSRBean, DatosTransferenciaSalidaBean datosTransferenciaSalidaBean, LeyendasBean leyendasBean);
}
