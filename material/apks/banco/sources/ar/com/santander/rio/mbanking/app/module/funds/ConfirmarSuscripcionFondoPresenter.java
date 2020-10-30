package ar.com.santander.rio.mbanking.app.module.funds;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.base.BasePresenter;
import ar.com.santander.rio.mbanking.app.base.BaseWSResponseHandler;
import ar.com.santander.rio.mbanking.app.ui.Constants;
import ar.com.santander.rio.mbanking.app.ui.activities.InfoActivity;
import ar.com.santander.rio.mbanking.app.ui.activities.InfoActivity.InfoType;
import ar.com.santander.rio.mbanking.app.ui.constants.TarjetasConstants;
import ar.com.santander.rio.mbanking.app.ui.constants.WSErrorHandlerConstants;
import ar.com.santander.rio.mbanking.app.ui.fragments.FragmentConstants;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListener;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.services.events.SuscribirFondoEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeOption;
import ar.com.santander.rio.mbanking.services.model.general.Cuenta;
import ar.com.santander.rio.mbanking.services.soap.beans.body.AlertaEvaluacionRiesgoBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CuentaFondosBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.FondoBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.SimularSuscripcionFondoBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.SuscribirFondoBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.versions.VSimularSuscripcionFondo;
import ar.com.santander.rio.mbanking.utils.UtilCurrency;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

public class ConfirmarSuscripcionFondoPresenter extends BasePresenter<ConfirmarSuscripcionFondoView> {
    FondoBean a;
    CuentaFondosBean b;
    String c;
    Cuenta d;
    SimularSuscripcionFondoBodyResponseBean e;
    /* access modifiers changed from: private */
    public boolean f = true;

    public void showConfirmarDialog(FondoBean fondoBean, CuentaFondosBean cuentaFondosBean, String str, Cuenta cuenta, SimularSuscripcionFondoBodyResponseBean simularSuscripcionFondoBodyResponseBean, String str2) {
    }

    public ConfirmarSuscripcionFondoPresenter(Bus bus, IDataManager iDataManager) {
        super(bus, iDataManager);
    }

    public void onCreatePage(SimularSuscripcionFondoBodyResponseBean simularSuscripcionFondoBodyResponseBean) {
        ((ConfirmarSuscripcionFondoView) getBaseView()).setConfirmarSuscripcionView(simularSuscripcionFondoBodyResponseBean);
    }

    public Context getContext() {
        return ((ConfirmarSuscripcionFondoView) getBaseView()).getContext();
    }

