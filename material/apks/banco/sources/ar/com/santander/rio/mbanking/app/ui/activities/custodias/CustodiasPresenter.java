package ar.com.santander.rio.mbanking.app.ui.activities.custodias;

import android.content.Context;
import android.text.TextUtils;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.base.BasePresenter;
import ar.com.santander.rio.mbanking.app.base.BaseWSResponseHandler;
import ar.com.santander.rio.mbanking.app.module.inversionesbancaprivada.analytics.InversionesAnalyticsImpl;
import ar.com.santander.rio.mbanking.app.ui.activities.custodias.CustodiasContract.Presenter;
import ar.com.santander.rio.mbanking.app.ui.activities.custodias.CustodiasContract.View;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.services.events.TenenciaCustodiaEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeOption;
import ar.com.santander.rio.mbanking.services.soap.beans.body.Leyenda;
import ar.com.santander.rio.mbanking.services.soap.beans.body.TenenciaCustodiaBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.versions.VTenenciaCustodia;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

public class CustodiasPresenter extends BasePresenter<View> implements Presenter {
    private SessionManager a;
    private Context b;
    private TenenciaCustodiaBodyResponseBean c;
    private InversionesAnalyticsImpl d;

    public CustodiasPresenter(Bus bus, IDataManager iDataManager, Context context, SessionManager sessionManager, AnalyticsManager analyticsManager) {
        super(bus, iDataManager);
        this.b = context;
        this.a = sessionManager;
        this.d = new InversionesAnalyticsImpl(context, analyticsManager);
    }

    public void getCustodia(String str, int i, int i2, String str2) {
        String str3;
        String str4;
        ((View) this.mBaseView).showProgressIndicator(VTenenciaCustodia.nameService);
        if (i < 0) {
            str4 = "RTL";
            str3 = "08";
        } else {
            str4 = "BP";
            str3 = "02";
        }
        this.mDataManager.getCustodia(str3, i > -1 ? String.valueOf(i) : "000", String.valueOf(i2), str4);
    }

    @Subscribe
    public void onGetCustodia(TenenciaCustodiaEvent tenenciaCustodiaEvent) {
        ((View) this.mBaseView).dismissProgressIndicator();
        final TenenciaCustodiaEvent tenenciaCustodiaEvent2 = tenenciaCustodiaEvent;
        AnonymousClass1 r1 = new BaseWSResponseHandler(this.b, TypeOption.INITIAL_VIEW, VTenenciaCustodia.nameService, this.mBaseView, (BaseActivity) this.b) {
            /* access modifiers changed from: protected */
            public void onOk() {
                CustodiasPresenter.this.a(tenenciaCustodiaEvent2);
            }

            /* access modifiers changed from: protected */
            public void onRes4Error() {
                CustodiasPresenter.this.a();
            }

            /* access modifiers changed from: protected */
            public void commonAllErrorsAfterProcess(WebServiceEvent webServiceEvent) {
                CustodiasPresenter.this.b();
            }
        };
        r1.handleWSResponse(tenenciaCustodiaEvent);
    }

    /* access modifiers changed from: private */
    public void a(TenenciaCustodiaEvent tenenciaCustodiaEvent) {
        this.c = tenenciaCustodiaEvent.response.getTenenciaCustodiaBodyResponseBean();
        if (!TextUtils.isEmpty(this.c.getMsjeError())) {
            ((View) this.mBaseView).setPartialErrorMsg(this.c.getMsjeError(), 0);
            this.d.TenenciaCustodiaErrorParcial();
        } else {
            ((View) this.mBaseView).setPartialErrorMsg("", 8);
        }
        if (!(this.c.getListaLeyendas() == null || this.c.getListaLeyendas().lstLeyendas == null)) {
            ((View) this.mBaseView).setLeyenda(((Leyenda) this.c.getListaLeyendas().lstLeyendas.get(0)).titulo, ((Leyenda) this.c.getListaLeyendas().lstLeyendas.get(0)).descripcion);
        }
        if (this.c.getCuenta() != null) {
            ((View) this.mBaseView).okResult();
            ((View) this.mBaseView).setCustodia(this.c.getCuenta());
            getRecyclerData("Pesos");
            return;
        }
        a();
    }

    /* access modifiers changed from: private */
    public void a() {
        ((View) this.mBaseView).errorResult();
    }

    public void getRecyclerData(String str) {
        if (this.c != null && this.c.getCuenta() != null) {
            if (str.equalsIgnoreCase("Pesos")) {
                this.d.TenenciaCustodiaMonedaPeso();
                if (this.c.getCuenta().getPesos() == null || this.c.getCuenta().getPesos().getCustodiasList() == null) {
                    ((View) this.mBaseView).setErrorCustodias();
                } else {
                    ((View) this.mBaseView).setRecyclerData(this.c.getCuenta().getPesos().getCustodiasList());
                }
            } else {
                this.d.TenenciaTituloValoresMonedausd();
                if (this.c.getCuenta().getOtraMoneda() == null || this.c.getCuenta().getOtraMoneda().getCustodiasList() == null) {
                    ((View) this.mBaseView).setErrorCustodias();
                } else {
                    ((View) this.mBaseView).setRecyclerData(this.c.getCuenta().getOtraMoneda().getCustodiasList());
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public void b() {
        ((View) this.mBaseView).unknowError();
    }
}
