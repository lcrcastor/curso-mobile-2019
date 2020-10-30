package ar.com.santander.rio.mbanking.app.module.funds;

import android.content.Context;
import android.util.Log;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.base.BasePresenter;
import ar.com.santander.rio.mbanking.app.base.BaseWSResponseHandler;
import ar.com.santander.rio.mbanking.app.ui.constants.WSErrorHandlerConstants;
import ar.com.santander.rio.mbanking.app.ui.fragments.FragmentConstants;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.services.events.SuscribirFondoEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeOption;
import ar.com.santander.rio.mbanking.services.model.general.Cuenta;
import ar.com.santander.rio.mbanking.services.soap.beans.body.AlertaEvaluacionRiesgoBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CuentaFondosBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.FondoBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.SimularSuscripcionFondoBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.SuscribirFondoBodyResponseBean;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

public class EvaluacionRiesgoSuscribirFondoPresenter extends BasePresenter<EvaluacionRiesgoSuscribirFondoView> {
    FondoBean a;
    CuentaFondosBean b;
    String c;
    Cuenta d;
    SimularSuscripcionFondoBodyResponseBean e;
    private boolean f = true;

    public EvaluacionRiesgoSuscribirFondoPresenter(Bus bus, IDataManager iDataManager) {
        super(bus, iDataManager);
    }

    public void onCreatePage(AlertaEvaluacionRiesgoBean alertaEvaluacionRiesgoBean, String str, String str2) {
        ((EvaluacionRiesgoSuscribirFondoView) getBaseView()).setEvaluacionRiesgoSuscripcionView(alertaEvaluacionRiesgoBean, str, str2);
    }

    public Context getContext() {
        return ((EvaluacionRiesgoSuscribirFondoView) getBaseView()).getContext();
    }

