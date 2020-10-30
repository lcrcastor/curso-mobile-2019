package ar.com.santander.rio.mbanking.app.module.nuevopago;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.base.BasePresenter;
import ar.com.santander.rio.mbanking.app.base.BaseWSResponseHandler;
import ar.com.santander.rio.mbanking.app.ui.constants.NuevoPagoServiciosConstants;
import ar.com.santander.rio.mbanking.app.ui.constants.WSErrorHandlerConstants;
import ar.com.santander.rio.mbanking.app.ui.fragments.FragmentConstants;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.managers.security.SoftTokenManager;
import ar.com.santander.rio.mbanking.services.events.PagoServicioCBEvent;
import ar.com.santander.rio.mbanking.services.events.PagoServiciosEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeOption;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CuentaDebitoBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.DatosDeudaBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.PagoServicioCBBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.PagoServicioCBDeudaBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.PagoServiciosBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.PagoServiciosBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.versions.VPagoServicio;
import ar.com.santander.rio.mbanking.utils.UtilCurrency;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

public class ConfirmarPagoServicioPresenter extends BasePresenter<ConfirmarPagoServicioView> {
    private String a = "";
    private String b = "";
    private String c = "";
    private String d = "";
    private String e = "";
    protected Context mContext;
    protected SoftTokenManager mSoftTokenManager;

    public ConfirmarPagoServicioPresenter(Bus bus, IDataManager iDataManager, SoftTokenManager softTokenManager, Context context) {
        super(bus, iDataManager);
        this.mSoftTokenManager = softTokenManager;
        this.mContext = context;
    }

    public void pagoServicio(CuentaDebitoBean cuentaDebitoBean, PagoServicioCBDeudaBean pagoServicioCBDeudaBean) {
        if (!this.e.equalsIgnoreCase(NuevoPagoServiciosConstants.ORIGEN_CB)) {
            this.mDataManager.getPagoServicios(new PagoServiciosBodyRequestBean(cuentaDebitoBean, pagoServicioCBDeudaBean, this.d, this.e.equalsIgnoreCase(NuevoPagoServiciosConstants.ORIGEN_AGENDA) ? "0" : "1"), ((ConfirmarPagoServicioView) getBaseView()).getActivity(), false);
            return;
        }
        cuentaDebitoBean.setDescCtaDebito(null);
        this.mDataManager.pagoServicioCB(new PagoServicioCBBodyRequestBean(cuentaDebitoBean, pagoServicioCBDeudaBean, this.d, "1"), ((ConfirmarPagoServicioView) getBaseView()).getActivity());
    }

    @Subscribe
    public void onPagoServicio(PagoServiciosEvent pagoServiciosEvent) {
        ((ConfirmarPagoServicioView) getBaseView()).dismissProgressIndicator();
        final PagoServiciosEvent pagoServiciosEvent2 = pagoServiciosEvent;
        AnonymousClass1 r1 = new BaseWSResponseHandler(this.mContext, TypeOption.TRANSACTIONAL_FINAL_VIEW, FragmentConstants.PAGO_SERVICIOS, getBaseView(), (BaseActivity) this.mContext) {
            public void onOk() {
                ConfirmarPagoServicioPresenter.this.a(pagoServiciosEvent2);
            }
        };
        r1.handleWSResponse(pagoServiciosEvent);
    }

    /* access modifiers changed from: private */
    public void a(PagoServiciosEvent pagoServiciosEvent) {
        try {
            PagoServiciosBodyResponseBean pagoServiciosBodyResponseBean = pagoServiciosEvent.getResponse().pagoServiciosBodyResponseBean;
            if (TextUtils.isEmpty(pagoServiciosBodyResponseBean.alertaDA) || !pagoServiciosBodyResponseBean.alertaDA.equalsIgnoreCase("1")) {
                ((ConfirmarPagoServicioView) getBaseView()).showComprobantePago(pagoServiciosBodyResponseBean);
            } else {
                ((ConfirmarPagoServicioView) getBaseView()).showAutoDebitWarning(TextUtils.isEmpty(pagoServiciosBodyResponseBean.mensajeDA) ? "" : pagoServiciosBodyResponseBean.mensajeDA);
            }
        } catch (Exception e2) {
            Log.e(WSErrorHandlerConstants.LOG_TAG, "onPagoServicioResultOk: ", e2);
            e2.fillInStackTrace();
        }
    }

