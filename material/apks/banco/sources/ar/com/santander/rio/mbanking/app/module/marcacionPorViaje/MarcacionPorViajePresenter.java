package ar.com.santander.rio.mbanking.app.module.marcacionPorViaje;

import android.content.Context;
import android.util.Log;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.base.BasePresenter;
import ar.com.santander.rio.mbanking.app.base.BaseWSResponseHandler;
import ar.com.santander.rio.mbanking.app.ui.constants.WSErrorHandlerConstants;
import ar.com.santander.rio.mbanking.app.ui.fragments.FragmentConstants;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.services.events.GetTarjPaisesEvent;
import ar.com.santander.rio.mbanking.services.events.GetViajesEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeOption;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ViajesBean;
import ar.com.santander.rio.mbanking.services.soap.versions.VGetTarjPaises;
import ar.com.santander.rio.mbanking.services.soap.versions.VGetViajes;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

public class MarcacionPorViajePresenter extends BasePresenter<MarcacionPorViajeView> {
    protected Context mContext;
    protected SessionManager sessionManager;

    public MarcacionPorViajePresenter(Bus bus, IDataManager iDataManager, Context context) {
        super(bus, iDataManager);
        this.mContext = context;
    }

    public void onCreatePage(ViajesBean viajesBean) {
        ((MarcacionPorViajeView) getBaseView()).setMarcacionPorViajeView(viajesBean);
    }

    public void onCreatePage(ViajesBean viajesBean, String str) {
        ((MarcacionPorViajeView) getBaseView()).setMarcacionPorViajeView(viajesBean, str);
    }

    public void onCreatePage(String str, String str2) {
        ((MarcacionPorViajeView) getBaseView()).setMarcacionPorViajeRes4View(str, str2);
    }

    public void callGetViajes() {
        ((MarcacionPorViajeView) getBaseView()).showProgressIndicator(VGetViajes.nameService);
        this.mDataManager.getViajes();
    }

    @Subscribe
    public void onGetViajes(GetViajesEvent getViajesEvent) {
        ((MarcacionPorViajeView) getBaseView()).dismissProgressIndicator();
        final GetViajesEvent getViajesEvent2 = getViajesEvent;
        AnonymousClass1 r1 = new BaseWSResponseHandler(this.mContext, TypeOption.INTERMDIATE_VIEW, FragmentConstants.TARJETAS, getBaseView(), (BaseActivity) this.mContext) {
            public void onOk() {
                MarcacionPorViajePresenter.this.a(getViajesEvent2);
            }

            /* access modifiers changed from: protected */
            public void onRes4Error() {
                MarcacionPorViajePresenter.this.b(getViajesEvent2);
            }
        };
        r1.handleWSResponse(getViajesEvent);
    }

    /* access modifiers changed from: private */
    public void a(GetViajesEvent getViajesEvent) {
        try {
            onCreatePage(getViajesEvent.getResponse().getGetViajesBodyResponseBean().getViajes(), getViajesEvent.getResponse().getGetViajesBodyResponseBean().getAyuda());
        } catch (Exception e) {
            Log.e(WSErrorHandlerConstants.LOG_TAG, "onGetViajesResultOk", e);
            e.fillInStackTrace();
        }
    }

    /* access modifiers changed from: private */
    public void b(GetViajesEvent getViajesEvent) {
        String str = "";
        try {
            if (getViajesEvent.getErrorBodyBean() != null) {
                str = getViajesEvent.getErrorBodyBean().getAyuda();
            }
            onCreatePage(getViajesEvent.getMessageToShow(), str);
        } catch (Exception e) {
            Log.e(WSErrorHandlerConstants.LOG_TAG, "onGetViajesRes4Error", e);
            e.fillInStackTrace();
        }
    }

    public void onGetTarjPaises() {
        ((MarcacionPorViajeView) getBaseView()).showProgressIndicator(VGetTarjPaises.nameService);
        this.mDataManager.getTarjPaises();
    }

    @Subscribe
    public void onGetTarjPaises(GetTarjPaisesEvent getTarjPaisesEvent) {
        ((MarcacionPorViajeView) getBaseView()).dismissProgressIndicator();
        final GetTarjPaisesEvent getTarjPaisesEvent2 = getTarjPaisesEvent;
        AnonymousClass2 r1 = new BaseWSResponseHandler(this.mContext, TypeOption.INTERMDIATE_VIEW, FragmentConstants.TARJETAS, getBaseView(), (BaseActivity) this.mContext) {
            /* access modifiers changed from: protected */
            public void onOk() {
                MarcacionPorViajePresenter.this.a(getTarjPaisesEvent2);
            }
        };
        r1.handleWSResponse(getTarjPaisesEvent);
    }

    /* access modifiers changed from: private */
    public void a(GetTarjPaisesEvent getTarjPaisesEvent) {
        try {
            ((MarcacionPorViajeView) getBaseView()).goToDetalleViajeEditable(getTarjPaisesEvent.getResponse().getTarjPaisesBodyResponseBean);
        } catch (Exception e) {
            Log.e(WSErrorHandlerConstants.LOG_TAG, "onGetTarjPaisesResultOk", e);
            e.fillInStackTrace();
        }
    }
}
