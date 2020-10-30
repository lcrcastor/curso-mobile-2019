package ar.com.santander.rio.mbanking.app.module.transfers;

import android.app.Activity;
import ar.com.santander.rio.mbanking.managers.security.SoftTokenManager;
import ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentaDebitoBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestBSRBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestOBBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestinoBSRBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestinoOBBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.DatosTransferenciaBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.LeyendasBean;
import java.util.ArrayList;

public class ConfirmacionTransferenciaPresenterImp implements ConfirmacionTransferenciaPresenter {
    private TransferenciasView a;
    protected Activity mActivity;
    protected SoftTokenManager mSoftTokenManager;

    public ConfirmacionTransferenciaPresenterImp(TransferenciasView transferenciasView, SoftTokenManager softTokenManager) {
        this.a = transferenciasView;
        this.mSoftTokenManager = softTokenManager;
    }

    public void onCreatePage(ArrayList<String> arrayList, DatosCuentasBean datosCuentasBean, DatosCuentasBean datosCuentasBean2, DatosCuentasDestBSRBean datosCuentasDestBSRBean, DatosCuentasDestOBBean datosCuentasDestOBBean, LeyendasBean leyendasBean, Activity activity) {
        this.a.setConfirmacionTransferenciaView(arrayList, datosCuentasBean, datosCuentasBean2, datosCuentasDestBSRBean, datosCuentasDestOBBean, leyendasBean);
        this.mActivity = activity;
    }

    public void sendRequestTransferenciasBSR(DatosTransferenciaBean datosTransferenciaBean, DatosCuentaDebitoBean datosCuentaDebitoBean, DatosCuentasDestinoBSRBean datosCuentasDestinoBSRBean) {
        this.a.getDataManager().transferencias(datosTransferenciaBean, datosCuentaDebitoBean, datosCuentasDestinoBSRBean, this.mActivity);
    }

    public void sendRequestTransferenciasOB(DatosTransferenciaBean datosTransferenciaBean, DatosCuentaDebitoBean datosCuentaDebitoBean, DatosCuentasDestinoOBBean datosCuentasDestinoOBBean) {
        this.a.getDataManager().transferencias(datosTransferenciaBean, datosCuentaDebitoBean, datosCuentasDestinoOBBean, this.mActivity);
    }
}
