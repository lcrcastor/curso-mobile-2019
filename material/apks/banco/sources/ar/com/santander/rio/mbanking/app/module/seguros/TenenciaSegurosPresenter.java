package ar.com.santander.rio.mbanking.app.module.seguros;

import android.content.Context;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.base.BasePresenter;
import ar.com.santander.rio.mbanking.app.base.BaseWSResponseHandler;
import ar.com.santander.rio.mbanking.app.ui.fragments.FragmentConstants;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.services.events.GetCotizacionSeguroMovilEvent;
import ar.com.santander.rio.mbanking.services.events.GetSegurosEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeOption;
import ar.com.santander.rio.mbanking.services.model.general.Tarjeta;
import ar.com.santander.rio.mbanking.services.soap.beans.body.LinkSeguroBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.SegurosBean;
import ar.com.santander.rio.mbanking.services.soap.versions.VGetCotizacionSeguroMovil;
import ar.com.santander.rio.mbanking.services.soap.versions.VGetSeguros;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

public class TenenciaSegurosPresenter extends BasePresenter<TenenciaSegurosView> {
    private Context a;
    private SessionManager b;

    public TenenciaSegurosPresenter(Bus bus, IDataManager iDataManager, Context context, SessionManager sessionManager) {
        super(bus, iDataManager);
        this.a = context;
        this.b = sessionManager;
    }

    public void onCreatePage(SegurosBean segurosBean, LinkSeguroBean linkSeguroBean) {
        ((TenenciaSegurosView) getBaseView()).setTenenciaSegurosView(Boolean.valueOf(true), segurosBean, linkSeguroBean);
    }

    private boolean a() {
        boolean z = (this.b.getLoginUnico().getProductos().getCuentas() == null || this.b.getLoginUnico().getProductos().getCuentas().getCuentas() == null || this.b.getLoginUnico().getProductos().getCuentas().getCuentas().isEmpty()) ? false : true;
        if (!(this.b.getLoginUnico().getProductos().getTarjetas() == null || this.b.getLoginUnico().getProductos().getTarjetas().getTarjetas() == null || this.b.getLoginUnico().getProductos().getTarjetas().getTarjetas().isEmpty())) {
            for (Tarjeta clase : this.b.getLoginUnico().getProductos().getTarjetas().getTarjetas()) {
                if (!clase.getClase().equalsIgnoreCase("T")) {
                    z = true;
                }
            }
        }
        return z;
    }

    public void consultarTenenciaSeguros() {
        if (a()) {
            ((TenenciaSegurosView) getBaseView()).showProgressIndicator(VGetSeguros.nameService);
            this.mDataManager.getSeguros();
            return;
        }
        ((TenenciaSegurosView) getBaseView()).setBackgroundVisibleInit();
        ((TenenciaSegurosView) getBaseView()).setTenenciaSegurosView(Boolean.valueOf(false), null, null);
    }

    @Subscribe
    public void onGetSeguros(GetSegurosEvent getSegurosEvent) {
        ((TenenciaSegurosView) getBaseView()).dismissProgressIndicator();
        final GetSegurosEvent getSegurosEvent2 = getSegurosEvent;
        AnonymousClass1 r1 = new BaseWSResponseHandler(this.a, TypeOption.INITIAL_VIEW, FragmentConstants.SEGUROS, getBaseView(), (BaseActivity) this.a) {
            /* access modifiers changed from: protected */
            public void onOk() {
                TenenciaSegurosPresenter.this.a(getSegurosEvent2);
            }
        };
        r1.handleWSResponse(getSegurosEvent);
        try {
            ((TenenciaSegurosView) getBaseView()).setBackgroundVisibleInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* access modifiers changed from: private */
    public void a(GetSegurosEvent getSegurosEvent) {
        try {
            ((TenenciaSegurosView) getBaseView()).setTenenciaSegurosView(Boolean.valueOf(true), getSegurosEvent.getResponse().getSegurosBodyResponseBean.getSeguros(), getSegurosEvent.getResponse().getSegurosBodyResponseBean.getSeguros().getLinkSeguroBean());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void obtenerCotizacionSeguroMovil(String str, String str2, String str3) {
        ((TenenciaSegurosView) getBaseView()).showProgressIndicator(VGetCotizacionSeguroMovil.nameService);
        this.mDataManager.getCotizacionSeguroMovil();
    }

    @Subscribe
    public void onGetCotizacionSeguroMovil(GetCotizacionSeguroMovilEvent getCotizacionSeguroMovilEvent) {
        ((TenenciaSegurosView) getBaseView()).dismissProgressIndicator();
        final GetCotizacionSeguroMovilEvent getCotizacionSeguroMovilEvent2 = getCotizacionSeguroMovilEvent;
        AnonymousClass2 r1 = new BaseWSResponseHandler(this.a, TypeOption.INTERMDIATE_VIEW, FragmentConstants.SEGUROS, getBaseView(), this.a.getString(R.string.IDXX_SEGUROS_LBL_TITULO_TENENCIA), (BaseActivity) this.a) {
            /* access modifiers changed from: protected */
            public void onOk() {
                TenenciaSegurosPresenter.this.a(getCotizacionSeguroMovilEvent2);
            }
        };
        r1.handleWSResponse(getCotizacionSeguroMovilEvent);
    }

    /* access modifiers changed from: private */
    public void a(GetCotizacionSeguroMovilEvent getCotizacionSeguroMovilEvent) {
        ((TenenciaSegurosView) getBaseView()).goToContratarSeguroMovil(getCotizacionSeguroMovilEvent.getResponse().getGetCotizacionSeguroMovilBodyResponseBean().getCotizacion());
    }

    public void goToContratarSeguroMovilSinCotizar() {
        ((TenenciaSegurosView) getBaseView()).goToContratarSeguroMovilSinCotizar();
    }
}
