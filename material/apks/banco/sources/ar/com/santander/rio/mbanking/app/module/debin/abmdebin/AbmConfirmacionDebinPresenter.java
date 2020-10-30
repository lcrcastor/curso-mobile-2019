package ar.com.santander.rio.mbanking.app.module.debin.abmdebin;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.base.BasePresenter;
import ar.com.santander.rio.mbanking.app.base.BaseWSResponseHandler;
import ar.com.santander.rio.mbanking.app.ui.fragments.FragmentConstants;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListener;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.services.events.AbmDebinCompradorEvent;
import ar.com.santander.rio.mbanking.services.events.AbmDebinVendedorEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeOption;
import ar.com.santander.rio.mbanking.services.soap.beans.body.AbmDebinCompradorBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.AbmDebinVendedorBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CompradorBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CuentaCompradorBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.DetalleDebinBean;
import ar.com.santander.rio.mbanking.services.soap.versions.VGetAbmDebinVendedor;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

public class AbmConfirmacionDebinPresenter extends BasePresenter<AbmConfirmacionDebinView> {
    /* access modifiers changed from: private */
    public Context a;
    private String b;
    private DetalleDebinBean c;
    private String d;

    public AbmConfirmacionDebinPresenter(Bus bus, IDataManager iDataManager, Context context) {
        super(bus, iDataManager);
        this.a = context;
    }

    public void onCreatePage(DetalleDebinBean detalleDebinBean) {
        ((AbmConfirmacionDebinView) getBaseView()).setConfirmarDebinView(detalleDebinBean);
    }

    private DetalleDebinBean a(DetalleDebinBean detalleDebinBean) {
        if (detalleDebinBean == null) {
            return new DetalleDebinBean();
        }
        DetalleDebinBean clone = detalleDebinBean.clone();
        clone.getCompradorBean().getCuentaCompradorBean().setSaldo(null);
        clone.getVendedorBean().getCuentaVendedor().setStatusAdhesion(null);
        clone.setFechaVencimiento(null);
        clone.setFechaCreacion(null);
        clone.setCodEstado(null);
        clone.setMostrarAceptarRechazar(null);
        clone.setMostrarDesconocimiento(null);
        return clone;
    }

