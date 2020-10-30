package ar.com.santander.rio.mbanking.app.module.funds;

import android.content.Context;
import android.util.Log;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.base.BasePresenter;
import ar.com.santander.rio.mbanking.app.base.BaseWSResponseHandler;
import ar.com.santander.rio.mbanking.app.ui.constants.WSErrorHandlerConstants;
import ar.com.santander.rio.mbanking.app.ui.fragments.FragmentConstants;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.services.events.DetCtasEvent;
import ar.com.santander.rio.mbanking.services.events.GetMovimientosFondoEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeOption;
import ar.com.santander.rio.mbanking.services.soap.beans.MovCtasResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CuentaFondosBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.FondoBean;
import ar.com.santander.rio.mbanking.services.soap.versions.VDetCtas;
import ar.com.santander.rio.mbanking.services.soap.versions.VGetMovimientosFondo;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

public class DetalleFondoPresenter extends BasePresenter<DetalleFondoView> {
    protected Context context;
    protected SessionManager sessionManager;

    public DetalleFondoPresenter(Bus bus, IDataManager iDataManager, SessionManager sessionManager2, Context context2) {
        super(bus, iDataManager);
        this.context = context2;
        this.sessionManager = sessionManager2;
    }

    public void onCreatePage() {
        ((DetalleFondoView) getBaseView()).setDetalleFondoView();
    }

    public void onGetMovimiento(FondoBean fondoBean, CuentaFondosBean cuentaFondosBean) {
        ((DetalleFondoView) getBaseView()).showProgressIndicator(VGetMovimientosFondo.nameService);
        this.mDataManager.getMovimientosFondo(fondoBean.getId(), cuentaFondosBean.getNumero());
    }

    @Subscribe
    public void onGetMovimientosFondo(GetMovimientosFondoEvent getMovimientosFondoEvent) {
        ((DetalleFondoView) getBaseView()).dismissProgressIndicator();
        final GetMovimientosFondoEvent getMovimientosFondoEvent2 = getMovimientosFondoEvent;
        AnonymousClass1 r1 = new BaseWSResponseHandler(this.context, TypeOption.INTERMDIATE_VIEW, FragmentConstants.FONDOS_INVERSION, getBaseView(), (BaseActivity) this.context) {
            public void onOk() {
                DetalleFondoPresenter.this.onGetMovimientoFondoOk(getMovimientosFondoEvent2);
            }
        };
        r1.handleWSResponse(getMovimientosFondoEvent);
    }

    public void onGetMovimientoFondoOk(GetMovimientosFondoEvent getMovimientosFondoEvent) {
        try {
            ((DetalleFondoView) getBaseView()).gotoMovimiento(getMovimientosFondoEvent.getResponseBean().getGetMovimientosFondoBodyResponseBean().getListaMovimientosFondosBean().getMovimientosFondosBean(), "DETALLE_FONDO");
        } catch (Exception e) {
            Log.e(WSErrorHandlerConstants.LOG_TAG, "onGetMovimientoFondoOk: ", e);
        }
    }

    public void callCnsDetCtasInd(FondoBean fondoBean) {
        ((DetalleFondoView) getBaseView()).showProgressIndicator(VDetCtas.nameService);
        this.mDataManager.getDetCtas("", "", "");
    }

    @Subscribe
    public void cnsDetCtasInd(DetCtasEvent detCtasEvent) {
        ((DetalleFondoView) getBaseView()).dismissProgressIndicator();
        final DetCtasEvent detCtasEvent2 = detCtasEvent;
        AnonymousClass2 r1 = new BaseWSResponseHandler(this.context, TypeOption.INTERMDIATE_VIEW, FragmentConstants.FONDOS_INVERSION, getBaseView(), (BaseActivity) this.context) {
            public void onOk() {
                DetalleFondoPresenter.this.a(detCtasEvent2);
            }
        };
        r1.handleWSResponse(detCtasEvent);
    }

    /* access modifiers changed from: private */
    public void a(DetCtasEvent detCtasEvent) {
        try {
            ((DetalleFondoView) getBaseView()).suscribirFondo(((MovCtasResponseBean) detCtasEvent.getBeanResponse()).movCtasBodyResponseBean);
        } catch (Exception e) {
            Log.e(WSErrorHandlerConstants.LOG_TAG, "onCnsDetCtasIndResultOk: ", e);
        }
    }
}