    public void showConfirmarLeyendaEspecial(FondoBean fondoBean, CuentaFondosBean cuentaFondosBean, String str, Cuenta cuenta, SimularSuscripcionFondoBodyResponseBean simularSuscripcionFondoBodyResponseBean, String str2) {
        this.f = true;
        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(getContext().getString(R.string.F24_41_TITLE_DIALOG_AVISO), simularSuscripcionFondoBodyResponseBean.getLeyendaEspecial(), null, null, getContext().getString(R.string.ID1_ALERT_BTN_CONTINUE), getContext().getString(R.string.IDX_ALERT_BTN_CANCEL), null);
        final FondoBean fondoBean2 = fondoBean;
        final CuentaFondosBean cuentaFondosBean2 = cuentaFondosBean;
        final String str3 = str;
        final Cuenta cuenta2 = cuenta;
        final SimularSuscripcionFondoBodyResponseBean simularSuscripcionFondoBodyResponseBean2 = simularSuscripcionFondoBodyResponseBean;
        final String str4 = str2;
        AnonymousClass1 r1 = new IDialogListener() {
            public void onItemSelected(String str) {
            }

            public void onSimpleActionButton() {
            }

            public void onPositiveButton() {
                ConfirmarSuscripcionFondoPresenter.this.a(fondoBean2, cuentaFondosBean2, str3, cuenta2, simularSuscripcionFondoBodyResponseBean2);
            }

            public void onNegativeButton() {
                if (str4.equalsIgnoreCase("DETALLE_FONDO")) {
                    ((ConfirmarSuscripcionFondoView) ConfirmarSuscripcionFondoPresenter.this.getBaseView()).getAnalyticsManager().trackEvent(((ConfirmarSuscripcionFondoView) ConfirmarSuscripcionFondoPresenter.this.getBaseView()).getContext().getString(R.string.analytics_event_category_fondos), ((ConfirmarSuscripcionFondoView) ConfirmarSuscripcionFondoPresenter.this.getBaseView()).getContext().getString(R.string.analytics_event_action_cancelar_suscripcion_cuotapartes), ((ConfirmarSuscripcionFondoView) ConfirmarSuscripcionFondoPresenter.this.getBaseView()).getContext().getString(R.string.analytics_event_label_cancelacion_suscripcion_cuotapartes));
                } else {
                    ((ConfirmarSuscripcionFondoView) ConfirmarSuscripcionFondoPresenter.this.getBaseView()).getAnalyticsManager().trackEvent(((ConfirmarSuscripcionFondoView) ConfirmarSuscripcionFondoPresenter.this.getBaseView()).getContext().getString(R.string.analytics_event_category_fondos), ((ConfirmarSuscripcionFondoView) ConfirmarSuscripcionFondoPresenter.this.getBaseView()).getContext().getString(R.string.analytics_event_action_cancelar_suscripcion_nuevo_fondo), ((ConfirmarSuscripcionFondoView) ConfirmarSuscripcionFondoPresenter.this.getBaseView()).getContext().getString(R.string.analytics_event_label_cancelacion_suscripcion_nuevo_fondo));
                }
            }
        };
        newInstance.setDialogListener(r1);
        newInstance.show(((AppCompatActivity) getBaseView()).getSupportFragmentManager(), "Dialog");
    }

    public void showTyC(String str) {
        Intent intent = new Intent(getContext(), InfoActivity.class);
        intent.putExtra(InfoActivity.TITLE_TO_SHOW, getContext().getString(R.string.F24_50_LBL_TITLE_TERMINOS));
        intent.putExtra(InfoActivity.TEXT_TO_SHOW, str);
        intent.putExtra(InfoActivity.TYPE_INFO, InfoType.DEFAULT);
        getContext().startActivity(intent);
    }

    public void gotoComprobante(FondoBean fondoBean, CuentaFondosBean cuentaFondosBean, String str, Cuenta cuenta, SimularSuscripcionFondoBodyResponseBean simularSuscripcionFondoBodyResponseBean, String str2) {
        if (!this.f) {
            return;
        }
        if (simularSuscripcionFondoBodyResponseBean.getIdFondo().equalsIgnoreCase("00042") || simularSuscripcionFondoBodyResponseBean.getIdFondo().equalsIgnoreCase("00054") || simularSuscripcionFondoBodyResponseBean.getIdFondo().equalsIgnoreCase("00041") || fondoBean.getId().equalsIgnoreCase(TarjetasConstants.CODIGO_TARJETA_AMEX) || fondoBean.getId().equalsIgnoreCase("54") || fondoBean.getId().equalsIgnoreCase("41")) {
            this.f = false;
            showConfirmarLeyendaEspecial(fondoBean, cuentaFondosBean, str, cuenta, simularSuscripcionFondoBodyResponseBean, str2);
            return;
        }
        this.f = false;
        a(fondoBean, cuentaFondosBean, str, cuenta, simularSuscripcionFondoBodyResponseBean);
    }