    public void callAbmDebinVendedor(final String str, final String str2, DetalleDebinBean detalleDebinBean) {
        String str3;
        String str4;
        String str5;
        String string;
        final DetalleDebinBean a2 = a(detalleDebinBean);
        if (str.equalsIgnoreCase("A")) {
            str5 = "Confirmar";
            string = this.a.getString(R.string.MSG_USER00515_DEBIN);
        } else if (str.equalsIgnoreCase("P")) {
            str5 = "Confirmar";
            string = this.a.getString(R.string.MSG_USER00512_DEBIN);
        } else if (str.equalsIgnoreCase("D")) {
            String string2 = this.a.getString(R.string.MSG_USER00529_DEBIN);
            this.b = str;
            this.c = detalleDebinBean;
            this.d = str2;
            str3 = string2;
            str4 = "Confirmar";
            IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(str4, str3, null, null, this.a.getString(R.string.IDX_ALERT_BTN_YES), this.a.getString(R.string.IDX_ALERT_BTN_NO), null);
            newInstance.setDialogListener(new IDialogListener() {
                public void onItemSelected(String str) {
                }

                public void onSimpleActionButton() {
                }

                public void onPositiveButton() {
                    ((AbmConfirmacionDebinView) AbmConfirmacionDebinPresenter.this.getBaseView()).showProgressIndicator(VGetAbmDebinVendedor.nameService);
                    if (str.equalsIgnoreCase("A")) {
                        AbmConfirmacionDebinPresenter.this.mDataManager.abmDebinVendedor(str, null, str2, (BaseActivity) AbmConfirmacionDebinPresenter.this.a, false);
                    } else if (str.equalsIgnoreCase("P")) {
                        AbmConfirmacionDebinPresenter.this.mDataManager.abmDebinComprador(str, a2, (BaseActivity) AbmConfirmacionDebinPresenter.this.a, true);
                    } else if (str.equalsIgnoreCase("R")) {
                        AbmConfirmacionDebinPresenter.this.mDataManager.abmDebinComprador(str, a2, (BaseActivity) AbmConfirmacionDebinPresenter.this.a, false);
                    } else if (str.equalsIgnoreCase("D")) {
                        AbmConfirmacionDebinPresenter.this.mDataManager.abmDebinComprador(str, a2, (BaseActivity) AbmConfirmacionDebinPresenter.this.a, false);
                    }
                }

                public void onNegativeButton() {
                    if (str.equalsIgnoreCase("A")) {
                        ((AbmConfirmacionDebinView) AbmConfirmacionDebinPresenter.this.getBaseView()).getAnalyticsManager().trackEvent(AbmConfirmacionDebinPresenter.this.a.getString(R.string.analytics_trackevent_category_debin), AbmConfirmacionDebinPresenter.this.a.getString(R.string.analytics_trackevent_action_cancelar), AbmConfirmacionDebinPresenter.this.a.getString(R.string.f224analytics_trackevent_label_Cancelar_confirmacin_debin));
                    } else if (str.equalsIgnoreCase("P")) {
                        ((AbmConfirmacionDebinView) AbmConfirmacionDebinPresenter.this.getBaseView()).getAnalyticsManager().trackEvent(AbmConfirmacionDebinPresenter.this.a.getString(R.string.analytics_trackevent_category_debin), AbmConfirmacionDebinPresenter.this.a.getString(R.string.analytics_trackevent_action_cancelar), AbmConfirmacionDebinPresenter.this.a.getString(R.string.analytics_trackevent_label_Cancelar_pago_debin));
                    } else {
                        ((AbmConfirmacionDebinView) AbmConfirmacionDebinPresenter.this.getBaseView()).getAnalyticsManager().trackEvent(AbmConfirmacionDebinPresenter.this.a.getString(R.string.analytics_trackevent_category_debin), AbmConfirmacionDebinPresenter.this.a.getString(R.string.analytics_trackevent_action_cancelar), AbmConfirmacionDebinPresenter.this.a.getString(R.string.analytics_trackevent_label_Cancelar_rechazo_debin));
                    }
                }
            });
            newInstance.show(((AppCompatActivity) getBaseView()).getSupportFragmentManager(), "Dialog");
        } else {
            str5 = "Confirmar";
            string = this.a.getString(R.string.MSG_USER00513_DEBIN);
        }
        str4 = str5;
        str3 = string;
        IsbanDialogFragment newInstance2 = IsbanDialogFragment.newInstance(str4, str3, null, null, this.a.getString(R.string.IDX_ALERT_BTN_YES), this.a.getString(R.string.IDX_ALERT_BTN_NO), null);
        newInstance2.setDialogListener(new IDialogListener() {
            public void onItemSelected(String str) {
            }

            public void onSimpleActionButton() {
            }

            public void onPositiveButton() {
                ((AbmConfirmacionDebinView) AbmConfirmacionDebinPresenter.this.getBaseView()).showProgressIndicator(VGetAbmDebinVendedor.nameService);
                if (str.equalsIgnoreCase("A")) {
                    AbmConfirmacionDebinPresenter.this.mDataManager.abmDebinVendedor(str, null, str2, (BaseActivity) AbmConfirmacionDebinPresenter.this.a, false);
                } else if (str.equalsIgnoreCase("P")) {
                    AbmConfirmacionDebinPresenter.this.mDataManager.abmDebinComprador(str, a2, (BaseActivity) AbmConfirmacionDebinPresenter.this.a, true);
                } else if (str.equalsIgnoreCase("R")) {
                    AbmConfirmacionDebinPresenter.this.mDataManager.abmDebinComprador(str, a2, (BaseActivity) AbmConfirmacionDebinPresenter.this.a, false);
                } else if (str.equalsIgnoreCase("D")) {
                    AbmConfirmacionDebinPresenter.this.mDataManager.abmDebinComprador(str, a2, (BaseActivity) AbmConfirmacionDebinPresenter.this.a, false);
                }
            }

            public void onNegativeButton() {
                if (str.equalsIgnoreCase("A")) {
                    ((AbmConfirmacionDebinView) AbmConfirmacionDebinPresenter.this.getBaseView()).getAnalyticsManager().trackEvent(AbmConfirmacionDebinPresenter.this.a.getString(R.string.analytics_trackevent_category_debin), AbmConfirmacionDebinPresenter.this.a.getString(R.string.analytics_trackevent_action_cancelar), AbmConfirmacionDebinPresenter.this.a.getString(R.string.f224analytics_trackevent_label_Cancelar_confirmacin_debin));
                } else if (str.equalsIgnoreCase("P")) {
                    ((AbmConfirmacionDebinView) AbmConfirmacionDebinPresenter.this.getBaseView()).getAnalyticsManager().trackEvent(AbmConfirmacionDebinPresenter.this.a.getString(R.string.analytics_trackevent_category_debin), AbmConfirmacionDebinPresenter.this.a.getString(R.string.analytics_trackevent_action_cancelar), AbmConfirmacionDebinPresenter.this.a.getString(R.string.analytics_trackevent_label_Cancelar_pago_debin));
                } else {
                    ((AbmConfirmacionDebinView) AbmConfirmacionDebinPresenter.this.getBaseView()).getAnalyticsManager().trackEvent(AbmConfirmacionDebinPresenter.this.a.getString(R.string.analytics_trackevent_category_debin), AbmConfirmacionDebinPresenter.this.a.getString(R.string.analytics_trackevent_action_cancelar), AbmConfirmacionDebinPresenter.this.a.getString(R.string.analytics_trackevent_label_Cancelar_rechazo_debin));
                }
            }
        });
        newInstance2.show(((AppCompatActivity) getBaseView()).getSupportFragmentManager(), "Dialog");
    }

