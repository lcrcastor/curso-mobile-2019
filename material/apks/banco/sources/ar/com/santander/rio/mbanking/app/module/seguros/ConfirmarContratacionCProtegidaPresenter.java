package ar.com.santander.rio.mbanking.app.module.seguros;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.base.BasePresenter;
import ar.com.santander.rio.mbanking.app.base.BaseWSResponseHandler;
import ar.com.santander.rio.mbanking.app.ui.activities.InfoActivity;
import ar.com.santander.rio.mbanking.app.ui.activities.InfoActivity.InfoType;
import ar.com.santander.rio.mbanking.app.ui.constants.PagoTarjetasConstants;
import ar.com.santander.rio.mbanking.app.ui.constants.WSErrorHandlerConstants;
import ar.com.santander.rio.mbanking.app.ui.fragments.FragmentConstants;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListener;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.services.events.ContratarCompraProtegidaEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeOption;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeResult;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CuentaShortBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.TarjetaBean;
import ar.com.santander.rio.mbanking.services.soap.versions.VContratarCompraProtegida;
import com.crashlytics.android.Crashlytics;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import java.util.ArrayList;
import java.util.List;

public class ConfirmarContratacionCProtegidaPresenter extends BasePresenter<ConfirmarContratacionCProtegidaView> {
    private Context a;

    public ConfirmarContratacionCProtegidaPresenter(Bus bus, IDataManager iDataManager, Context context) {
        super(bus, iDataManager);
        this.a = context;
    }

    public Context getContext() {
        return ((ConfirmarContratacionCProtegidaView) getBaseView()).getContext();
    }

    public void onCreatePage() {
        ((ConfirmarContratacionCProtegidaView) getBaseView()).setConfirmarContratacionView();
    }

    public void showDetalle(String str, String str2) {
        Intent intent = new Intent(getContext(), InfoActivity.class);
        intent.putExtra(InfoActivity.TITLE_TO_SHOW, str);
        intent.putExtra(InfoActivity.TEXT_TO_SHOW, str2);
        intent.putExtra(InfoActivity.TYPE_INFO, InfoType.DEFAULT);
        intent.putExtra(InfoActivity.ICON_TO_SHOW, R.drawable.ic_close);
        getContext().startActivity(intent);
    }

    public void showConfirmarDialog(String str, String str2, String str3, String str4, List<TarjetaBean> list, CuentaShortBean cuentaShortBean, TarjetaBean tarjetaBean) {
        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(this.a.getString(R.string.F27_13_TITLE_DIALOG_CONFIRMAR), this.a.getString(R.string.F27_13_BODY_CONFIRM_DIALOG), null, null, this.a.getString(R.string.IDX_ALERT_BTN_YES), this.a.getString(R.string.IDX_ALERT_BTN_NO), null);
        final IsbanDialogFragment isbanDialogFragment = newInstance;
        final List<TarjetaBean> list2 = list;
        final String str5 = str;
        final String str6 = str2;
        final String str7 = str3;
        final String str8 = str4;
        final CuentaShortBean cuentaShortBean2 = cuentaShortBean;
        final TarjetaBean tarjetaBean2 = tarjetaBean;
        AnonymousClass1 r0 = new IDialogListener() {
            public void onItemSelected(String str) {
            }

            public void onSimpleActionButton() {
            }

            public void onPositiveButton() {
                isbanDialogFragment.dismiss();
                ((ConfirmarContratacionCProtegidaView) ConfirmarContratacionCProtegidaPresenter.this.getBaseView()).showProgressIndicator(VContratarCompraProtegida.nameService);
                ArrayList arrayList = new ArrayList();
                for (TarjetaBean tarjetaBean : list2) {
                    arrayList.add(new TarjetaBean(tarjetaBean.getTipo(), tarjetaBean.getNumTarjeta(), tarjetaBean.getNumCuenta()));
                }
                ConfirmarContratacionCProtegidaPresenter.this.mDataManager.contratarCompraProtegida(str5, str6, str7, str8, arrayList, cuentaShortBean2, tarjetaBean2);
            }

            public void onNegativeButton() {
                ((ConfirmarContratacionCProtegidaView) ConfirmarContratacionCProtegidaPresenter.this.getBaseView()).getAnalyticsManager().trackEvent(((ConfirmarContratacionCProtegidaView) ConfirmarContratacionCProtegidaPresenter.this.getBaseView()).getContext().getString(R.string.analytics_trackevent_category_seguros), ((ConfirmarContratacionCProtegidaView) ConfirmarContratacionCProtegidaPresenter.this.getBaseView()).getContext().getString(R.string.analytics_trackevent_action_cancelar_contratacion), ((ConfirmarContratacionCProtegidaView) ConfirmarContratacionCProtegidaPresenter.this.getBaseView()).getContext().getString(R.string.analytics_trackevent_label_compra_protegida));
            }
        };
        newInstance.setDialogListener(r0);
        newInstance.show(((AppCompatActivity) getBaseView()).getSupportFragmentManager(), "isbanDialogConfirm");
    }

