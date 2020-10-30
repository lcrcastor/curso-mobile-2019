package ar.com.santander.rio.mbanking.app.module.transfers.abmDestinatarioTransf;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.base.BasePresenter;
import ar.com.santander.rio.mbanking.app.base.BaseWSResponseHandler;
import ar.com.santander.rio.mbanking.app.ui.constants.WSErrorHandlerConstants;
import ar.com.santander.rio.mbanking.app.ui.fragments.FragmentConstants;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.services.events.VerificaDatosInicialesTransfOBEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeOption;
import ar.com.santander.rio.mbanking.services.soap.beans.VerificaDatosInicialesTransfOBResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ABMDestinatarioTransfBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestBSRBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestOBBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.VerificaDatosOBBean;
import ar.com.santander.rio.mbanking.services.soap.versions.VVerificaDatosInicialesTransf;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

public class ComprobanteABMDestinatarioTransferenciaPresenter extends BasePresenter<ComprobanteABMDestinatarioTransferenciaView> {
    protected Context mContext;
    protected SessionManager mSessionManager;

    public ComprobanteABMDestinatarioTransferenciaPresenter(Bus bus, IDataManager iDataManager, SessionManager sessionManager, Context context) {
        super(bus, iDataManager);
        this.mSessionManager = sessionManager;
        this.mContext = context;
    }

    public void onCreatePage(ABMDestinatarioTransfBodyResponseBean aBMDestinatarioTransfBodyResponseBean, DatosCuentasDestOBBean datosCuentasDestOBBean, DatosCuentasDestBSRBean datosCuentasDestBSRBean) {
        ((ComprobanteABMDestinatarioTransferenciaView) getBaseView()).setComprobanteABMDestinatarioView(aBMDestinatarioTransfBodyResponseBean, datosCuentasDestOBBean, datosCuentasDestBSRBean);
    }

    public void onCreatePage(ABMDestinatarioTransfBodyResponseBean aBMDestinatarioTransfBodyResponseBean) {
        ((ComprobanteABMDestinatarioTransferenciaView) getBaseView()).setComprobanteABMDestinatarioView(aBMDestinatarioTransfBodyResponseBean);
    }

    public void onVerificar(String str, String str2, String str3, String str4, String str5, String str6) {
        ((ComprobanteABMDestinatarioTransferenciaView) getBaseView()).showProgressIndicator(VVerificaDatosInicialesTransf.nameService);
        String str7 = !TextUtils.isEmpty(str6) ? null : str5;
        IDataManager iDataManager = this.mDataManager;
        VerificaDatosOBBean verificaDatosOBBean = new VerificaDatosOBBean(str, str2, str3, str4, this.mSessionManager.getLoginUnico().getDatosPersonales().getNroDocumento(), this.mSessionManager.getLoginUnico().getDatosPersonales().getFechaNacimiento(), str7, str6);
        iDataManager.verificaDatosInicialesTransfOB(verificaDatosOBBean);
    }

    @Subscribe
    public void onVerificaDatosInicialesTransfOB(VerificaDatosInicialesTransfOBEvent verificaDatosInicialesTransfOBEvent) {
        ((ComprobanteABMDestinatarioTransferenciaView) getBaseView()).dismissProgressIndicator();
        final VerificaDatosInicialesTransfOBEvent verificaDatosInicialesTransfOBEvent2 = verificaDatosInicialesTransfOBEvent;
        AnonymousClass1 r1 = new BaseWSResponseHandler(this.mContext, TypeOption.INTERMDIATE_VIEW, FragmentConstants.TRANSFERENCIAS, getBaseView(), (BaseActivity) this.mContext) {
            public void onOk() {
                ComprobanteABMDestinatarioTransferenciaPresenter.this.a(verificaDatosInicialesTransfOBEvent2);
            }
        };
        r1.handleWSResponse(verificaDatosInicialesTransfOBEvent);
    }

    /* access modifiers changed from: private */
    public void a(VerificaDatosInicialesTransfOBEvent verificaDatosInicialesTransfOBEvent) {
        try {
            ((ComprobanteABMDestinatarioTransferenciaView) getBaseView()).finishConfirmacionABM(Integer.valueOf(-1), Boolean.valueOf(false), (VerificaDatosInicialesTransfOBResponseBean) verificaDatosInicialesTransfOBEvent.getBeanResponse());
        } catch (Exception e) {
            Log.e(WSErrorHandlerConstants.LOG_TAG, "onVerificaDatosInicialesTransfOBResultOk: ", e);
            e.fillInStackTrace();
        }
    }
}
