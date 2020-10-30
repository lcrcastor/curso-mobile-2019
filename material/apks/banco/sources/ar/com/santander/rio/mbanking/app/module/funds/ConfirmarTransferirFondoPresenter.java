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
import ar.com.santander.rio.mbanking.app.ui.constants.WSErrorHandlerConstants;
import ar.com.santander.rio.mbanking.app.ui.fragments.FragmentConstants;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListener;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.services.events.TransferirFondoEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeOption;
import ar.com.santander.rio.mbanking.services.soap.beans.TransferirFondoResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.AlertaEvaluacionRiesgoBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CuentaFondosBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.SimularTransferenciaFondoBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.TransferirFondoBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.versions.VTransferirFondo;
import ar.com.santander.rio.mbanking.utils.UtilCurrency;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

public class ConfirmarTransferirFondoPresenter extends BasePresenter<ConfirmarTransferirFondoView> {
    /* access modifiers changed from: private */
    public SimularTransferenciaFondoBodyResponseBean a;
    /* access modifiers changed from: private */
    public CuentaFondosBean b;
    /* access modifiers changed from: private */
    public boolean c = false;
    /* access modifiers changed from: private */
    public String d;

    public ConfirmarTransferirFondoPresenter(Bus bus, IDataManager iDataManager) {
        super(bus, iDataManager);
    }

    public void onCreatePage(SimularTransferenciaFondoBodyResponseBean simularTransferenciaFondoBodyResponseBean) {
        ((ConfirmarTransferirFondoView) getBaseView()).setConfirmarTransferirFondoView(simularTransferenciaFondoBodyResponseBean);
    }

    public Context getContext() {
        return ((ConfirmarTransferirFondoView) getBaseView()).getContext();
    }

    private void a(final SimularTransferenciaFondoBodyResponseBean simularTransferenciaFondoBodyResponseBean, final CuentaFondosBean cuentaFondosBean, final String str) {
        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(getContext().getString(R.string.F24_41_TITLE_DIALOG_AVISO), simularTransferenciaFondoBodyResponseBean.getLeyendaEspecial(), null, null, getContext().getString(R.string.ID1_ALERT_BTN_CONTINUE), getContext().getString(R.string.IDX_ALERT_BTN_CANCEL), null);
        newInstance.setDialogListener(new IDialogListener() {
            public void onItemSelected(String str) {
            }

            public void onSimpleActionButton() {
            }

            public void onPositiveButton() {
                ConfirmarTransferirFondoPresenter.this.a(simularTransferenciaFondoBodyResponseBean, cuentaFondosBean, "0", "", "0");
            }

            public void onNegativeButton() {
                if (str.equalsIgnoreCase(ConfirmarTransferirFondoPresenter.this.getContext().getString(R.string.F24_30_SELECTED_AMOUNT_TOTAL))) {
                    ((ConfirmarTransferirFondoView) ConfirmarTransferirFondoPresenter.this.getBaseView()).getAnalyticsManager().trackEvent(((ConfirmarTransferirFondoView) ConfirmarTransferirFondoPresenter.this.getBaseView()).getContext().getString(R.string.analytics_event_category_fondos), ((ConfirmarTransferirFondoView) ConfirmarTransferirFondoPresenter.this.getBaseView()).getContext().getString(R.string.analytics_event_action_cancelar_transferencia), ((ConfirmarTransferirFondoView) ConfirmarTransferirFondoPresenter.this.getBaseView()).getContext().getString(R.string.analytics_event_label_importe_total));
                } else {
                    ((ConfirmarTransferirFondoView) ConfirmarTransferirFondoPresenter.this.getBaseView()).getAnalyticsManager().trackEvent(((ConfirmarTransferirFondoView) ConfirmarTransferirFondoPresenter.this.getBaseView()).getContext().getString(R.string.analytics_event_category_fondos), ((ConfirmarTransferirFondoView) ConfirmarTransferirFondoPresenter.this.getBaseView()).getContext().getString(R.string.analytics_event_action_cancelar_transferencia), ((ConfirmarTransferirFondoView) ConfirmarTransferirFondoPresenter.this.getBaseView()).getContext().getString(R.string.analytics_event_label_cancelar_transferencia_otro_importe));
                }
            }
        });
        newInstance.show(((AppCompatActivity) getBaseView()).getSupportFragmentManager(), "Dialog");
    }

