package ar.com.santander.rio.mbanking.app.module.seguros;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.base.BasePresenter;
import ar.com.santander.rio.mbanking.app.base.BaseWSResponseHandler;
import ar.com.santander.rio.mbanking.app.ui.activities.InfoActivity;
import ar.com.santander.rio.mbanking.app.ui.activities.InfoActivity.InfoType;
import ar.com.santander.rio.mbanking.app.ui.constants.PagoTarjetasConstants;
import ar.com.santander.rio.mbanking.app.ui.fragments.FragmentConstants;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListener;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.services.events.ContratarSeguroMovilEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeOption;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeResult;
import ar.com.santander.rio.mbanking.services.soap.beans.body.BajaSeguroBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CuentaShortBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.TarjetaBean;
import ar.com.santander.rio.mbanking.services.soap.versions.VContratarSeguroMovil;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

public class ConfirmarContratacionPresenter extends BasePresenter<ConfirmarContratacionView> {
    private Context a;
    /* access modifiers changed from: private */
    public String b;

    public ConfirmarContratacionPresenter(Bus bus, IDataManager iDataManager, Context context) {
        super(bus, iDataManager);
        this.a = context;
    }

    public Context getContext() {
        return ((ConfirmarContratacionView) getBaseView()).getContext();
    }

    public void onCreatePage() {
        ((ConfirmarContratacionView) getBaseView()).setConfirmarContratacionView();
    }

    public void showTyC(String str) {
        Intent intent = new Intent(getContext(), InfoActivity.class);
        intent.putExtra(InfoActivity.TITLE_TO_SHOW, getContext().getString(R.string.ID_4091_SEGUROS_LBL_TYC));
        intent.putExtra(InfoActivity.TEXT_TO_SHOW, str);
        intent.putExtra(InfoActivity.TYPE_INFO, InfoType.DEFAULT);
        intent.putExtra(InfoActivity.ICON_TO_SHOW, R.drawable.ic_close);
        getContext().startActivity(intent);
    }

    public void showConfirmarDialog() {
        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(this.a.getString(R.string.F27_13_TITLE_DIALOG_CONFIRMAR), this.a.getString(R.string.F27_13_BODY_CONFIRM_DIALOG), null, null, this.a.getString(R.string.IDX_ALERT_BTN_YES), this.a.getString(R.string.IDX_ALERT_BTN_NO), null);
        newInstance.setDialogListener(new IDialogListener() {
            public void onItemSelected(String str) {
            }

            public void onNegativeButton() {
            }

            public void onSimpleActionButton() {
            }

            public void onPositiveButton() {
                ((ConfirmarContratacionView) ConfirmarContratacionPresenter.this.getBaseView()).gotoComprobanteContratacion(null);
            }
        });
        newInstance.show(((AppCompatActivity) getBaseView()).getSupportFragmentManager(), "isbanDialogConfirm");
    }