    /* access modifiers changed from: private */
    public void a(FondoBean fondoBean, CuentaFondosBean cuentaFondosBean, String str, Cuenta cuenta, SimularSuscripcionFondoBodyResponseBean simularSuscripcionFondoBodyResponseBean) {
        this.a = fondoBean;
        this.b = cuentaFondosBean;
        String str2 = str;
        this.c = str2;
        this.d = cuenta;
        this.e = simularSuscripcionFondoBodyResponseBean;
        if (getBaseView() != null) {
            ((ConfirmarSuscripcionFondoView) getBaseView()).showProgressIndicator(VSimularSuscripcionFondo.nameService);
        }
        String str3 = "0";
        try {
            if (fondoBean.getMoneda().equalsIgnoreCase(Constants.SYMBOL_CURRENCY_DOLLAR_DESCRIPTION) || UtilCurrency.getSimbolCurrencyFromDescription(Html.fromHtml(fondoBean.getMoneda()).toString()).equalsIgnoreCase(Constants.SYMBOL_CURRENCY_DOLAR)) {
                str3 = "2";
            }
        } catch (Exception unused) {
        }
        String str4 = str3;
        String str5 = cuenta.getTipo().equalsIgnoreCase("02") ? str4.equals("0") ? "09" : "10" : cuenta.getTipo();
        this.mDataManager.suscribirFondo(cuentaFondosBean.getTipoCuenta(), cuentaFondosBean.getSucursalCuenta(), cuentaFondosBean.getNumero(), fondoBean.getId(), str2, str4, str5, cuenta.getNroSuc(), cuenta.getNumero(), simularSuscripcionFondoBodyResponseBean.getPorcentComision(), simularSuscripcionFondoBodyResponseBean.getCotizaCambio(), "0", "");
    }

    @Subscribe
    public void onSuscribirFondo(SuscribirFondoEvent suscribirFondoEvent) {
        ((ConfirmarSuscripcionFondoView) getBaseView()).dismissProgressIndicator();
        final SuscribirFondoEvent suscribirFondoEvent2 = suscribirFondoEvent;
        AnonymousClass2 r1 = new BaseWSResponseHandler(this, getContext(), TypeOption.TRANSACTIONAL_FINAL_VIEW, FragmentConstants.FONDOS_INVERSION, getBaseView(), true, true, (BaseActivity) getContext()) {
            final /* synthetic */ ConfirmarSuscripcionFondoPresenter b;

            {
                this.b = r10;
            }

            public void onOk() {
                SuscribirFondoBodyResponseBean suscribirFondoBodyResponseBean = suscribirFondoEvent2.getResponseBean().getSuscribirFondoBodyResponseBean();
                if (!this.b.f) {
                    this.b.f = true;
                    if (suscribirFondoBodyResponseBean.getAlertaRiesgo().equalsIgnoreCase("0")) {
                        ((ConfirmarSuscripcionFondoView) this.b.getBaseView()).showConfirmDialog(suscribirFondoBodyResponseBean.getIdEvaluacionRiesgo());
                    } else if (suscribirFondoBodyResponseBean.getAlertaRiesgo().equalsIgnoreCase("1") || suscribirFondoBodyResponseBean.getAlertaRiesgo().equalsIgnoreCase("2")) {
                        this.b.showAlertRisk1TransferDialog(suscribirFondoBodyResponseBean.getMensaje(), suscribirFondoBodyResponseBean.getAlertaRiesgo(), suscribirFondoBodyResponseBean.getIdEvaluacionRiesgo());
                    }
                } else {
                    this.b.onSuscribirFondoResultOk(suscribirFondoBodyResponseBean);
                }
            }

            public void onRes8Error(WebServiceEvent webServiceEvent) {
                this.b.f = true;
                ((ConfirmarSuscripcionFondoView) this.b.getBaseView()).gotoFueraHorarioFondo(this.mErrorEvent.getMessageToShow());
            }
        };
        r1.handleWSResponse(suscribirFondoEvent);
    }

    public void onSuscribirFondoResultOk(SuscribirFondoBodyResponseBean suscribirFondoBodyResponseBean) {
        try {
            ((ConfirmarSuscripcionFondoView) getBaseView()).gotoComprobante(suscribirFondoBodyResponseBean);
        } catch (Exception e2) {
            Log.e(WSErrorHandlerConstants.LOG_TAG, "onSuscribirFondoResultOk: ", e2);
            e2.fillInStackTrace();
        }
    }

    public void showAlertRisk1TransferDialog(AlertaEvaluacionRiesgoBean alertaEvaluacionRiesgoBean, String str, String str2) {
        ((ConfirmarSuscripcionFondoView) getBaseView()).gotoEvaluacionRiesgoSuscripcionFondo(alertaEvaluacionRiesgoBean, str, str2);
    }
}
