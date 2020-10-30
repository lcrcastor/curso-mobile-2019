package ar.com.santander.rio.mbanking.app.module.transfers;

import android.app.Activity;
import ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentaDebitoBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestBSRBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestOBBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestinoBSRBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestinoOBBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.DatosTransferenciaBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.LeyendasBean;
import java.util.ArrayList;

public interface ConfirmacionTransferenciaPresenter {
    void onCreatePage(ArrayList<String> arrayList, DatosCuentasBean datosCuentasBean, DatosCuentasBean datosCuentasBean2, DatosCuentasDestBSRBean datosCuentasDestBSRBean, DatosCuentasDestOBBean datosCuentasDestOBBean, LeyendasBean leyendasBean, Activity activity);

    void sendRequestTransferenciasBSR(DatosTransferenciaBean datosTransferenciaBean, DatosCuentaDebitoBean datosCuentaDebitoBean, DatosCuentasDestinoBSRBean datosCuentasDestinoBSRBean);

    void sendRequestTransferenciasOB(DatosTransferenciaBean datosTransferenciaBean, DatosCuentaDebitoBean datosCuentaDebitoBean, DatosCuentasDestinoOBBean datosCuentasDestinoOBBean);
}
