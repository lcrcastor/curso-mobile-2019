package ar.com.santander.rio.mbanking.app.module.transfers.abmDestinatarioTransf;

import android.content.Context;
import android.util.Log;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.base.BasePresenter;
import ar.com.santander.rio.mbanking.app.base.BaseWSResponseHandler;
import ar.com.santander.rio.mbanking.app.ui.constants.TransferenciasConstants;
import ar.com.santander.rio.mbanking.app.ui.constants.WSErrorHandlerConstants;
import ar.com.santander.rio.mbanking.app.ui.fragments.FragmentConstants;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.managers.security.SoftTokenManager;
import ar.com.santander.rio.mbanking.services.events.ABMDestinatarioTransfEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeOption;
import ar.com.santander.rio.mbanking.services.soap.beans.ABMDestinatarioTransfResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ABMDestinatarioTransfBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestBSRBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestOBBean;
import ar.com.santander.rio.mbanking.services.soap.versions.VABMDestinatarioTransf;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

public class ConfirmarABMDestinatarioTransferenciaPresenter extends BasePresenter<ConfirmarABMDestinatarioTransferenciaView> {
    protected AnalyticsManager analyticsManager;
    protected Context mContext;
    protected SoftTokenManager mSoftTokenManager;

    public ConfirmarABMDestinatarioTransferenciaPresenter(Bus bus, IDataManager iDataManager, SoftTokenManager softTokenManager, Context context, AnalyticsManager analyticsManager2) {
        super(bus, iDataManager);
        this.mSoftTokenManager = softTokenManager;
        this.mContext = context;
        this.analyticsManager = analyticsManager2;
    }

    public void onCreatePage(DatosCuentasDestOBBean datosCuentasDestOBBean, DatosCuentasDestBSRBean datosCuentasDestBSRBean) {
        ((ConfirmarABMDestinatarioTransferenciaView) getBaseView()).setConfirmarABMDestinatarioView(datosCuentasDestOBBean, datosCuentasDestBSRBean);
    }

    @Subscribe
    public void onABMDestinatarioTransf(ABMDestinatarioTransfEvent aBMDestinatarioTransfEvent) {
        ((ConfirmarABMDestinatarioTransferenciaView) getBaseView()).dismissProgressIndicator();
        final ABMDestinatarioTransfEvent aBMDestinatarioTransfEvent2 = aBMDestinatarioTransfEvent;
        AnonymousClass1 r1 = new BaseWSResponseHandler(this.mContext, TypeOption.INTERMDIATE_VIEW, FragmentConstants.TRANSFERENCIAS, getBaseView(), (BaseActivity) this.mContext) {
            public void onOk() {
                ConfirmarABMDestinatarioTransferenciaPresenter.this.a(aBMDestinatarioTransfEvent2);
            }
        };
        r1.handleWSResponse(aBMDestinatarioTransfEvent);
    }

    /* access modifiers changed from: private */
    public void a(ABMDestinatarioTransfEvent aBMDestinatarioTransfEvent) {
        try {
            ((ConfirmarABMDestinatarioTransferenciaView) getBaseView()).goToComprobanteABMDestinatario(((ABMDestinatarioTransfResponseBean) aBMDestinatarioTransfEvent.getBeanResponse()).getAbmDestinatarioTransfBodyResponseBean());
        } catch (Exception e) {
            Log.e(WSErrorHandlerConstants.LOG_TAG, "onABMDestinatarioTransfOk: ", e);
            e.fillInStackTrace();
        }
    }

    public void onConfirmar(String str, String str2, String str3, String str4, DatosCuentasDestBSRBean datosCuentasDestBSRBean, DatosCuentasDestOBBean datosCuentasDestOBBean, Boolean bool) {
        if (str.equalsIgnoreCase(TransferenciasConstants.cINTENT_EXTRA_DATA_ORIGEN_NUEVO) || bool.booleanValue()) {
            ((ConfirmarABMDestinatarioTransferenciaView) getBaseView()).showProgressIndicator(VABMDestinatarioTransf.nameService);
            IDataManager iDataManager = this.mDataManager;
            ABMDestinatarioTransfBodyRequestBean aBMDestinatarioTransfBodyRequestBean = new ABMDestinatarioTransfBodyRequestBean(str2, str3, str4, datosCuentasDestBSRBean, datosCuentasDestOBBean);
            iDataManager.abmDestinatarioTransf(aBMDestinatarioTransfBodyRequestBean, ((ConfirmarABMDestinatarioTransferenciaView) getBaseView()).getActivity(), Boolean.valueOf(true));
            return;
        }
        ((ConfirmarABMDestinatarioTransferenciaView) getBaseView()).showProgressIndicator(VABMDestinatarioTransf.nameService);
        IDataManager iDataManager2 = this.mDataManager;
        ABMDestinatarioTransfBodyRequestBean aBMDestinatarioTransfBodyRequestBean2 = new ABMDestinatarioTransfBodyRequestBean(str2, str3, str4, datosCuentasDestBSRBean, datosCuentasDestOBBean);
        iDataManager2.abmDestinatarioTransf(aBMDestinatarioTransfBodyRequestBean2, ((ConfirmarABMDestinatarioTransferenciaView) getBaseView()).getActivity(), Boolean.valueOf(false));
    }
}