    /* JADX WARNING: Removed duplicated region for block: B:16:0x0064  */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x006e  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void callSuscripcionFondo(ar.com.santander.rio.mbanking.services.soap.beans.body.FondoBean r18, ar.com.santander.rio.mbanking.services.soap.beans.body.CuentaFondosBean r19, java.lang.String r20, ar.com.santander.rio.mbanking.services.model.general.Cuenta r21, ar.com.santander.rio.mbanking.services.soap.beans.body.SimularSuscripcionFondoBodyResponseBean r22, java.lang.String r23, java.lang.String r24) {
        /*
            r17 = this;
            r0 = r17
            boolean r1 = r0.f
            if (r1 == 0) goto L_0x00a6
            r1 = 0
            r0.f = r1
            r1 = r18
            r0.a = r1
            r2 = r19
            r0.b = r2
            r6 = r20
            r0.c = r6
            r3 = r21
            r0.d = r3
            r4 = r22
            r0.e = r4
            ar.com.santander.rio.mbanking.app.base.IBaseView r5 = r17.getBaseView()
            if (r5 == 0) goto L_0x002e
            ar.com.santander.rio.mbanking.app.base.IBaseView r5 = r17.getBaseView()
            ar.com.santander.rio.mbanking.app.module.funds.EvaluacionRiesgoSuscribirFondoView r5 = (ar.com.santander.rio.mbanking.app.module.funds.EvaluacionRiesgoSuscribirFondoView) r5
            java.lang.String r7 = "simularSuscripcionFondo"
            r5.showProgressIndicator(r7)
        L_0x002e:
            java.lang.String r5 = "0"
            java.lang.String r7 = r18.getMoneda()     // Catch:{ Exception -> 0x0057 }
            java.lang.String r8 = ar.com.santander.rio.mbanking.app.ui.Constants.SYMBOL_CURRENCY_DOLLAR_DESCRIPTION     // Catch:{ Exception -> 0x0057 }
            boolean r7 = r7.equalsIgnoreCase(r8)     // Catch:{ Exception -> 0x0057 }
            if (r7 != 0) goto L_0x0054
            java.lang.String r7 = r18.getMoneda()     // Catch:{ Exception -> 0x0057 }
            android.text.Spanned r7 = android.text.Html.fromHtml(r7)     // Catch:{ Exception -> 0x0057 }
            java.lang.String r7 = r7.toString()     // Catch:{ Exception -> 0x0057 }
            java.lang.String r7 = ar.com.santander.rio.mbanking.utils.UtilCurrency.getSimbolCurrencyFromDescription(r7)     // Catch:{ Exception -> 0x0057 }
            java.lang.String r8 = ar.com.santander.rio.mbanking.app.ui.Constants.SYMBOL_CURRENCY_DOLAR     // Catch:{ Exception -> 0x0057 }
            boolean r7 = r7.equalsIgnoreCase(r8)     // Catch:{ Exception -> 0x0057 }
            if (r7 == 0) goto L_0x0057
        L_0x0054:
            java.lang.String r7 = "2"
            goto L_0x0058
        L_0x0057:
            r7 = r5
        L_0x0058:
            java.lang.String r5 = r21.getTipo()
            java.lang.String r8 = "02"
            boolean r5 = r5.equalsIgnoreCase(r8)
            if (r5 == 0) goto L_0x006e
            java.lang.String r5 = "0"
            if (r7 != r5) goto L_0x006b
            java.lang.String r5 = "09"
            goto L_0x0072
        L_0x006b:
            java.lang.String r5 = "10"
            goto L_0x0072
        L_0x006e:
            java.lang.String r5 = r21.getTipo()
        L_0x0072:
            r8 = r5
            ar.com.santander.rio.mbanking.managers.data.IDataManager r5 = r0.mDataManager
            java.lang.String r9 = r19.getTipoCuenta()
            java.lang.String r10 = r19.getSucursalCuenta()
            java.lang.String r11 = r19.getNumero()
            java.lang.String r12 = r18.getId()
            java.lang.String r13 = r21.getNroSuc()
            java.lang.String r14 = r21.getNumero()
            java.lang.String r15 = r22.getPorcentComision()
            java.lang.String r16 = r22.getCotizaCambio()
            r1 = r5
            r2 = r9
            r3 = r10
            r4 = r11
            r5 = r12
            r9 = r13
            r10 = r14
            r11 = r15
            r12 = r16
            r13 = r23
            r14 = r24
            r1.suscribirFondo(r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14)
        L_0x00a6:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: ar.com.santander.rio.mbanking.app.module.funds.EvaluacionRiesgoSuscribirFondoPresenter.callSuscripcionFondo(ar.com.santander.rio.mbanking.services.soap.beans.body.FondoBean, ar.com.santander.rio.mbanking.services.soap.beans.body.CuentaFondosBean, java.lang.String, ar.com.santander.rio.mbanking.services.model.general.Cuenta, ar.com.santander.rio.mbanking.services.soap.beans.body.SimularSuscripcionFondoBodyResponseBean, java.lang.String, java.lang.String):void");
    }

    @Subscribe
    public void onSuscribirFondo(SuscribirFondoEvent suscribirFondoEvent) {
        this.f = true;
        final SuscribirFondoEvent suscribirFondoEvent2 = suscribirFondoEvent;
        AnonymousClass1 r1 = new BaseWSResponseHandler(this, getContext(), TypeOption.TRANSACTIONAL_FINAL_VIEW, FragmentConstants.FONDOS_INVERSION, getBaseView(), true, true, (BaseActivity) getContext()) {
            final /* synthetic */ EvaluacionRiesgoSuscribirFondoPresenter b;

            {
                this.b = r10;
            }

            public void onOk() {
                this.b.onSuscribirFondoResultOk(suscribirFondoEvent2.getResponseBean().getSuscribirFondoBodyResponseBean());
            }
        };
        r1.handleWSResponse(suscribirFondoEvent);
    }

    public void onSuscribirFondoResultOk(SuscribirFondoBodyResponseBean suscribirFondoBodyResponseBean) {
        try {
            ((EvaluacionRiesgoSuscribirFondoView) getBaseView()).gotoComprobante(suscribirFondoBodyResponseBean);
        } catch (Exception e2) {
            Log.e(WSErrorHandlerConstants.LOG_TAG, "onSuscribirFondoResultOk: ", e2);
            e2.fillInStackTrace();
        }
    }
}
