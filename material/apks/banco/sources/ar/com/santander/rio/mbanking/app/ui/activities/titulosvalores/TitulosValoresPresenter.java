package ar.com.santander.rio.mbanking.app.ui.activities.titulosvalores;

import android.content.Context;
import android.text.TextUtils;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.base.BasePresenter;
import ar.com.santander.rio.mbanking.app.base.BaseWSResponseHandler;
import ar.com.santander.rio.mbanking.app.module.inversionesbancaprivada.analytics.InversionesAnalyticsImpl;
import ar.com.santander.rio.mbanking.app.ui.activities.titulosvalores.TitulosValoresContract.Presenter;
import ar.com.santander.rio.mbanking.app.ui.activities.titulosvalores.TitulosValoresContract.View;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.services.events.TenenciaTitulosEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeOption;
import ar.com.santander.rio.mbanking.services.soap.beans.body.TenenciaTitulosBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.versions.VTenenciaTitulosValores;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

public class TitulosValoresPresenter extends BasePresenter<View> implements Presenter {
    private SessionManager a;
    private Context b;
    private TenenciaTitulosBodyResponseBean c;
    private InversionesAnalyticsImpl d;

    public void changeCuenta(int i) {
    }

    public TitulosValoresPresenter(Bus bus, IDataManager iDataManager, Context context, SessionManager sessionManager, AnalyticsManager analyticsManager) {
        super(bus, iDataManager);
        this.b = context;
        this.a = sessionManager;
        this.d = new InversionesAnalyticsImpl(context, analyticsManager);
    }

    public void getTitulosValores(String str, int i, int i2, String str2) {
        String str3;
        String str4;
        ((View) this.mBaseView).showProgressIndicator(VTenenciaTitulosValores.nameService);
        if (i < 0) {
            str4 = "RTL";
            str3 = "08";
            this.d.TenenciaTitulosValoresrtl();
        } else {
            str4 = "BP";
            str3 = "02";
            this.d.TenenciaTitulosValoresbp();
        }
        this.mDataManager.getTenenciaTitulos(str3, i > -1 ? String.valueOf(i) : "000", String.valueOf(i2), str4);
    }

    public void getRecyclerData(String str) {
        if (this.c == null || this.c.getCuenta() == null || this.c.getCuenta().getTitulosValores() == null) {
            ((View) this.mBaseView).setErrorTitulosValores();
        } else if (str.equalsIgnoreCase("Pesos")) {
            this.d.TenenciaTituloValoresMonedaPeso();
            if (this.c.getCuenta().getTitulosValores().getPesos() != null) {
                ((View) this.mBaseView).setRecyclerData(this.c.getCuenta().getTitulosValores().getPesos().getTitulos());
            } else {
                ((View) this.mBaseView).setErrorTitulosValores();
            }
        } else {
            this.d.TenenciaTituloValoresMonedausd();
            if (this.c.getCuenta().getTitulosValores().getDolares() != null) {
                ((View) this.mBaseView).setRecyclerData(this.c.getCuenta().getTitulosValores().getDolares().getTitulos());
            } else {
                ((View) this.mBaseView).setErrorTitulosValores();
            }
        }
    }

    @Subscribe
    public void onGetTitulosValores(TenenciaTitulosEvent tenenciaTitulosEvent) {
        ((View) this.mBaseView).dismissProgressIndicator();
        final TenenciaTitulosEvent tenenciaTitulosEvent2 = tenenciaTitulosEvent;
        AnonymousClass1 r1 = new BaseWSResponseHandler(this.b, TypeOption.INITIAL_VIEW, VTenenciaTitulosValores.nameService, this.mBaseView, (BaseActivity) this.b) {
            /* access modifiers changed from: protected */
            public void onOk() {
                TitulosValoresPresenter.this.a(tenenciaTitulosEvent2);
            }

            /* access modifiers changed from: protected */
            public void onRes4Error() {
                TitulosValoresPresenter.this.a();
            }

            /* access modifiers changed from: protected */
            public void commonAllErrorsAfterProcess(WebServiceEvent webServiceEvent) {
                TitulosValoresPresenter.this.b();
            }
        };
        r1.handleWSResponse(tenenciaTitulosEvent);
    }

    /* access modifiers changed from: private */
    public void a(TenenciaTitulosEvent tenenciaTitulosEvent) {
        this.c = tenenciaTitulosEvent.response.getTenenciaTitulosBodyResponseBean();
        ((View) this.mBaseView).okResult();
        if (!TextUtils.isEmpty(this.c.getMsjeError())) {
            ((View) this.mBaseView).setPartialErrorMsg(this.c.getMsjeError(), 0);
            this.d.TenenciaTitulosValoresErrorParcial();
        } else {
            ((View) this.mBaseView).setPartialErrorMsg("", 8);
        }
        if (this.c.getCuenta() != null) {
            ((View) this.mBaseView).setTitulosValores(this.c.getCuenta());
            getRecyclerData("Pesos");
            if (this.c.getListaLeyendas().lstLeyendas != null) {
                ((View) this.mBaseView).setLeyenda(this.c.getListaLeyendas().getLeyendaByTag("DET_TITULOS").getTitulo(), this.c.getListaLeyendas().getLeyendaByTag("DET_TITULOS").getDescripcion());
                return;
            }
            return;
        }
        a();
    }

    /* access modifiers changed from: private */
    public void a() {
        ((View) this.mBaseView).errorResult();
    }

    /* access modifiers changed from: private */
    public void b() {
        ((View) this.mBaseView).unknowError();
    }
}
