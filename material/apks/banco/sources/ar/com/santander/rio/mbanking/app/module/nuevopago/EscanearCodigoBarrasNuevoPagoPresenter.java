package ar.com.santander.rio.mbanking.app.module.nuevopago;

import android.content.Context;
import android.util.Log;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.base.BasePresenter;
import ar.com.santander.rio.mbanking.app.base.BaseWSResponseHandler;
import ar.com.santander.rio.mbanking.app.ui.constants.WSErrorHandlerConstants;
import ar.com.santander.rio.mbanking.app.ui.fragments.FragmentConstants;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.services.events.CnsDeudaCBEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeOption;
import ar.com.santander.rio.mbanking.services.soap.beans.body.DatosDeudaBean;
import ar.com.santander.rio.mbanking.services.soap.versions.VCnsDeudaCB;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import java.util.List;

public class EscanearCodigoBarrasNuevoPagoPresenter extends BasePresenter<EscanearCodigoBarrasNuevoPagoView> {
    private final int a = 2;
    private String b;
    private int c = 0;
    protected Context mContext;

    public int getMaxRetries() {
        return 2;
    }

    public EscanearCodigoBarrasNuevoPagoPresenter(Bus bus, IDataManager iDataManager, Context context) {
        super(bus, iDataManager);
        this.mContext = context;
    }

    public void cnsDeudaCB(String str) {
        ((EscanearCodigoBarrasNuevoPagoView) getBaseView()).showProgressIndicator(VCnsDeudaCB.nameService);
        this.mDataManager.cnsDeudaCB(Integer.toString(str.length()), str, "");
    }

    @Subscribe
    public void onCnsDeudaCB(CnsDeudaCBEvent cnsDeudaCBEvent) {
        ((EscanearCodigoBarrasNuevoPagoView) getBaseView()).dismissProgressIndicator();
        final CnsDeudaCBEvent cnsDeudaCBEvent2 = cnsDeudaCBEvent;
        AnonymousClass1 r1 = new BaseWSResponseHandler(this.mContext, TypeOption.INTERMDIATE_VIEW, FragmentConstants.PAGO_SERVICIOS, getBaseView(), (BaseActivity) this.mContext) {
            public void onOk() {
                EscanearCodigoBarrasNuevoPagoPresenter.this.a(cnsDeudaCBEvent2);
            }
        };
        r1.handleWSResponse(cnsDeudaCBEvent);
    }

    /* access modifiers changed from: private */
    public void a(CnsDeudaCBEvent cnsDeudaCBEvent) {
        try {
            if (Integer.valueOf(cnsDeudaCBEvent.getResponse().cnsDeudaCBBodyResponseBean.cantEmpresas).intValue() != 1) {
                ((EscanearCodigoBarrasNuevoPagoView) getBaseView()).showSeleccionarEmpresa(this.b, cnsDeudaCBEvent.getResponse().cnsDeudaCBBodyResponseBean.listaEmpresa.lstCnsEmpresaDatosEmpresa);
            } else if (cnsDeudaCBEvent.getResponse().cnsDeudaCBBodyResponseBean.datosDeudas.lstDatosDeudaBean.size() == 1) {
                DatosDeudaBean datosDeudaBean = (DatosDeudaBean) cnsDeudaCBEvent.getResponse().cnsDeudaCBBodyResponseBean.datosDeudas.lstDatosDeudaBean.get(0);
                List cuentas = cnsDeudaCBEvent.getResponse().cnsDeudaCBBodyResponseBean.cuentasDeudasBean.getCuentas();
                if (datosDeudaBean.datosEmpresa.tipoPago.equalsIgnoreCase("2")) {
                    ((EscanearCodigoBarrasNuevoPagoView) getBaseView()).showPrepararPagoAfip(datosDeudaBean, cuentas);
                } else {
                    ((EscanearCodigoBarrasNuevoPagoView) getBaseView()).showPrepararPagoElectronico(datosDeudaBean, cuentas);
                }
            } else {
                ((EscanearCodigoBarrasNuevoPagoView) getBaseView()).showSelectPayment(cnsDeudaCBEvent.getResponse().cnsDeudaCBBodyResponseBean.datosDeudas.lstDatosDeudaBean);
            }
        } catch (Exception e) {
            Log.e(WSErrorHandlerConstants.LOG_TAG, "onCnsDeudaCBResultOk: ", e);
            e.fillInStackTrace();
        }
    }

    public void resetRetryCounter() {
        this.c = 0;
    }

    public void incremetRetryCounter() {
        this.c++;
    }

    public int getRetryCounter() {
        return this.c;
    }

    public void onBarcodeRecognized(String str) {
        this.b = str;
        cnsDeudaCB(str);
    }
}
