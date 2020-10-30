package ar.com.santander.rio.mbanking.app.module.funds;

import android.content.Context;
import android.util.Log;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.base.BasePresenter;
import ar.com.santander.rio.mbanking.app.base.BaseWSResponseHandler;
import ar.com.santander.rio.mbanking.app.ui.constants.FondosConstants;
import ar.com.santander.rio.mbanking.app.ui.constants.WSErrorHandlerConstants;
import ar.com.santander.rio.mbanking.app.ui.fragments.FragmentConstants;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.services.events.DetCtasEvent;
import ar.com.santander.rio.mbanking.services.events.GetCotizacionesFondoEvent;
import ar.com.santander.rio.mbanking.services.events.GetDetalleFondoEvent;
import ar.com.santander.rio.mbanking.services.events.GetInfoAdmFondosEvent;
import ar.com.santander.rio.mbanking.services.events.GetMovimientosFondoEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeOption;
import ar.com.santander.rio.mbanking.services.soap.beans.GetDetalleFondoResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.MovCtasResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CuentaFondosBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.FondoBean;
import ar.com.santander.rio.mbanking.services.soap.versions.VGetCotizaciones;
import ar.com.santander.rio.mbanking.services.soap.versions.VGetDetalleFondo;
import ar.com.santander.rio.mbanking.services.soap.versions.VGetInfoAdmFondos;
import ar.com.santander.rio.mbanking.services.soap.versions.VGetMovimientosFondo;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

public class SeleccionarInformacionFondoPresenter extends BasePresenter<SeleccionarInformacionFondoView> {
    private Context a;
    private FondoBean b;
    private String c;

    public SeleccionarInformacionFondoPresenter(Bus bus, IDataManager iDataManager, Context context) {
        super(bus, iDataManager);
        this.a = context;
    }

    public void gotoNextFlow(String str, FondoBean fondoBean, CuentaFondosBean cuentaFondosBean) {
        if (str.equalsIgnoreCase(FondosConstants.ORIGEN_INFORMACION)) {
            a(fondoBean);
        } else if (str.equalsIgnoreCase(FondosConstants.ORIGEN_SUSCRIBIR)) {
            ((SeleccionarInformacionFondoView) getBaseView()).gotoSuscribirFondo(null, fondoBean);
        } else if (str.equalsIgnoreCase(FondosConstants.ORIGEN_TRANSFERIR)) {
            ((SeleccionarInformacionFondoView) getBaseView()).gotoTransferirFondo(fondoBean);
        } else if (str.equalsIgnoreCase(FondosConstants.ORIGEN_ULTIMOS_MOVIMIENTOS)) {
            callGetMovimiento(fondoBean, cuentaFondosBean);
        }
    }

    public void callGetMovimiento(FondoBean fondoBean, CuentaFondosBean cuentaFondosBean) {
        this.b = fondoBean;
        ((SeleccionarInformacionFondoView) getBaseView()).showProgressIndicator(VGetMovimientosFondo.nameService);
        this.mDataManager.getMovimientosFondo(fondoBean.getId(), cuentaFondosBean.getNumero());
    }

    @Subscribe
    public void onGetMovimientosFondo(GetMovimientosFondoEvent getMovimientosFondoEvent) {
        ((SeleccionarInformacionFondoView) getBaseView()).dismissProgressIndicator();
        final GetMovimientosFondoEvent getMovimientosFondoEvent2 = getMovimientosFondoEvent;
        AnonymousClass1 r1 = new BaseWSResponseHandler(this.a, TypeOption.INTERMDIATE_VIEW, FragmentConstants.FONDOS_INVERSION, getBaseView(), (BaseActivity) this.a) {
            public void onOk() {
                SeleccionarInformacionFondoPresenter.this.onGetMovimientoFondoOk(getMovimientosFondoEvent2);
            }
        };
        r1.handleWSResponse(getMovimientosFondoEvent);
    }

    public void onGetMovimientoFondoOk(GetMovimientosFondoEvent getMovimientosFondoEvent) {
        try {
            ((SeleccionarInformacionFondoView) getBaseView()).gotoUltimosMovimientosFondo(this.b, getMovimientosFondoEvent.getResponseBean().getGetMovimientosFondoBodyResponseBean().getListaMovimientosFondosBean().getMovimientosFondosBean());
        } catch (Exception e) {
            Log.e(WSErrorHandlerConstants.LOG_TAG, "onGetMovimientoFondoOk: ", e);
            e.fillInStackTrace();
        }
    }

    private void a(FondoBean fondoBean) {
        this.b = fondoBean;
        ((SeleccionarInformacionFondoView) getBaseView()).showProgressIndicator(VGetDetalleFondo.nameService);
        this.mDataManager.getDetalleFondo(fondoBean.getId());
    }

    @Subscribe
    public void getDetalleFondo(GetDetalleFondoEvent getDetalleFondoEvent) {
        ((SeleccionarInformacionFondoView) getBaseView()).dismissProgressIndicator();
        final GetDetalleFondoEvent getDetalleFondoEvent2 = getDetalleFondoEvent;
        AnonymousClass2 r1 = new BaseWSResponseHandler(this.a, TypeOption.INTERMDIATE_VIEW, FragmentConstants.FONDOS_INVERSION, getBaseView(), (BaseActivity) this.a) {
            public void onOk() {
                SeleccionarInformacionFondoPresenter.this.a(getDetalleFondoEvent2);
            }
        };
        r1.handleWSResponse(getDetalleFondoEvent);
    }

