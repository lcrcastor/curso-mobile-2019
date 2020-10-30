package ar.com.santander.rio.mbanking.app.module.modificacionAlias;

import android.content.Context;
import android.util.Log;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.base.BasePresenter;
import ar.com.santander.rio.mbanking.app.base.BaseWSResponseHandler;
import ar.com.santander.rio.mbanking.app.ui.constants.WSErrorHandlerConstants;
import ar.com.santander.rio.mbanking.app.ui.fragments.FragmentConstants;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.services.events.ABMAliasEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeOption;
import ar.com.santander.rio.mbanking.services.soap.beans.ABMAliasResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CuentaShortBean;
import ar.com.santander.rio.mbanking.services.soap.versions.VABMAlias;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

public class ModificacionAliasConfirmacionPresenter extends BasePresenter<ModificacionAliasConfirmacionView> {
    protected Context mContext;

    public ModificacionAliasConfirmacionPresenter(Bus bus, IDataManager iDataManager, Context context) {
        super(bus, iDataManager);
        this.mContext = context;
    }

    public void onCreatePage() {
        ((ModificacionAliasConfirmacionView) getBaseView()).setModificacionAliasConfirmacionView();
    }

    public void onConfirmar(String str, String str2, String str3, String str4, String str5, CuentaShortBean cuentaShortBean) {
        ((ModificacionAliasConfirmacionView) getBaseView()).showProgressIndicator(VABMAlias.nameService);
        this.mDataManager.abmAlias(str, str2, str3, str4, str5, cuentaShortBean);
    }

    @Subscribe
    public void onABMAlias(ABMAliasEvent aBMAliasEvent) {
        ((ModificacionAliasConfirmacionView) getBaseView()).dismissProgressIndicator();
        final ABMAliasEvent aBMAliasEvent2 = aBMAliasEvent;
        AnonymousClass1 r1 = new BaseWSResponseHandler(this.mContext, TypeOption.NO_TRANSACTIONAL_FINAL_VIEW, FragmentConstants.TRANSFERENCIAS, getBaseView(), (BaseActivity) this.mContext) {
            /* access modifiers changed from: protected */
            public void onOk() {
                ModificacionAliasConfirmacionPresenter.this.a(aBMAliasEvent2);
            }
        };
        r1.handleWSResponse(aBMAliasEvent);
    }

    /* access modifiers changed from: private */
    public void a(ABMAliasEvent aBMAliasEvent) {
        try {
            ABMAliasResponseBean aBMAliasResponseBean = (ABMAliasResponseBean) aBMAliasEvent.getBeanResponse();
            if (aBMAliasResponseBean.abmAliasBodyResponseBean.resCod == null || aBMAliasResponseBean.abmAliasBodyResponseBean.resCod.isEmpty()) {
                ((ModificacionAliasConfirmacionView) getBaseView()).goToComprobanteABMAlias(aBMAliasResponseBean.getAbmAliasBodyResponseBean().getNroComprobante(), aBMAliasResponseBean.getAbmAliasBodyResponseBean().getFechaOperacion(), aBMAliasResponseBean.getAbmAliasBodyResponseBean().getNumeroCuil());
            } else {
                ((ModificacionAliasConfirmacionView) getBaseView()).showDialogReasigna(aBMAliasResponseBean.getAbmAliasBodyResponseBean().resDesc, aBMAliasResponseBean.getAbmAliasBodyResponseBean().getIdAlias());
            }
        } catch (Exception e) {
            Log.e(WSErrorHandlerConstants.LOG_TAG, "onABMAliasResultOk: ", e);
            e.fillInStackTrace();
        }
    }
}