    /* access modifiers changed from: private */
    public void a(final String str) {
        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(getContext().getString(R.string.F24_31_TITLE_CONFIRM_DIALOG), getContext().getString(R.string.F24_31_BODY_CONFIRM_DIALOG), null, null, getContext().getString(R.string.IDX_ALERT_BTN_YES), getContext().getString(R.string.IDX_ALERT_BTN_NO), null);
        newInstance.setDialogListener(new IDialogListener() {
            public void onItemSelected(String str) {
            }

            public void onSimpleActionButton() {
            }

            public void onPositiveButton() {
                ConfirmarTransferirFondoPresenter.this.c = true;
                ConfirmarTransferirFondoPresenter.this.a(ConfirmarTransferirFondoPresenter.this.a, ConfirmarTransferirFondoPresenter.this.b, "0", str, "1");
            }

            public void onNegativeButton() {
                if (ConfirmarTransferirFondoPresenter.this.d.equalsIgnoreCase(ConfirmarTransferirFondoPresenter.this.getContext().getString(R.string.F24_30_SELECTED_AMOUNT_TOTAL))) {
                    ((ConfirmarTransferirFondoView) ConfirmarTransferirFondoPresenter.this.getBaseView()).getAnalyticsManager().trackEvent(((ConfirmarTransferirFondoView) ConfirmarTransferirFondoPresenter.this.getBaseView()).getContext().getString(R.string.analytics_event_category_fondos), ((ConfirmarTransferirFondoView) ConfirmarTransferirFondoPresenter.this.getBaseView()).getContext().getString(R.string.analytics_event_action_cancelar_transferencia), ((ConfirmarTransferirFondoView) ConfirmarTransferirFondoPresenter.this.getBaseView()).getContext().getString(R.string.analytics_event_label_importe_total));
                } else {
                    ((ConfirmarTransferirFondoView) ConfirmarTransferirFondoPresenter.this.getBaseView()).getAnalyticsManager().trackEvent(((ConfirmarTransferirFondoView) ConfirmarTransferirFondoPresenter.this.getBaseView()).getContext().getString(R.string.analytics_event_category_fondos), ((ConfirmarTransferirFondoView) ConfirmarTransferirFondoPresenter.this.getBaseView()).getContext().getString(R.string.analytics_event_action_cancelar_transferencia), ((ConfirmarTransferirFondoView) ConfirmarTransferirFondoPresenter.this.getBaseView()).getContext().getString(R.string.analytics_event_label_cancelar_transferencia_otro_importe));
                }
            }
        });
        newInstance.show(((AppCompatActivity) getBaseView()).getSupportFragmentManager(), "Dialog");
    }

    /* access modifiers changed from: private */
    public void a(SimularTransferenciaFondoBodyResponseBean simularTransferenciaFondoBodyResponseBean, CuentaFondosBean cuentaFondosBean, String str, String str2, String str3) {
        String str4;
        ConfirmarTransferirFondoPresenter confirmarTransferirFondoPresenter;
        ((ConfirmarTransferirFondoView) getBaseView()).showProgressIndicator(VTransferirFondo.nameService);
        String str5 = "0";
        if (simularTransferenciaFondoBodyResponseBean.getMonedaDestino().equalsIgnoreCase("2") || UtilCurrency.getSimbolCurrencyFromDescription(Html.fromHtml(simularTransferenciaFondoBodyResponseBean.getMoneda()).toString()).equalsIgnoreCase(Constants.SYMBOL_CURRENCY_DOLAR)) {
            str5 = "2";
        }
        String str6 = str5;
        String str7 = str3;
        if (str7.equalsIgnoreCase("1")) {
            confirmarTransferirFondoPresenter = this;
            str4 = str7;
        } else {
            confirmarTransferirFondoPresenter = this;
            str4 = str;
        }
        confirmarTransferirFondoPresenter.mDataManager.transferirFondo(simularTransferenciaFondoBodyResponseBean.getImporteTransferir(), str6, simularTransferenciaFondoBodyResponseBean.getIdFondoOrigen(), simularTransferenciaFondoBodyResponseBean.getIdFondoDestino(), cuentaFondosBean.getTipoCuenta(), cuentaFondosBean.getSucursalCuenta(), cuentaFondosBean.getNumero(), str4, str2, simularTransferenciaFondoBodyResponseBean.getComisionOrigen(), simularTransferenciaFondoBodyResponseBean.getComisionDestino(), "", str7);
    }