    @Subscribe
    public void onAbmDebinVendedor(AbmDebinVendedorEvent abmDebinVendedorEvent) {
        ((AbmConfirmacionDebinView) getBaseView()).dismissProgressIndicator();
        final AbmDebinVendedorEvent abmDebinVendedorEvent2 = abmDebinVendedorEvent;
        AnonymousClass2 r1 = new BaseWSResponseHandler(this.a, TypeOption.NO_TRANSACTIONAL_FINAL_VIEW, FragmentConstants.DEBIN, getBaseView(), (BaseActivity) this.a) {
            /* access modifiers changed from: protected */
            public void onOk() {
                AbmConfirmacionDebinPresenter.this.a(abmDebinVendedorEvent2);
            }
        };
        r1.handleWSResponse(abmDebinVendedorEvent);
    }

    /* access modifiers changed from: private */
    public void a(AbmDebinVendedorEvent abmDebinVendedorEvent) {
        try {
            ((AbmConfirmacionDebinView) getBaseView()).gotoComprobanteAbmDebin(abmDebinVendedorEvent.getResponse().getAbmDebinVendedorBodyResponseBean(), new AbmDebinCompradorBodyResponseBean());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Subscribe
    public void onAbmDebinComprador(AbmDebinCompradorEvent abmDebinCompradorEvent) {
        ((AbmConfirmacionDebinView) getBaseView()).dismissProgressIndicator();
        final AbmDebinCompradorEvent abmDebinCompradorEvent2 = abmDebinCompradorEvent;
        AnonymousClass3 r1 = new BaseWSResponseHandler(this.a, TypeOption.NO_TRANSACTIONAL_FINAL_VIEW, FragmentConstants.DEBIN, getBaseView(), (BaseActivity) this.a) {
            /* access modifiers changed from: protected */
            public void onOk() {
                AbmConfirmacionDebinPresenter.this.a(abmDebinCompradorEvent2);
            }
        };
        r1.handleWSResponse(abmDebinCompradorEvent);
    }

    /* access modifiers changed from: private */
    public void a(AbmDebinCompradorEvent abmDebinCompradorEvent) {
        try {
            AbmDebinCompradorBodyResponseBean abmDebinCompradorBodyResponseBean = abmDebinCompradorEvent.getResponse().getAbmDebinCompradorBodyResponseBean();
            if (abmDebinCompradorBodyResponseBean.getCtaMigradaBean() != null) {
                showDialogConfirmationAbmCtaMigrada((AppCompatActivity) this.a, abmDebinCompradorBodyResponseBean);
            } else {
                ((AbmConfirmacionDebinView) getBaseView()).gotoComprobanteAbmDebin(new AbmDebinVendedorBodyResponseBean(), abmDebinCompradorBodyResponseBean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* access modifiers changed from: private */
    public void a(AbmDebinCompradorBodyResponseBean abmDebinCompradorBodyResponseBean) {
        CuentaCompradorBean cuentaCompradorBean = this.c.getCompradorBean().getCuentaCompradorBean();
        cuentaCompradorBean.updateCuentaMigradas(abmDebinCompradorBodyResponseBean.getCtaMigradaBean());
        CompradorBean compradorBean = this.c.getCompradorBean();
        compradorBean.setCuentaCompradorBean(cuentaCompradorBean);
        this.c.setCompradorBean(compradorBean);
        ((AbmConfirmacionDebinView) getBaseView()).setConfirmarDebinView(this.c);
        this.mDataManager.abmDebinComprador(this.b, abmDebinCompradorBodyResponseBean.getCtaMigradaBean().getEstadoMigrado(), a(this.c), (BaseActivity) this.a, false);
    }

    public void showDialogConfirmationAbmCtaMigrada(AppCompatActivity appCompatActivity, final AbmDebinCompradorBodyResponseBean abmDebinCompradorBodyResponseBean) {
        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance("DIALOGCONFIRMATIONABMGETCTAMIGRADA", appCompatActivity.getString(R.string.IDX_ALERT_LBL_TITLE_ADVISE), abmDebinCompradorBodyResponseBean.getCtaMigradaBean().getMensajeCtaMigrada(), null, null, appCompatActivity.getString(R.string.BTN_POSITIVE_CTA_MIGRADA), appCompatActivity.getString(R.string.BTN_NEGATIVE_CTA_MIGRADA), null, null);
        newInstance.setDialogListener(new IDialogListener() {
            public void onItemSelected(String str) {
            }

            public void onNegativeButton() {
            }

            public void onSimpleActionButton() {
            }

            public void onPositiveButton() {
                AbmConfirmacionDebinPresenter.this.a(abmDebinCompradorBodyResponseBean);
            }
        });
        newInstance.show(appCompatActivity.getSupportFragmentManager(), "DIALOGCONFIRMATIONABMGETCTAMIGRADA");
    }
}
