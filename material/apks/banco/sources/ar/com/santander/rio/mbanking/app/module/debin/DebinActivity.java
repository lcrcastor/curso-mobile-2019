package ar.com.santander.rio.mbanking.app.module.debin;

import android.app.Activity;
import android.content.Context;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.base.BasePresenter;
import ar.com.santander.rio.mbanking.app.base.BaseWSResponseHandler;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.app.ui.constants.DebinConstants;
import ar.com.santander.rio.mbanking.app.ui.constants.DebinConstants.ACTION_CONSULTA_ADHESION_VENDEDOR;
import ar.com.santander.rio.mbanking.app.ui.constants.DebinConstants.PRE_AUTORIZACIONES;
import ar.com.santander.rio.mbanking.app.ui.constants.PagoTarjetasConstants;
import ar.com.santander.rio.mbanking.app.ui.fragments.FragmentConstants;
import ar.com.santander.rio.mbanking.app.ui.fragments.privado.DetalleDebinFragment;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListener;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.managers.security.SoftTokenManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.services.events.ConsultarAdhesionVendedorEvent;
import ar.com.santander.rio.mbanking.services.events.GetDebinesEvent;
import ar.com.santander.rio.mbanking.services.events.GetDetalleDebinCompradorEvent;
import ar.com.santander.rio.mbanking.services.events.GetDetalleDebinVendedorEvent;
import ar.com.santander.rio.mbanking.services.events.GetFirmaCredinEvent;
import ar.com.santander.rio.mbanking.services.events.GetPreAutorizacionesEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeOption;
import ar.com.santander.rio.mbanking.services.soap.beans.GetFirmaCredinResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ConsultarAdhesionVendedorBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetDebinesBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetDetalleDebinCompradorBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetFirmaCredinBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetPreAutorizacionesBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ListDebinesBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ListGroupBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ListaCuentasVendedorBean;
import ar.com.santander.rio.mbanking.services.soap.versions.VGetConsultarAdhesionVendedor;
import ar.com.santander.rio.mbanking.services.soap.versions.VGetDebines;
import ar.com.santander.rio.mbanking.services.soap.versions.VGetDetalleDebinComprador;
import ar.com.santander.rio.mbanking.services.soap.versions.VGetDetalleDebinVendedor;
import ar.com.santander.rio.mbanking.services.soap.versions.VGetFirmaCredin;
import ar.com.santander.rio.mbanking.services.soap.versions.VGetPreAutorizaciones;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import java.util.ArrayList;
import java.util.List;

public class DebinActivity extends BasePresenter<DebinView> {
    private Activity a;
    /* access modifiers changed from: private */
    public Context b;
    /* access modifiers changed from: private */
    public SessionManager c;
    private String d = null;
    public DetalleDebinFragment detalleDebinFragment;
    /* access modifiers changed from: private */
    public boolean e = true;
    private boolean f = false;
    /* access modifiers changed from: private */
    public List<ListGroupBean> g;

    public DebinActivity(Bus bus, IDataManager iDataManager, Context context, SessionManager sessionManager, Activity activity) {
        super(bus, iDataManager);
        this.b = context;
        this.c = sessionManager;
        this.a = activity;
    }

