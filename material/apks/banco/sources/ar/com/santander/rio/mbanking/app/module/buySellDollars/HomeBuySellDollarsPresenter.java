package ar.com.santander.rio.mbanking.app.module.buySellDollars;

import android.util.Log;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.base.BasePresenter;
import ar.com.santander.rio.mbanking.app.base.BaseWSResponseHandler;
import ar.com.santander.rio.mbanking.app.ui.constants.WSErrorHandlerConstants;
import ar.com.santander.rio.mbanking.app.ui.fragments.FragmentConstants;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.services.events.GetDatosInicialesDolaresEvent;
import ar.com.santander.rio.mbanking.services.events.SimulacionDolarEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeOption;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CompraVentaDolaresCuentaBean;
import ar.com.santander.rio.mbanking.services.soap.versions.VGetDatosInicialesDolares;
import ar.com.santander.rio.mbanking.services.soap.versions.VSimulacionDolar;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

public class HomeBuySellDollarsPresenter extends BasePresenter<HomeBuySellDollarsView> {
    SessionManager a;
    boolean b = false;

    public HomeBuySellDollarsPresenter(Bus bus, IDataManager iDataManager, SessionManager sessionManager) {
        super(bus, iDataManager);
        this.a = sessionManager;
    }

    public void getDatosIniciales(String str) {
        if (!this.b) {
            this.b = true;
            ((HomeBuySellDollarsView) this.mBaseView).showProgressIndicator(VGetDatosInicialesDolares.nameService);
            this.mDataManager.getDatosInicialesDolares(str, new CompraVentaDolaresCuentaBean());
        }
    }

    public void getDatosIniciales(String str, CompraVentaDolaresCuentaBean compraVentaDolaresCuentaBean) {
        if (!this.b) {
            this.b = true;
            ((HomeBuySellDollarsView) this.mBaseView).showProgressIndicator(VGetDatosInicialesDolares.nameService);
            this.mDataManager.getDatosInicialesDolares(str, compraVentaDolaresCuentaBean);
        }
    }

    @Subscribe
    public void onGetDatosInicialesDolares(GetDatosInicialesDolaresEvent getDatosInicialesDolaresEvent) {
        ((HomeBuySellDollarsView) getBaseView()).dismissProgressIndicator();
        final GetDatosInicialesDolaresEvent getDatosInicialesDolaresEvent2 = getDatosInicialesDolaresEvent;
        AnonymousClass1 r1 = new BaseWSResponseHandler(((HomeBuySellDollarsView) getBaseView()).getContext(), TypeOption.INITIAL_VIEW, FragmentConstants.COMPRAVENTA_DOLARES, getBaseView(), (BaseActivity) ((HomeBuySellDollarsView) getBaseView()).getContext()) {
            public void onOk() {
                HomeBuySellDollarsPresenter.this.a(getDatosInicialesDolaresEvent2);
            }

            public void onRes4Error(WebServiceEvent webServiceEvent) {
                HomeBuySellDollarsPresenter.this.b(getDatosInicialesDolaresEvent2);
            }
        };
        this.b = false;
        r1.handleWSResponse(getDatosInicialesDolaresEvent);
    }

    /* access modifiers changed from: private */
    public void a(GetDatosInicialesDolaresEvent getDatosInicialesDolaresEvent) {
        try {
            ((HomeBuySellDollarsView) getBaseView()).onGetDatosInicialesDolares(getDatosInicialesDolaresEvent.getResponse());
        } catch (Exception e) {
            Log.e(WSErrorHandlerConstants.LOG_TAG, "getDatosInicialesDolaresResultOk: ", e);
            e.fillInStackTrace();
        }
    }

    /* access modifiers changed from: private */
    public void b(GetDatosInicialesDolaresEvent getDatosInicialesDolaresEvent) {
        try {
            ((HomeBuySellDollarsView) getBaseView()).showSadError(getDatosInicialesDolaresEvent.getMessageToShow());
        } catch (Exception e) {
            Log.e(WSErrorHandlerConstants.LOG_TAG, "getDatosInicialesDolaresResult4: ", e);
            e.fillInStackTrace();
        }
    }

    public void doSimulacionDolar(String str, String str2, String str3, String str4, CompraVentaDolaresCuentaBean compraVentaDolaresCuentaBean, CompraVentaDolaresCuentaBean compraVentaDolaresCuentaBean2) {
        ((HomeBuySellDollarsView) this.mBaseView).showProgressIndicator(VSimulacionDolar.nameService);
        this.mDataManager.simulacionDolar(str, str2, str3, str4, compraVentaDolaresCuentaBean, compraVentaDolaresCuentaBean2);
    }

    @Subscribe
    public void onDoSimulacionDolar(SimulacionDolarEvent simulacionDolarEvent) {
        ((HomeBuySellDollarsView) getBaseView()).dismissProgressIndicator();
        final SimulacionDolarEvent simulacionDolarEvent2 = simulacionDolarEvent;
        AnonymousClass2 r1 = new BaseWSResponseHandler(((HomeBuySellDollarsView) getBaseView()).getContext(), TypeOption.INTERMDIATE_VIEW, FragmentConstants.COMPRAVENTA_DOLARES, getBaseView(), (BaseActivity) ((HomeBuySellDollarsView) getBaseView()).getContext()) {
            public void onOk() {
                HomeBuySellDollarsPresenter.this.a(simulacionDolarEvent2);
            }

            public void onRes4Error(WebServiceEvent webServiceEvent) {
                HomeBuySellDollarsPresenter.this.b(simulacionDolarEvent2);
            }
        };
        r1.handleWSResponse(simulacionDolarEvent);
    }

    /* access modifiers changed from: private */
    public void a(SimulacionDolarEvent simulacionDolarEvent) {
        try {
            ((HomeBuySellDollarsView) getBaseView()).onSimulacionDolar(simulacionDolarEvent.getResponse());
        } catch (Exception e) {
            Log.e(WSErrorHandlerConstants.LOG_TAG, "doSimulacionDolarResultOk: ", e);
            e.fillInStackTrace();
        }
    }

    /* access modifiers changed from: private */
    public void b(SimulacionDolarEvent simulacionDolarEvent) {
        try {
            ((HomeBuySellDollarsView) getBaseView()).warnExchangeRateChanged(simulacionDolarEvent);
        } catch (Exception e) {
            Log.e(WSErrorHandlerConstants.LOG_TAG, "doSimulacionDolarResult4: ", e);
            e.fillInStackTrace();
        }
    }
}
