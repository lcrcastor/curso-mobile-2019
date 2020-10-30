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
import ar.com.santander.rio.mbanking.services.soap.beans.body.CnsEmpresaDatosEmpresa;
import ar.com.santander.rio.mbanking.services.soap.beans.body.DatosDeudaBean;
import ar.com.santander.rio.mbanking.services.soap.versions.VCnsDeudaCB;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import java.util.List;

public class SeleccionarEmpresaNuevoPagoPresenter extends BasePresenter<SeleccionarEmpresaNuevoPagoView> {
    protected Context mContext;

    public SeleccionarEmpresaNuevoPagoPresenter(Bus bus, IDataManager iDataManager, Context context) {
        super(bus, iDataManager);
        this.mContext = context;
    }

    public void cnsDeudaCB(String str, String str2) {
        ((SeleccionarEmpresaNuevoPagoView) getBaseView()).showProgressIndicator(VCnsDeudaCB.nameService);
        this.mDataManager.cnsDeudaCB(Integer.toString(str.length()), str, str2);
    }

    @Subscribe
    public void onCnsDeudaCB(CnsDeudaCBEvent cnsDeudaCBEvent) {
        ((SeleccionarEmpresaNuevoPagoView) getBaseView()).dismissProgressIndicator();
        final CnsDeudaCBEvent cnsDeudaCBEvent2 = cnsDeudaCBEvent;
        AnonymousClass1 r1 = new BaseWSResponseHandler(this.mContext, TypeOption.INTERMDIATE_VIEW, FragmentConstants.PAGO_SERVICIOS, getBaseView(), (BaseActivity) this.mContext) {
            public void onOk() {
                SeleccionarEmpresaNuevoPagoPresenter.this.a(cnsDeudaCBEvent2);
            }
        };
        r1.handleWSResponse(cnsDeudaCBEvent);
    }

    /* access modifiers changed from: private */
    public void a(CnsDeudaCBEvent cnsDeudaCBEvent) {
        try {
            if (cnsDeudaCBEvent.getResponse().cnsDeudaCBBodyResponseBean.datosDeudas.lstDatosDeudaBean.size() == 1) {
                DatosDeudaBean datosDeudaBean = (DatosDeudaBean) cnsDeudaCBEvent.getResponse().cnsDeudaCBBodyResponseBean.datosDeudas.lstDatosDeudaBean.get(0);
                List cuentas = cnsDeudaCBEvent.getResponse().cnsDeudaCBBodyResponseBean.cuentasDeudasBean.getCuentas();
                if (datosDeudaBean.datosEmpresa.tipoPago.equalsIgnoreCase("2")) {
                    ((SeleccionarEmpresaNuevoPagoView) getBaseView()).showPrepararPagoAfip(datosDeudaBean, cuentas);
                } else {
                    ((SeleccionarEmpresaNuevoPagoView) getBaseView()).showPrepararPagoElectronico(datosDeudaBean, cuentas);
                }
            } else {
                ((SeleccionarEmpresaNuevoPagoView) getBaseView()).showSelectPayment(cnsDeudaCBEvent.getResponse().cnsDeudaCBBodyResponseBean.datosDeudas.lstDatosDeudaBean);
            }
        } catch (Exception e) {
            Log.e(WSErrorHandlerConstants.LOG_TAG, "onCnsDeudaCBResultOk: ", e);
            e.fillInStackTrace();
        }
    }

    public void showSelectCompany() {
        ((SeleccionarEmpresaNuevoPagoView) getBaseView()).showSelectCompany();
    }

    public void onCompanySelected(String str, CnsEmpresaDatosEmpresa cnsEmpresaDatosEmpresa) {
        cnsDeudaCB(str, cnsEmpresaDatosEmpresa.empServ);
    }
}
