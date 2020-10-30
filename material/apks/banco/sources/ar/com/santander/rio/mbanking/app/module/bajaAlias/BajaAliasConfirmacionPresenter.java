package ar.com.santander.rio.mbanking.app.module.bajaAlias;

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
import ar.com.santander.rio.mbanking.services.soap.beans.body.CuentaShortBean;
import ar.com.santander.rio.mbanking.services.soap.versions.VABMAlias;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

public class BajaAliasConfirmacionPresenter extends BasePresenter<BajaAliasConfirmacionView> {
    protected Context mContext;

    public BajaAliasConfirmacionPresenter(Bus bus, IDataManager iDataManager, Context context) {
        super(bus, iDataManager);
        this.mContext = context;
    }

    public void onCreatePage() {
        ((BajaAliasConfirmacionView) getBaseView()).setBajaAliasConfirmacionView();
    }

    public void onConfirmar(String str, String str2, String str3, String str4, CuentaShortBean cuentaShortBean) {
        ((BajaAliasConfirmacionView) getBaseView()).showProgressIndicator(VABMAlias.nameService);
        this.mDataManager.abmAlias(str, str2, str3, str4, cuentaShortBean);
    }

    @Subscribe
    public void onABMAlias(ABMAliasEvent aBMAliasEvent) {
        ((BajaAliasConfirmacionView) getBaseView()).dismissProgressIndicator();
        final ABMAliasEvent aBMAliasEvent2 = aBMAliasEvent;
        AnonymousClass1 r1 = new BaseWSResponseHandler(this.mContext, TypeOption.INTERMDIATE_VIEW, FragmentConstants.CUENTAS, getBaseView(), (BaseActivity) this.mContext) {
            public void onOk() {
                BajaAliasConfirmacionPresenter.this.a(aBMAliasEvent2);
            }
        };
        r1.handleWSResponse(aBMAliasEvent);
    }

    /* access modifiers changed from: private */
    public void a(ABMAliasEvent aBMAliasEvent) {
        try {
            ((BajaAliasConfirmacionView) getBaseView()).goToComprobanteBajaAlias(aBMAliasEvent.getResponse().getAbmAliasBodyResponseBean().getNroComprobante(), aBMAliasEvent.getResponse().getAbmAliasBodyResponseBean().getFechaOperacion(), aBMAliasEvent.getResponse().getAbmAliasBodyResponseBean().getNumeroCuil());
        } catch (Exception e) {
            Log.e(WSErrorHandlerConstants.LOG_TAG, "onABMAliasResultOk: ", e);
        }
    }
}
