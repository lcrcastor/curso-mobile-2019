package ar.com.santander.rio.mbanking.app.module.transfers;

import ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestBSRBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.DatosTransferenciaSalidaBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.LeyendasBean;
import java.util.ArrayList;

public class ComprobanteTransferenciaPresenterImp implements ComprobanteTransferenciaPresenter {
    public TransferenciasView transferenciasView;

    public ComprobanteTransferenciaPresenterImp(TransferenciasView transferenciasView2) {
        this.transferenciasView = transferenciasView2;
    }

    public void onCreatePage(ArrayList<String> arrayList, DatosCuentasBean datosCuentasBean, DatosCuentasBean datosCuentasBean2, DatosCuentasDestBSRBean datosCuentasDestBSRBean, DatosTransferenciaSalidaBean datosTransferenciaSalidaBean, LeyendasBean leyendasBean) {
        this.transferenciasView.setComprobanteTransferenciaView(arrayList, datosCuentasBean, datosCuentasBean2, datosCuentasDestBSRBean, datosTransferenciaSalidaBean, leyendasBean);
    }
}
