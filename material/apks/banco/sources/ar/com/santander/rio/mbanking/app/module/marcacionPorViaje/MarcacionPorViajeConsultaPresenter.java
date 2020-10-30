package ar.com.santander.rio.mbanking.app.module.marcacionPorViaje;

import android.content.Context;
import android.util.Log;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.base.BasePresenter;
import ar.com.santander.rio.mbanking.app.base.BaseWSResponseHandler;
import ar.com.santander.rio.mbanking.app.ui.constants.WSErrorHandlerConstants;
import ar.com.santander.rio.mbanking.app.ui.fragments.FragmentConstants;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.services.events.ABMViajesEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeOption;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetTarjPaisesBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.PaisesBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.TarjetasMarcacionBean;
import ar.com.santander.rio.mbanking.services.soap.versions.VABMViajes;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

public class MarcacionPorViajeConsultaPresenter extends BasePresenter<MarcacionPorViajeConsultaView> {
    protected Context mContext;

    public MarcacionPorViajeConsultaPresenter(Bus bus, IDataManager iDataManager, Context context) {
        super(bus, iDataManager);
        this.mContext = context;
    }

    public void onCreatePage(GetTarjPaisesBodyResponseBean getTarjPaisesBodyResponseBean) {
        ((MarcacionPorViajeConsultaView) getBaseView()).setMarcacionPorViajeConsultaView(getTarjPaisesBodyResponseBean);
    }

    public void callABMViajes(String str, String str2, String str3, String str4, String str5, String str6, PaisesBean paisesBean, TarjetasMarcacionBean tarjetasMarcacionBean) {
        ((MarcacionPorViajeConsultaView) getBaseView()).showProgressIndicator(VABMViajes.nameService);
        this.mDataManager.abmViajes(str, str2, str3, str4, str5, str6, paisesBean, tarjetasMarcacionBean);
    }

    @Subscribe
    public void onABMViajes(ABMViajesEvent aBMViajesEvent) {
        ((MarcacionPorViajeConsultaView) getBaseView()).dismissProgressIndicator();
        final ABMViajesEvent aBMViajesEvent2 = aBMViajesEvent;
        AnonymousClass1 r1 = new BaseWSResponseHandler(this.mContext, TypeOption.INTERMDIATE_VIEW, FragmentConstants.TARJETAS, getBaseView(), (BaseActivity) this.mContext) {
            /* access modifiers changed from: protected */
            public void onOk() {
                MarcacionPorViajeConsultaPresenter.this.a(aBMViajesEvent2);
            }

            /* access modifiers changed from: protected */
            public void onRes4Error() {
                MarcacionPorViajeConsultaPresenter.this.a(aBMViajesEvent2.getErrorBodyBean().resCod, aBMViajesEvent2.getErrorBodyBean().resDesc);
            }
        };
        r1.handleWSResponse(aBMViajesEvent);
    }

    /* access modifiers changed from: private */
    public void a(ABMViajesEvent aBMViajesEvent) {
        try {
            ((MarcacionPorViajeConsultaView) getBaseView()).gotoComprobante(Boolean.valueOf(true), "", "");
        } catch (Exception e) {
            Log.e(WSErrorHandlerConstants.LOG_TAG, "onABMViajesResultOK", e);
            e.fillInStackTrace();
        }
    }

    /* access modifiers changed from: private */
    public void a(String str, String str2) {
        try {
            ((MarcacionPorViajeConsultaView) getBaseView()).gotoComprobante(Boolean.valueOf(false), str, str2);
        } catch (Exception e) {
            Log.e(WSErrorHandlerConstants.LOG_TAG, "onABMViajesResultRes4", e);
            e.fillInStackTrace();
        }
    }
}