    /* access modifiers changed from: private */
    public void a(GetDetalleFondoEvent getDetalleFondoEvent) {
        try {
            ((SeleccionarInformacionFondoView) getBaseView()).gotoInformacionFondo(((GetDetalleFondoResponseBean) getDetalleFondoEvent.getBeanResponse()).getGetDetalleFondoBodyResponseBean().getInformacionFondo(), this.b);
        } catch (Exception e) {
            Log.e(WSErrorHandlerConstants.LOG_TAG, "onGetDetalleFondoResultOk: ", e);
            e.fillInStackTrace();
        }
    }

    @Subscribe
    public void cnsDetCtasInd(DetCtasEvent detCtasEvent) {
        ((SeleccionarInformacionFondoView) getBaseView()).dismissProgressIndicator();
        final DetCtasEvent detCtasEvent2 = detCtasEvent;
        AnonymousClass3 r1 = new BaseWSResponseHandler(this.a, TypeOption.INTERMDIATE_VIEW, FragmentConstants.FONDOS_INVERSION, getBaseView(), (BaseActivity) this.a) {
            public void onOk() {
                SeleccionarInformacionFondoPresenter.this.a(detCtasEvent2);
            }
        };
        r1.handleWSResponse(detCtasEvent);
    }

    /* access modifiers changed from: private */
    public void a(DetCtasEvent detCtasEvent) {
        try {
            ((SeleccionarInformacionFondoView) getBaseView()).gotoSuscribirFondo(((MovCtasResponseBean) detCtasEvent.getBeanResponse()).movCtasBodyResponseBean, this.b);
        } catch (Exception e) {
            Log.e(WSErrorHandlerConstants.LOG_TAG, "onCnsDetCtasIndResultOk: ", e);
            e.fillInStackTrace();
        }
    }

    public void callGetInfoAdmFondos(String str) {
        this.c = str;
        ((SeleccionarInformacionFondoView) getBaseView()).showProgressIndicator(VGetInfoAdmFondos.nameService);
        this.mDataManager.getInfoAdmFondos();
    }

    @Subscribe
    public void getInfoAdmFondos(GetInfoAdmFondosEvent getInfoAdmFondosEvent) {
        ((SeleccionarInformacionFondoView) getBaseView()).dismissProgressIndicator();
        final GetInfoAdmFondosEvent getInfoAdmFondosEvent2 = getInfoAdmFondosEvent;
        AnonymousClass4 r1 = new BaseWSResponseHandler(this.a, TypeOption.INTERMDIATE_VIEW, FragmentConstants.FONDOS_INVERSION, getBaseView(), (BaseActivity) this.a) {
            public void onOk() {
                SeleccionarInformacionFondoPresenter.this.a(getInfoAdmFondosEvent2);
            }
        };
        r1.handleWSResponse(getInfoAdmFondosEvent);
    }

    /* access modifiers changed from: private */
    public void a(GetInfoAdmFondosEvent getInfoAdmFondosEvent) {
        try {
            ((SeleccionarInformacionFondoView) getBaseView()).gotoInfoAdmFondos(getInfoAdmFondosEvent.getResponseBean().getGetInfoAdmFondosBodyResponseBean().getListaFondosBean(), getInfoAdmFondosEvent.getResponseBean().getGetInfoAdmFondosBodyResponseBean().getLegales(), this.c);
        } catch (Exception e) {
            e.printStackTrace();
            e.fillInStackTrace();
        }
    }

    public void callGetCotizaciones() {
        ((SeleccionarInformacionFondoView) getBaseView()).showProgressIndicator(VGetCotizaciones.nameService);
        this.mDataManager.getCotizacionesFondos();
    }

    @Subscribe
    public void onGetCotizaciones(GetCotizacionesFondoEvent getCotizacionesFondoEvent) {
        ((SeleccionarInformacionFondoView) getBaseView()).dismissProgressIndicator();
        final GetCotizacionesFondoEvent getCotizacionesFondoEvent2 = getCotizacionesFondoEvent;
        AnonymousClass5 r1 = new BaseWSResponseHandler(this.a, TypeOption.INTERMDIATE_VIEW, FragmentConstants.FONDOS_INVERSION, getBaseView(), (BaseActivity) this.a) {
            public void onOk() {
                SeleccionarInformacionFondoPresenter.this.a(getCotizacionesFondoEvent2);
            }
        };
        r1.handleWSResponse(getCotizacionesFondoEvent);
    }

    /* access modifiers changed from: private */
    public void a(GetCotizacionesFondoEvent getCotizacionesFondoEvent) {
        try {
            ((SeleccionarInformacionFondoView) getBaseView()).gotoCotizacionesFondos(getCotizacionesFondoEvent.getResponseBean().getGetCotizacionesBodyResponseBean().getListaCategoriasFondosBean().getCategoriasFondosBean(), getCotizacionesFondoEvent.getResponseBean().getGetCotizacionesBodyResponseBean().getLegales());
        } catch (Exception e) {
            e.printStackTrace();
            e.fillInStackTrace();
        }
    }
}