    public void showConfirmarMovilDialog(String str, String str2, String str3, String str4, String str5, String str6, String str7, BajaSeguroBean bajaSeguroBean, CuentaShortBean cuentaShortBean, TarjetaBean tarjetaBean) {
        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(this.a.getString(R.string.F27_13_TITLE_DIALOG_CONFIRMAR), this.a.getString(R.string.F27_34_BODY_CONFIRM_DIALOG), null, null, this.a.getString(R.string.IDX_ALERT_BTN_YES), this.a.getString(R.string.IDX_ALERT_BTN_NO), null);
        final IsbanDialogFragment isbanDialogFragment = newInstance;
        final String str8 = str;
        final String str9 = str2;
        final String str10 = str3;
        final String str11 = str4;
        final String str12 = str5;
        final String str13 = str6;
        final String str14 = str7;
        final BajaSeguroBean bajaSeguroBean2 = bajaSeguroBean;
        final CuentaShortBean cuentaShortBean2 = cuentaShortBean;
        final TarjetaBean tarjetaBean2 = tarjetaBean;
        AnonymousClass2 r0 = new IDialogListener() {
            public void onItemSelected(String str) {
            }

            public void onSimpleActionButton() {
            }

            public void onPositiveButton() {
                isbanDialogFragment.dismiss();
                ((ConfirmarContratacionView) ConfirmarContratacionPresenter.this.getBaseView()).showProgressIndicator(VContratarSeguroMovil.nameService);
                ConfirmarContratacionPresenter.this.mDataManager.contratarSeguroMovil(str8, str9, str10, str11, str12, str13, str14, bajaSeguroBean2, cuentaShortBean2, tarjetaBean2);
            }

            public void onNegativeButton() {
                try {
                    if (ConfirmarContratacionPresenter.this.b.equalsIgnoreCase("A")) {
                        ((ConfirmarContratacionView) ConfirmarContratacionPresenter.this.getBaseView()).getAnalyticsManager().trackEvent(((ConfirmarContratacionView) ConfirmarContratacionPresenter.this.getBaseView()).getContext().getString(R.string.analytics_trackevent_category_seguros), ((ConfirmarContratacionView) ConfirmarContratacionPresenter.this.getBaseView()).getContext().getString(R.string.analytics_trackevent_action_cancelar_contratacion), ((ConfirmarContratacionView) ConfirmarContratacionPresenter.this.getBaseView()).getContext().getString(R.string.analytics_trackevent_label_seguro_movil));
                    } else {
                        ((ConfirmarContratacionView) ConfirmarContratacionPresenter.this.getBaseView()).getAnalyticsManager().trackEvent(((ConfirmarContratacionView) ConfirmarContratacionPresenter.this.getBaseView()).getContext().getString(R.string.analytics_trackevent_category_seguros), ((ConfirmarContratacionView) ConfirmarContratacionPresenter.this.getBaseView()).getContext().getString(R.string.analytics_trackevent_action_cancelar_contratacion), ((ConfirmarContratacionView) ConfirmarContratacionPresenter.this.getBaseView()).getContext().getString(R.string.analytics_trackevent_label_nuevo_seguro_movil));
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        };
        newInstance.setDialogListener(r0);
        newInstance.show(((AppCompatActivity) getBaseView()).getSupportFragmentManager(), "isbanDialogConfirm");
    }

    @Subscribe
    public void onContratarSeguroMovil(ContratarSeguroMovilEvent contratarSeguroMovilEvent) {
        ((ConfirmarContratacionView) getBaseView()).dismissProgressIndicator();
        if (contratarSeguroMovilEvent.getResult() != TypeResult.SERVER_ERROR) {
            final ContratarSeguroMovilEvent contratarSeguroMovilEvent2 = contratarSeguroMovilEvent;
            AnonymousClass3 r2 = new BaseWSResponseHandler(this.a, TypeOption.TRANSACTIONAL_FINAL_VIEW, FragmentConstants.SEGUROS, getBaseView(), (BaseActivity) this.a) {
                /* access modifiers changed from: protected */
                public void onOk() {
                    ConfirmarContratacionPresenter.this.onGetContratarSeguroMovilOk(contratarSeguroMovilEvent2);
                }
            };
            r2.handleWSResponse(contratarSeguroMovilEvent);
            return;
        }
        a(contratarSeguroMovilEvent);
    }

    public void onGetContratarSeguroMovilOk(ContratarSeguroMovilEvent contratarSeguroMovilEvent) {
        ((ConfirmarContratacionView) getBaseView()).gotoComprobanteContratacion(contratarSeguroMovilEvent.getResponse().getContratarSeguroMovilBodyResponseBean());
    }

    private void a(final ContratarSeguroMovilEvent contratarSeguroMovilEvent) {
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
                    ConfirmarContratacionPresenter.this.refrescoError(contratarSeguroMovilEvent);
                }
            });
            newInstance.show(((AppCompatActivity) getBaseView()).getSupportFragmentManager(), "popUpErrorServer");
        } catch (Exception unused) {
        }
    }

    public void refrescoError(ContratarSeguroMovilEvent contratarSeguroMovilEvent) {
        ((ConfirmarContratacionView) getBaseView()).endActivity();
    }
}
