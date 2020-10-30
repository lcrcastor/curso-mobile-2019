package ar.com.santander.rio.mbanking.app.module.nuevopago;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.base.BasePresenter;
import ar.com.santander.rio.mbanking.app.base.BaseWSResponseHandler;
import ar.com.santander.rio.mbanking.app.ui.constants.WSErrorHandlerConstants;
import ar.com.santander.rio.mbanking.app.ui.fragments.FragmentConstants;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.services.events.CnsDeudaManualEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeOption;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CuentaDebitoBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.DatosDeudaBean;
import ar.com.santander.rio.mbanking.services.soap.versions.VCnsDeudaManual;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import java.util.List;

public class AgregarPagoNuevoPagoPresenter extends BasePresenter<AgregarPagoNuevoPagoView> {
    protected Context mContext;

    public AgregarPagoNuevoPagoPresenter(Bus bus, IDataManager iDataManager, Context context) {
        super(bus, iDataManager);
        this.mContext = context;
    }

    public void cnsDeudaManual(String str, String str2) {
        ((AgregarPagoNuevoPagoView) getBaseView()).showProgressIndicator(VCnsDeudaManual.nameService);
        this.mDataManager.cnsDeudaManual(str, str2);
    }

    @Subscribe
    public void onCnsDeudaManual(CnsDeudaManualEvent cnsDeudaManualEvent) {
        ((AgregarPagoNuevoPagoView) getBaseView()).dismissProgressIndicator();
        final CnsDeudaManualEvent cnsDeudaManualEvent2 = cnsDeudaManualEvent;
        AnonymousClass1 r1 = new BaseWSResponseHandler(this.mContext, TypeOption.INTERMDIATE_VIEW, FragmentConstants.PAGO_SERVICIOS, getBaseView(), (BaseActivity) this.mContext) {
            public void onOk() {
                AgregarPagoNuevoPagoPresenter.this.a(cnsDeudaManualEvent2);
            }
        };
        r1.handleWSResponse(cnsDeudaManualEvent);
    }

    /* access modifiers changed from: private */
    public void a(CnsDeudaManualEvent cnsDeudaManualEvent) {
        String str;
        try {
            if (Integer.valueOf(cnsDeudaManualEvent.getResponse().cnsDeudaManualBodyResponseBean.datosDeudas.cantDeudas).intValue() > 1) {
                List<CuentaDebitoBean> list = cnsDeudaManualEvent.getResponse().cnsDeudaManualBodyResponseBean.cuentas.lstCuentaDeudas;
                List<DatosDeudaBean> list2 = cnsDeudaManualEvent.getResponse().cnsDeudaManualBodyResponseBean.datosDeudas.lstDatosDeudaBean;
                try {
                    str = cnsDeudaManualEvent.getResponse().cnsDeudaManualBodyResponseBean.leyendaAyuda;
                } catch (Exception unused) {
                    str = null;
                }
                if (TextUtils.isEmpty(str)) {
                    str = "";
                }
                ((AgregarPagoNuevoPagoView) getBaseView()).showSelectPayment(list, list2, str);
            } else if (Integer.valueOf(cnsDeudaManualEvent.getResponse().cnsDeudaManualBodyResponseBean.datosDeudas.cantDeudas).intValue() == 1) {
                ((AgregarPagoNuevoPagoView) getBaseView()).showSelectPayment(cnsDeudaManualEvent.getResponse().cnsDeudaManualBodyResponseBean.cuentas.lstCuentaDeudas, (DatosDeudaBean) cnsDeudaManualEvent.getResponse().cnsDeudaManualBodyResponseBean.datosDeudas.lstDatosDeudaBean.get(0));
            }
        } catch (Exception e) {
            Log.e(WSErrorHandlerConstants.LOG_TAG, "onCnsDeudaManualResultOk: ", e);
            e.fillInStackTrace();
        }
    }

    public void showAddPayment() {
        ((AgregarPagoNuevoPagoView) getBaseView()).showAddPayment();
    }

    public void onAddPayment(String str, String str2) {
        cnsDeudaManual(str, str2);
    }

    public void onAddPayment(List<CuentaDebitoBean> list, DatosDeudaBean datosDeudaBean) {
        ((AgregarPagoNuevoPagoView) getBaseView()).showSelectPayment(list, datosDeudaBean);
    }
}