    @Subscribe
    public void onPagoCBServicio(PagoServicioCBEvent pagoServicioCBEvent) {
        ((ConfirmarPagoServicioView) getBaseView()).dismissProgressIndicator();
        final PagoServicioCBEvent pagoServicioCBEvent2 = pagoServicioCBEvent;
        AnonymousClass2 r1 = new BaseWSResponseHandler(this.mContext, TypeOption.TRANSACTIONAL_FINAL_VIEW, FragmentConstants.PAGO_SERVICIOS, getBaseView(), (BaseActivity) this.mContext) {
            public void onOk() {
                ConfirmarPagoServicioPresenter.this.a(pagoServicioCBEvent2);
            }
        };
        r1.handleWSResponse(pagoServicioCBEvent);
    }

    /* access modifiers changed from: private */
    public void a(PagoServicioCBEvent pagoServicioCBEvent) {
        try {
            PagoServiciosBodyResponseBean pagoServiciosBodyResponseBean = pagoServicioCBEvent.getResponse().pagoServicioCBBodyResponseBean;
            if (TextUtils.isEmpty(pagoServiciosBodyResponseBean.alertaDA) || !pagoServiciosBodyResponseBean.alertaDA.equalsIgnoreCase("1")) {
                ((ConfirmarPagoServicioView) getBaseView()).showComprobantePago(pagoServiciosBodyResponseBean);
            } else {
                ((ConfirmarPagoServicioView) getBaseView()).showAutoDebitWarning(TextUtils.isEmpty(pagoServiciosBodyResponseBean.mensajeDA) ? "" : pagoServiciosBodyResponseBean.mensajeDA);
            }
        } catch (Exception e2) {
            Log.e(WSErrorHandlerConstants.LOG_TAG, "onPagoServicioCBResultOk: ", e2);
            e2.fillInStackTrace();
        }
    }

    public void showConfirmarPago() {
        ((ConfirmarPagoServicioView) getBaseView()).showConfirmarPago();
    }

    public void onPagar(CuentaDebitoBean cuentaDebitoBean, DatosDeudaBean datosDeudaBean, String str, String str2, String str3) {
        DatosDeudaBean datosDeudaBean2 = datosDeudaBean;
        this.d = str2;
        this.e = str3;
        if (!TextUtils.isEmpty(datosDeudaBean2.datosEmpresa.tipoPago)) {
            if (datosDeudaBean2.datosEmpresa.tipoPago.equalsIgnoreCase("2")) {
                if (TextUtils.isEmpty(datosDeudaBean2.cuitEmpleador)) {
                    this.a = datosDeudaBean2.cuit;
                } else {
                    this.a = datosDeudaBean2.cuitEmpleador;
                }
                this.b = datosDeudaBean2.cur;
                this.c = datosDeudaBean2.datAdicionales.replace("-", "");
            } else if (datosDeudaBean2.datosEmpresa.tipoPago.equalsIgnoreCase("3")) {
                this.a = "";
                this.b = "";
                this.c = "";
            } else {
                this.a = "";
                this.b = "";
                this.c = "";
            }
        }
        String formattedAmountInArsFromString = UtilCurrency.getFormattedAmountInArsFromString(datosDeudaBean2.importe);
        ((ConfirmarPagoServicioView) getBaseView()).showProgressIndicator(VPagoServicio.nameService);
        PagoServicioCBDeudaBean pagoServicioCBDeudaBean = new PagoServicioCBDeudaBean(datosDeudaBean2.datosEmpresa.empServ, datosDeudaBean2.identificacion, formattedAmountInArsFromString, datosDeudaBean2.moneda, datosDeudaBean2.factura, datosDeudaBean2.vencimiento, datosDeudaBean2.infoAdicional, this.b, this.a, this.c);
        pagoServicio(cuentaDebitoBean, pagoServicioCBDeudaBean);
    }
}