    public void showDebinOptions(final SoftTokenManager softTokenManager) {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        arrayList.add(this.b.getString(R.string.ID_4410_DEBIN_ORDENDEBIN));
        arrayList.add(this.b.getString(R.string.ID_4634_DEBIN_BTN_MENU_PREAUTORIZACIONES));
        arrayList.add(this.b.getString(R.string.F32_00_4411));
        arrayList.add(this.b.getString(R.string.ID_4690_DEBIN_GENERAR_CREDIN));
        try {
            arrayList2.add(CAccessibility.getInstance(this.b).applyFilterGeneral(this.b.getString(R.string.ID_4455_DEBIN_GENERARDEBIN)));
            arrayList2.add(CAccessibility.getInstance(this.b).applyFilterDebinPreAutorizacion(this.b.getString(R.string.ID_4634_DEBIN_BTN_MENU_PREAUTORIZACIONES)));
            arrayList2.add(CAccessibility.getInstance(this.b).applyFilterGeneral(this.b.getString(R.string.F32_00_4411)));
            arrayList2.add(CAccessibility.getInstance(this.b).applyFilterGeneral(this.b.getString(R.string.ID_4690_DEBIN_GENERAR_CREDIN)));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        final IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance("popUpDebin", null, null, arrayList, this.b.getString(R.string.F32_00_4412), null, null, "", arrayList2);
        newInstance.setDialogListener(new IDialogListener() {
            public void onNegativeButton() {
            }

            public void onPositiveButton() {
            }

            public void onItemSelected(String str) {
                if (str.equalsIgnoreCase(DebinActivity.this.b.getString(R.string.ID_4410_DEBIN_ORDENDEBIN))) {
                    DebinActivity.this.consultarAdhesionVendedor(ACTION_CONSULTA_ADHESION_VENDEDOR.ADHERIR_NUEVO);
                } else if (str.equalsIgnoreCase(DebinActivity.this.b.getString(R.string.ID_4634_DEBIN_BTN_MENU_PREAUTORIZACIONES))) {
                    DebinActivity.this.g = DebinActivity.this.c.getTableByID(PRE_AUTORIZACIONES.ESTREPEAUT).getListGroupBeans();
                    DebinActivity.this.consultarPreAutorizaciones("C", ((ListGroupBean) DebinActivity.this.g.get(0)).getCode(), null, true);
                } else if (str.equalsIgnoreCase(DebinActivity.this.b.getString(R.string.F32_00_4411))) {
                    DebinActivity.this.consultarAdhesionVendedor(ACTION_CONSULTA_ADHESION_VENDEDOR.GESTION_ADHESION);
                } else if (!str.equalsIgnoreCase(DebinActivity.this.b.getString(R.string.ID_4690_DEBIN_GENERAR_CREDIN))) {
                } else {
                    if (softTokenManager.isSoftTokenAvailable().booleanValue()) {
                        DebinActivity.this.popUpSaliendoDeSantanderRio();
                    } else {
                        ((DebinView) DebinActivity.this.getBaseView()).mostrarPopUpSinToken();
                    }
                }
            }

            public void onSimpleActionButton() {
                newInstance.dismiss();
            }
        });
        newInstance.show(((DebinView) getBaseView()).getFragmentManager(), "popUpDebin");
    }

    public void popUpSaliendoDeSantanderRio() {
        final IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(DebinConstants.DIALOG_FIRMA_CREDIN, PagoTarjetasConstants.ISBAN_DIALOG_WARNING_TITLE, this.b.getString(R.string.MSG_USER00526_DEBIN), null, null, this.b.getString(R.string.ID1_ALERT_BTN_ACCEPT), this.b.getString(R.string.IDX_ALERT_BTN_CANCEL), null, null);
        newInstance.setDialogListener(new IDialogListener() {
            public void onItemSelected(String str) {
            }

            public void onSimpleActionButton() {
            }

            public void onPositiveButton() {
                DebinActivity.this.consultarFirmaCredin(true);
            }

            public void onNegativeButton() {
                if (!newInstance.getAutoClose().booleanValue()) {
                    newInstance.setAutoClose(Boolean.valueOf(true));
                    newInstance.closeDialog();
                }
            }
        });
        newInstance.show(((DebinView) getBaseView()).getFragmentManager(), DebinConstants.DIALOG_FIRMA_CREDIN);
    }

    public void consultarFirmaCredin(boolean z) {
        this.e = z;
        if (this.e) {
            ((DebinView) getBaseView()).showProgressIndicator(VGetFirmaCredin.nameService);
        }
        this.mDataManager.getFirmaCredin(this.a);
    }

    @Subscribe
    public void getFirmaCredin(GetFirmaCredinEvent getFirmaCredinEvent) {
        ((DebinView) getBaseView()).dismissProgressIndicator();
        final GetFirmaCredinEvent getFirmaCredinEvent2 = getFirmaCredinEvent;
        AnonymousClass3 r1 = new BaseWSResponseHandler(this.b, TypeOption.INITIAL_VIEW, FragmentConstants.DEBIN, getBaseView(), (BaseActivity) this.a) {
            /* access modifiers changed from: protected */
            public void onOk() {
                DebinActivity.this.a(getFirmaCredinEvent2);
            }
        };
        r1.handleWSResponse(getFirmaCredinEvent);
    }

    public void consultarPreAutorizaciones(String str, String str2, String str3, boolean z) {
        this.e = z;
        if (this.e) {
            ((DebinView) getBaseView()).showProgressIndicator(VGetPreAutorizaciones.nameService);
        }
        this.mDataManager.getPreautorizaciones(str, str2, str3);
    }

    public void consultarDebines(String str, String str2, String str3, String str4, String str5, boolean z) {
        this.e = z;
        if (this.e) {
            ((DebinView) getBaseView()).showProgressIndicator(VGetDebines.nameService);
        }
        this.mDataManager.getDebines(str, str2, str3, str4, str5);
    }

    @Subscribe
    public void onGetPreAutorizaciones(GetPreAutorizacionesEvent getPreAutorizacionesEvent) {
        ((DebinView) getBaseView()).dismissProgressIndicator();
        final GetPreAutorizacionesEvent getPreAutorizacionesEvent2 = getPreAutorizacionesEvent;
        AnonymousClass4 r1 = new BaseWSResponseHandler(this.b, TypeOption.INITIAL_VIEW, FragmentConstants.DEBIN, getBaseView(), (BaseActivity) this.a) {
            /* access modifiers changed from: protected */
            public void onOk() {
                DebinActivity.this.a(getPreAutorizacionesEvent2);
            }

            /* access modifiers changed from: protected */
            public void onRes4Error(WebServiceEvent webServiceEvent) {
                if (DebinActivity.this.e) {
                    super.onRes4Error(webServiceEvent);
                } else {
                    DebinActivity.this.b(webServiceEvent);
                }
            }

            /* access modifiers changed from: protected */
            public void onServerError(WebServiceEvent webServiceEvent) {
                super.onServerError(webServiceEvent);
                DebinActivity.this.a(webServiceEvent);
            }
        };
        r1.handleWSResponse(getPreAutorizacionesEvent);
    }

    /* access modifiers changed from: private */
    public void a(GetPreAutorizacionesEvent getPreAutorizacionesEvent) {
        try {
            GetPreAutorizacionesBodyResponseBean preAutorizacionesBodyResponseBean = getPreAutorizacionesEvent.getResponse().getPreAutorizacionesBodyResponseBean();
            if (!preAutorizacionesBodyResponseBean.getListPreautorizaciones().isEmpty()) {
                ((DebinView) getBaseView()).setPreAutorizacionesView(preAutorizacionesBodyResponseBean, "");
            } else {
                ((DebinView) getBaseView()).setPreAutorizacionesView(preAutorizacionesBodyResponseBean, preAutorizacionesBodyResponseBean.resDesc);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            e2.fillInStackTrace();
        }
    }

    /* access modifiers changed from: private */
    public void a(GetFirmaCredinEvent getFirmaCredinEvent) {
        try {
            GetFirmaCredinBodyResponseBean getFirmaCredinBodyResponseBean = ((GetFirmaCredinResponseBean) getFirmaCredinEvent.getBeanResponse()).getFirmaCredinBodyResponseBean;
            if (!getFirmaCredinBodyResponseBean.getFirma().isEmpty()) {
                ((DebinView) getBaseView()).goToFirmaCredin(getFirmaCredinBodyResponseBean.getFirma(), getFirmaCredinBodyResponseBean.getUrl());
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            e2.fillInStackTrace();
        }
    }

    /* access modifiers changed from: private */
    public void a(WebServiceEvent webServiceEvent) {
        try {
            a(webServiceEvent.getMessageToShow());
        } catch (Exception e2) {
            e2.printStackTrace();
            e2.fillInStackTrace();
        }
    }

    /* access modifiers changed from: private */
    public void b(WebServiceEvent webServiceEvent) {
        try {
            a(webServiceEvent.getMessageToShow());
        } catch (Exception e2) {
            e2.printStackTrace();
            e2.fillInStackTrace();
        }
    }

    @Subscribe
    public void onGetDebines(GetDebinesEvent getDebinesEvent) {
        if (!this.f) {
            ((DebinView) getBaseView()).dismissProgressIndicator();
        }
        if (this.e) {
            ((DebinView) getBaseView()).dismissProgressIndicator();
        }
        final GetDebinesEvent getDebinesEvent2 = getDebinesEvent;
        AnonymousClass5 r1 = new BaseWSResponseHandler(this.b, TypeOption.INITIAL_VIEW, FragmentConstants.DEBIN, getBaseView(), this.b.getString(R.string.F32_00_4401), (BaseActivity) this.a) {
            /* access modifiers changed from: protected */
            public void onOk() {
                DebinActivity.this.a(getDebinesEvent2);
            }

            /* access modifiers changed from: protected */
            public void onRes4Error(WebServiceEvent webServiceEvent) {
                if (DebinActivity.this.e) {
                    super.onRes4Error(webServiceEvent);
                } else {
                    DebinActivity.this.c(webServiceEvent);
                }
            }

            /* access modifiers changed from: protected */
            public void onServerError(WebServiceEvent webServiceEvent) {
                if (DebinActivity.this.e) {
                    super.onServerError(webServiceEvent);
                } else {
                    DebinActivity.this.d(webServiceEvent);
                }
            }
        };
        r1.handleWSResponse(getDebinesEvent);
    }

    /* access modifiers changed from: private */
    public void a(GetDebinesEvent getDebinesEvent) {
        try {
            GetDebinesBodyResponseBean debinesBodyResponseBean = getDebinesEvent.getResponse().getDebinesBodyResponseBean();
            if (!debinesBodyResponseBean.getListaDebinesBean().getListDebinesBean().isEmpty()) {
                ((DebinView) getBaseView()).setDebinesView(debinesBodyResponseBean.getListaDebinesBean().getListDebinesBean(), debinesBodyResponseBean.getSiguientePagina(), "");
            } else {
                ((DebinView) getBaseView()).setDebinesView(new ArrayList(), debinesBodyResponseBean.getSiguientePagina(), debinesBodyResponseBean.resDesc);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            e2.fillInStackTrace();
        }
    }

    /* access modifiers changed from: private */
    public void c(WebServiceEvent webServiceEvent) {
        try {
            a(webServiceEvent.getMessageToShow());
        } catch (Exception e2) {
            e2.printStackTrace();
            e2.fillInStackTrace();
        }
    }

    /* access modifiers changed from: private */
    public void d(WebServiceEvent webServiceEvent) {
        try {
            a(this.b.getString(R.string.F32_00_4468));
        } catch (Exception e2) {
            e2.printStackTrace();
            e2.fillInStackTrace();
        }
    }

    private void a(String str) {
        ((DebinView) getBaseView()).setErrorRecallDebinesView(str);
    }

    public void detalleDebinComprador(ListDebinesBean listDebinesBean) {
        this.f = true;
        ((DebinView) getBaseView()).showProgressIndicator(VGetDetalleDebinComprador.nameService);
        this.mDataManager.getDetalleDebinComprador(listDebinesBean.getIdDebin());
    }

    @Subscribe
    public void onGetDetalleDebinComprador(GetDetalleDebinCompradorEvent getDetalleDebinCompradorEvent) {
        this.f = false;
        ((DebinView) getBaseView()).dismissProgressIndicator();
        final GetDetalleDebinCompradorEvent getDetalleDebinCompradorEvent2 = getDetalleDebinCompradorEvent;
        AnonymousClass6 r1 = new BaseWSResponseHandler(this.b, TypeOption.INTERMDIATE_VIEW, FragmentConstants.DEBIN, getBaseView(), (BaseActivity) this.a) {
            /* access modifiers changed from: protected */
            public void onOk() {
                DebinActivity.this.a(getDetalleDebinCompradorEvent2);
            }
        };
        r1.handleWSResponse(getDetalleDebinCompradorEvent);
        try {
            ((DebinView) getBaseView()).setBackgroundVisibleInit();
        } catch (Exception unused) {
        }
    }

    /* access modifiers changed from: private */
    public void a(GetDetalleDebinCompradorEvent getDetalleDebinCompradorEvent) {
        try {
            GetDetalleDebinCompradorBodyResponseBean detalleDebinCompradorBodyResponseBean = getDetalleDebinCompradorEvent.getResponse().getDetalleDebinCompradorBodyResponseBean();
            if (detalleDebinCompradorBodyResponseBean.getLeyendaDebin() != null) {
                ((DebinView) getBaseView()).setDetalleDebinCompradorView(detalleDebinCompradorBodyResponseBean.getDetalleDebinBean(), detalleDebinCompradorBodyResponseBean.getLeyendaDebin());
            } else {
                ((DebinView) getBaseView()).setDetalleDebinCompradorView(detalleDebinCompradorBodyResponseBean.getDetalleDebinBean(), "");
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void detalleDebinVendedor(ListDebinesBean listDebinesBean) {
        this.f = true;
        ((DebinView) getBaseView()).showProgressIndicator(VGetDetalleDebinVendedor.nameService);
        this.mDataManager.getDetalleDebinVendedor(listDebinesBean.getIdDebin());
    }

    @Subscribe
    public void onGetDetalleDebinComprador(GetDetalleDebinVendedorEvent getDetalleDebinVendedorEvent) {
        this.f = false;
        ((DebinView) getBaseView()).dismissProgressIndicator();
        final GetDetalleDebinVendedorEvent getDetalleDebinVendedorEvent2 = getDetalleDebinVendedorEvent;
        AnonymousClass7 r1 = new BaseWSResponseHandler(this.b, TypeOption.INTERMDIATE_VIEW, FragmentConstants.DEBIN, getBaseView(), (BaseActivity) this.a) {
            /* access modifiers changed from: protected */
            public void onOk() {
                DebinActivity.this.a(getDetalleDebinVendedorEvent2);
            }
        };
        r1.handleWSResponse(getDetalleDebinVendedorEvent);
        try {
            ((DebinView) getBaseView()).setBackgroundVisibleInit();
        } catch (Exception unused) {
        }
    }

    /* access modifiers changed from: private */
    public void a(GetDetalleDebinVendedorEvent getDetalleDebinVendedorEvent) {
        try {
            ((DebinView) getBaseView()).setDetalleDebinVendedorView(getDetalleDebinVendedorEvent.getResponse().getDetalleDebinVendedorBodyResponseBean().getDetalleDebinBean());
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void consultarAdhesionVendedor(String str) {
        this.d = str;
        ConsultarAdhesionVendedorBodyResponseBean consultarAdhesionVendedorBodyResponseBean = this.c.getConsultarAdhesionVendedorBodyResponseBean();
        if (consultarAdhesionVendedorBodyResponseBean == null) {
            ((DebinView) getBaseView()).showProgressIndicator(VGetConsultarAdhesionVendedor.nameService);
            this.mDataManager.consultarAdhesionVendedor();
            return;
        }
        a(consultarAdhesionVendedorBodyResponseBean);
    }

    @Subscribe
    public void onConsultarAdhesionVendedor(ConsultarAdhesionVendedorEvent consultarAdhesionVendedorEvent) {
        ((DebinView) getBaseView()).dismissProgressIndicator();
        final ConsultarAdhesionVendedorEvent consultarAdhesionVendedorEvent2 = consultarAdhesionVendedorEvent;
        AnonymousClass8 r1 = new BaseWSResponseHandler(this.b, TypeOption.INTERMDIATE_VIEW, FragmentConstants.DEBIN, getBaseView(), (BaseActivity) this.a) {
            /* access modifiers changed from: protected */
            public void onOk() {
                DebinActivity.this.a(consultarAdhesionVendedorEvent2);
            }

            /* access modifiers changed from: protected */
            public void onRes4Error() {
                DebinActivity.this.b(consultarAdhesionVendedorEvent2);
            }
        };
        r1.handleWSResponse(consultarAdhesionVendedorEvent);
    }

    /* access modifiers changed from: private */
    public void a(ConsultarAdhesionVendedorEvent consultarAdhesionVendedorEvent) {
        try {
            a(consultarAdhesionVendedorEvent.getResponse().getConsultarAdhesionVendedorBodyResponseBean());
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    private void a(ConsultarAdhesionVendedorBodyResponseBean consultarAdhesionVendedorBodyResponseBean) {
        if (!consultarAdhesionVendedorBodyResponseBean.getListaCuentasVendedorBean().getCuentaVendedor().isEmpty()) {
            if (this.d.equalsIgnoreCase(ACTION_CONSULTA_ADHESION_VENDEDOR.ADHERIR_NUEVO)) {
                ((DebinView) getBaseView()).goToNuevoDebin(consultarAdhesionVendedorBodyResponseBean.getListaCuentasVendedorBean(), "", consultarAdhesionVendedorBodyResponseBean.getLeyendaDebin());
            } else if (this.d.equalsIgnoreCase(ACTION_CONSULTA_ADHESION_VENDEDOR.GESTION_ADHESION)) {
                ((DebinView) getBaseView()).goToGestionAdhesionDebin(consultarAdhesionVendedorBodyResponseBean.getListaCuentasVendedorBean());
            }
        } else if (this.d.equalsIgnoreCase(ACTION_CONSULTA_ADHESION_VENDEDOR.ADHERIR_NUEVO)) {
            ((DebinView) getBaseView()).goToNuevoDebin(new ListaCuentasVendedorBean(), consultarAdhesionVendedorBodyResponseBean.resDesc, consultarAdhesionVendedorBodyResponseBean.getLeyendaDebin());
        } else if (this.d.equalsIgnoreCase(ACTION_CONSULTA_ADHESION_VENDEDOR.GESTION_ADHESION)) {
            ((DebinView) getBaseView()).goToGestionAdhesionDebin(new ListaCuentasVendedorBean());
        }
    }

    /* access modifiers changed from: private */
    public void b(ConsultarAdhesionVendedorEvent consultarAdhesionVendedorEvent) {
        try {
            ((DebinView) getBaseView()).goToNuevoDebin(new ListaCuentasVendedorBean(), consultarAdhesionVendedorEvent.getResponse().consultarAdhesionVendedorBodyResponseBean.resDesc, consultarAdhesionVendedorEvent.getResponse().getConsultarAdhesionVendedorBodyResponseBean().getLeyendaDebin());
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }
}