    @Subscribe
    public void transferirFondo(TransferirFondoEvent transferirFondoEvent) {
        ((ConfirmarTransferirFondoView) getBaseView()).dismissProgressIndicator();
        final TransferirFondoEvent transferirFondoEvent2 = transferirFondoEvent;
        AnonymousClass3 r1 = new BaseWSResponseHandler(getContext(), TypeOption.TRANSACTIONAL_FINAL_VIEW, FragmentConstants.FONDOS_INVERSION, getBaseView(), (BaseActivity) getContext()) {
            public void onOk() {
                TransferirFondoBodyResponseBean transferirFondoBodyResponseBean = ((TransferirFondoResponseBean) transferirFondoEvent2.getBeanResponse()).getTransferirFondoBodyResponseBean();
                if (ConfirmarTransferirFondoPresenter.this.c) {
                    ConfirmarTransferirFondoPresenter.this.a(transferirFondoBodyResponseBean);
                } else if (transferirFondoBodyResponseBean.getAlertaRiesgo().equalsIgnoreCase("0")) {
                    ConfirmarTransferirFondoPresenter.this.a(transferirFondoBodyResponseBean.getIdEvaluacionRiesgo());
                } else if (transferirFondoBodyResponseBean.getAlertaRiesgo().equalsIgnoreCase("1")) {
                    ConfirmarTransferirFondoPresenter.this.a(transferirFondoBodyResponseBean.getMensaje(), transferirFondoBodyResponseBean.getAlertaRiesgo(), transferirFondoBodyResponseBean.getIdEvaluacionRiesgo());
                }
            }

            public void onRes8Error(WebServiceEvent webServiceEvent) {
                ((ConfirmarTransferirFondoView) ConfirmarTransferirFondoPresenter.this.getBaseView()).gotoFueraHorarioFondo(this.mErrorEvent.getMessageToShow());
            }
        };
        r1.handleWSResponse(transferirFondoEvent);
    }

    /* access modifiers changed from: private */
    public void a(TransferirFondoBodyResponseBean transferirFondoBodyResponseBean) {
        try {
            ((ConfirmarTransferirFondoView) getBaseView()).gotoComprobante(transferirFondoBodyResponseBean);
        } catch (Exception e) {
            Log.e(WSErrorHandlerConstants.LOG_TAG, "transferirFondoResultOk: ", e);
            e.fillInStackTrace();
        }
    }

    /* access modifiers changed from: private */
    public void a(AlertaEvaluacionRiesgoBean alertaEvaluacionRiesgoBean, String str, String str2) {
        ((ConfirmarTransferirFondoView) getBaseView()).gotoEvaluacionRiesgoTransferirFondo(alertaEvaluacionRiesgoBean, str, str2);
    }

    public void showTyC(String str) {
        Intent intent = new Intent(getContext(), InfoActivity.class);
        intent.putExtra(InfoActivity.TITLE_TO_SHOW, getContext().getString(R.string.F24_30_LBL_TITLE_TERMINOS));
        intent.putExtra(InfoActivity.TEXT_TO_SHOW, str);
        intent.putExtra(InfoActivity.TYPE_INFO, InfoType.DEFAULT);
        getContext().startActivity(intent);
    }

    public void gotoComprobante(SimularTransferenciaFondoBodyResponseBean simularTransferenciaFondoBodyResponseBean, CuentaFondosBean cuentaFondosBean, String str) {
        this.a = simularTransferenciaFondoBodyResponseBean;
        this.b = cuentaFondosBean;
        this.d = str;
        if (simularTransferenciaFondoBodyResponseBean.getIdFondoDestino().equalsIgnoreCase("00042") || simularTransferenciaFondoBodyResponseBean.getIdFondoDestino().equalsIgnoreCase("00054") || simularTransferenciaFondoBodyResponseBean.getIdFondoDestino().equalsIgnoreCase("00041")) {
            a(simularTransferenciaFondoBodyResponseBean, cuentaFondosBean, str);
            return;
        }
        a(simularTransferenciaFondoBodyResponseBean, cuentaFondosBean, "0", "", "0");
    }
}