    @Subscribe
    public void onContratarCompraProtegida(ContratarCompraProtegidaEvent contratarCompraProtegidaEvent) {
        ((ConfirmarContratacionCProtegidaView) getBaseView()).dismissProgressIndicator();
        if (contratarCompraProtegidaEvent.getResult() != TypeResult.SERVER_ERROR) {
            final ContratarCompraProtegidaEvent contratarCompraProtegidaEvent2 = contratarCompraProtegidaEvent;
            AnonymousClass2 r2 = new BaseWSResponseHandler(this.a, TypeOption.TRANSACTIONAL_FINAL_VIEW, FragmentConstants.SEGUROS, getBaseView(), (BaseActivity) this.a) {
                /* access modifiers changed from: protected */
                public void onOk() {
                    ConfirmarContratacionCProtegidaPresenter.this.onGetContratarCompraProtegidaOK(contratarCompraProtegidaEvent2);
                }
            };
            r2.handleWSResponse(contratarCompraProtegidaEvent);
            return;
        }
        a(contratarCompraProtegidaEvent);
    }

    private void a(final ContratarCompraProtegidaEvent contratarCompraProtegidaEvent) {
        String str = "Por favor, verificá en 5 (cinco) minutos si la contratación pudo ser correctamente cursada, desde la tenencia de tus Seguros.";
        try {
            final IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(PagoTarjetasConstants.ISBAN_DIALOG_ERROR_TITLE, str, null, this.a.getString(R.string.ID1_ALERT_BTN_ACCEPT), null, null, null);
            newInstance.setDialogListener(new IDialogListener() {
                public void onItemSelected(String str) {
                }

                public void onNegativeButton() {
                }

                public void onPositiveButton() {
                }

                public void onSimpleActionButton() {
                    newInstance.dismiss();
                    ConfirmarContratacionCProtegidaPresenter.this.refrescoError(contratarCompraProtegidaEvent);
                }
            });
            newInstance.show(((AppCompatActivity) getBaseView()).getSupportFragmentManager(), "popUpErrorServer");
        } catch (Exception unused) {
        }
    }

    public void refrescoError(ContratarCompraProtegidaEvent contratarCompraProtegidaEvent) {
        ((ConfirmarContratacionCProtegidaView) getBaseView()).endActivity();
    }

    public void onGetContratarCompraProtegidaOK(ContratarCompraProtegidaEvent contratarCompraProtegidaEvent) {
        try {
            ((ConfirmarContratacionCProtegidaView) getBaseView()).gotoComprobanteContratacion(contratarCompraProtegidaEvent.getResponse().getContratarCompraProtegidaBodyResponseBean());
        } catch (Exception e) {
            Crashlytics.log(e.getMessage());
            Log.e(WSErrorHandlerConstants.LOG_TAG, "onGetContratarCompraProtegidaOK: ", e);
        }
    }
}
