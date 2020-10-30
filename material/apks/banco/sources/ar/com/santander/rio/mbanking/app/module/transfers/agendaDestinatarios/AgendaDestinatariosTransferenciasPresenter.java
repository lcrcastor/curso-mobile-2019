package ar.com.santander.rio.mbanking.app.module.transfers.agendaDestinatarios;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.base.BasePresenter;
import ar.com.santander.rio.mbanking.app.base.BaseWSResponseHandler;
import ar.com.santander.rio.mbanking.app.ui.constants.TransferenciasConstants;
import ar.com.santander.rio.mbanking.app.ui.constants.TransferenciasConstants.accionVerificarDatosTransf;
import ar.com.santander.rio.mbanking.app.ui.fragments.FragmentConstants;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListener;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.services.events.VerificaDatosInicialesTransfEvent;
import ar.com.santander.rio.mbanking.services.events.VerificaDatosInicialesTransfOBEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeOption;
import ar.com.santander.rio.mbanking.services.model.general.AgendaDestinatarios;
import ar.com.santander.rio.mbanking.services.soap.beans.VerificaDatosInicialesTransfOBResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.AgDestBSRBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.AgDestOBBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.AgendadosBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestBSRBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestOBBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.VerificaDatosBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.VerificaDatosInicialesTransfOBBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.VerificaDatosOBBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.VerificaDatosSalidaOBBean;
import ar.com.santander.rio.mbanking.services.soap.versions.VVerificaDatosInicialesTransf;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

public class AgendaDestinatariosTransferenciasPresenter extends BasePresenter<AgendaDestinatariosTransferenciasView> {
    private SessionManager a;
    private Context b;
    private String c;
    private String d;
    private DatosCuentasDestOBBean e = null;
    private DatosCuentasDestBSRBean f = null;
    private AgendaDestinatarios g;
    /* access modifiers changed from: private */
    public DatosCuentasBean h;
    /* access modifiers changed from: private */
    public String i;
    /* access modifiers changed from: private */
    public AgendadosBean j;
    /* access modifiers changed from: private */
    public AgendaDestinatarios k;
    /* access modifiers changed from: private */
    public String l;

    public AgendaDestinatariosTransferenciasPresenter(Bus bus, IDataManager iDataManager, SessionManager sessionManager, Context context) {
        super(bus, iDataManager);
        this.a = sessionManager;
        this.b = context;
    }

    public void onDestSelected(AgendaDestinatarios agendaDestinatarios, String str, DatosCuentasBean datosCuentasBean, AgendadosBean agendadosBean, String str2) {
        this.c = str2;
        this.g = agendaDestinatarios;
        this.k = agendaDestinatarios;
        this.j = agendadosBean;
        this.i = str;
        this.h = datosCuentasBean;
        this.l = str2;
        if (str.equals(TransferenciasConstants.cBANCO_PROPIA)) {
            ((AgendaDestinatariosTransferenciasView) getBaseView()).gotoNextFlow(agendaDestinatarios, null, null, null);
        } else if (str.equals(TransferenciasConstants.cBANCO_SR_TERCEROS)) {
            callVerificaDatosInicialesTransf(agendaDestinatarios, agendadosBean);
        } else if (str.equals(TransferenciasConstants.cBANCO_OB)) {
            callVerificaDatosInicialesTransfOB(agendaDestinatarios, agendadosBean, datosCuentasBean);
        }
    }

    /* access modifiers changed from: private */
    public void a(AgendaDestinatarios agendaDestinatarios, String str, DatosCuentasBean datosCuentasBean, AgendadosBean agendadosBean, String str2, String str3, String str4) {
        this.c = str2;
        this.g = agendaDestinatarios;
        this.k = agendaDestinatarios;
        this.j = agendadosBean;
        this.i = str;
        this.h = datosCuentasBean;
        this.l = str2;
        if (str.equals(TransferenciasConstants.cBANCO_PROPIA)) {
            ((AgendaDestinatariosTransferenciasView) getBaseView()).gotoNextFlow(agendaDestinatarios, null, null, null);
        } else if (str.equals(TransferenciasConstants.cBANCO_SR_TERCEROS)) {
            a(agendaDestinatarios, agendadosBean, str3, str4);
        } else if (str.equals(TransferenciasConstants.cBANCO_OB)) {
            a(agendaDestinatarios, agendadosBean, datosCuentasBean, str3, str4);
        }
    }

    private void a(AgendaDestinatarios agendaDestinatarios, AgendadosBean agendadosBean, String str, String str2) {
        this.f = getDatosCuentasDestBSRBeanPara(agendaDestinatarios.getInfo2(), agendadosBean);
        this.d = this.f.getTipoDestino();
        VerificaDatosBean verificaDatosBean = new VerificaDatosBean(this.f.getTipoDestino(), this.f.getTipo(), str2, str, this.a.getLoginUnico().getDatosPersonales().getNroDocumento(), this.a.getLoginUnico().getDatosPersonales().getFechaNacimiento(), this.f.getAlias());
        ((AgendaDestinatariosTransferenciasView) getBaseView()).showProgressIndicator(VVerificaDatosInicialesTransf.nameService);
        this.mDataManager.verificaDatosInicialesTransf(verificaDatosBean);
    }

    public void callVerificaDatosInicialesTransf(AgendaDestinatarios agendaDestinatarios, AgendadosBean agendadosBean) {
        this.f = getDatosCuentasDestBSRBeanPara(agendaDestinatarios.getInfo2(), agendadosBean);
        this.d = this.f.getTipoDestino();
        VerificaDatosBean verificaDatosBean = new VerificaDatosBean(this.f.getTipoDestino(), this.f.getTipo(), this.f.getSucursal(), this.f.getNumero(), this.a.getLoginUnico().getDatosPersonales().getNroDocumento(), this.a.getLoginUnico().getDatosPersonales().getFechaNacimiento(), this.f.getAlias());
        ((AgendaDestinatariosTransferenciasView) getBaseView()).showProgressIndicator(VVerificaDatosInicialesTransf.nameService);
        this.mDataManager.verificaDatosInicialesTransf(verificaDatosBean);
    }

    private void a(AgendaDestinatarios agendaDestinatarios, AgendadosBean agendadosBean, DatosCuentasBean datosCuentasBean, String str, String str2) {
        this.e = getDatosCuentasDestOBBeanPara(agendaDestinatarios.getInfo2(), agendadosBean);
        this.d = this.e.getTipoDestino();
        VerificaDatosOBBean verificaDatosOBBean = new VerificaDatosOBBean(this.e.getTipoDestino(), datosCuentasBean.getTipo(), str2, str, this.a.getLoginUnico().getDatosPersonales().getNroDocumento(), this.a.getLoginUnico().getDatosPersonales().getFechaNacimiento(), (this.e.getAlias() == null || String.valueOf(this.e).isEmpty()) ? this.e.getCbu() : null, this.e.getAlias());
        ((AgendaDestinatariosTransferenciasView) getBaseView()).showProgressIndicator(VVerificaDatosInicialesTransf.nameService);
        this.mDataManager.verificaDatosInicialesTransfOB(verificaDatosOBBean);
    }

    public void callVerificaDatosInicialesTransfOB(AgendaDestinatarios agendaDestinatarios, AgendadosBean agendadosBean, DatosCuentasBean datosCuentasBean) {
        this.e = getDatosCuentasDestOBBeanPara(agendaDestinatarios.getInfo2(), agendadosBean);
        this.d = this.e.getTipoDestino();
        VerificaDatosOBBean verificaDatosOBBean = new VerificaDatosOBBean(this.e.getTipoDestino(), datosCuentasBean.getTipo(), datosCuentasBean.getSucursal(), datosCuentasBean.getNumero(), this.a.getLoginUnico().getDatosPersonales().getNroDocumento(), this.a.getLoginUnico().getDatosPersonales().getFechaNacimiento(), (this.e.getAlias() == null || String.valueOf(this.e).isEmpty()) ? this.e.getCbu() : null, this.e.getAlias());
        ((AgendaDestinatariosTransferenciasView) getBaseView()).showProgressIndicator(VVerificaDatosInicialesTransf.nameService);
        this.mDataManager.verificaDatosInicialesTransfOB(verificaDatosOBBean);
    }

    public DatosCuentasDestBSRBean getDatosCuentasDestBSRBeanPara(String str, AgendadosBean agendadosBean) {
        int i2 = 0;
        while (i2 < ((AgDestBSRBean) agendadosBean.getListAgDestBSRBean().get(0)).getListDatosCuentasDestBSRBean().size()) {
            try {
                if (((DatosCuentasDestBSRBean) ((AgDestBSRBean) agendadosBean.getListAgDestBSRBean().get(0)).getListDatosCuentasDestBSRBean().get(i2)).getDescCtaDestinoBSR().equals(str)) {
                    return (DatosCuentasDestBSRBean) ((AgDestBSRBean) agendadosBean.getListAgDestBSRBean().get(0)).getListDatosCuentasDestBSRBean().get(i2);
                }
                i2++;
            } catch (Exception unused) {
            }
        }
        return null;
    }

    public DatosCuentasDestOBBean getDatosCuentasDestOBBeanPara(String str, AgendadosBean agendadosBean) {
        int i2 = 0;
        while (i2 < ((AgDestOBBean) agendadosBean.getListAgDestOBBean().get(0)).getListDatosCuentasDestOBBean().size()) {
            try {
                if (((DatosCuentasDestOBBean) ((AgDestOBBean) agendadosBean.getListAgDestOBBean().get(0)).getListDatosCuentasDestOBBean().get(i2)).getCbu().equals(str)) {
                    return (DatosCuentasDestOBBean) ((AgDestOBBean) agendadosBean.getListAgDestOBBean().get(0)).getListDatosCuentasDestOBBean().get(i2);
                }
                i2++;
            } catch (Exception unused) {
            }
        }
        return null;
    }

    @Subscribe
    public void onVerificaDatosInicialesTransf(VerificaDatosInicialesTransfEvent verificaDatosInicialesTransfEvent) {
        ((AgendaDestinatariosTransferenciasView) getBaseView()).dismissProgressIndicator();
        final VerificaDatosInicialesTransfEvent verificaDatosInicialesTransfEvent2 = verificaDatosInicialesTransfEvent;
        AnonymousClass1 r1 = new BaseWSResponseHandler(this.b, TypeOption.INTERMDIATE_VIEW, FragmentConstants.TRANSFERENCIAS, getBaseView(), (BaseActivity) this.b) {
            public void onOk() {
                AgendaDestinatariosTransferenciasPresenter.this.onVerificaDatosInicialesTransfOk(verificaDatosInicialesTransfEvent2);
            }

            public void commonAllErrorsAfterProcess(WebServiceEvent webServiceEvent) {
                AgendaDestinatariosTransferenciasPresenter.this.onVerificaDatosInicialesTransfCommonError();
            }
        };
        r1.handleWSResponse(verificaDatosInicialesTransfEvent);
    }

    public void onVerificaDatosInicialesTransfOk(VerificaDatosInicialesTransfEvent verificaDatosInicialesTransfEvent) {
        VerificaDatosInicialesTransfOBBodyResponseBean verificaDatosInicialesTransfOBBodyResponseBean = ((VerificaDatosInicialesTransfOBResponseBean) verificaDatosInicialesTransfEvent.getBeanResponse()).getVerificaDatosInicialesTransfOBBodyResponseBean();
        if (!this.c.equalsIgnoreCase(accionVerificarDatosTransf.SELECCION)) {
            return;
        }
        if (verificaDatosInicialesTransfOBBodyResponseBean.resCod == null || verificaDatosInicialesTransfOBBodyResponseBean.resCod.isEmpty()) {
            ((AgendaDestinatariosTransferenciasView) getBaseView()).gotoNextFlow(this.g, verificaDatosInicialesTransfOBBodyResponseBean.getVerificaDatosSalidaOBBean(), this.f, null);
        } else {
            showChangedAliasError(verificaDatosInicialesTransfOBBodyResponseBean.resDesc, verificaDatosInicialesTransfOBBodyResponseBean.getVerificaDatosSalidaOBBean());
        }
    }

    public void onVerificaDatosInicialesTransfCommonError() {
        if (this.c.equalsIgnoreCase(accionVerificarDatosTransf.SELECCION)) {
            ((AgendaDestinatariosTransferenciasView) getBaseView()).resetSelectedFromList();
        } else {
            this.c.equalsIgnoreCase(accionVerificarDatosTransf.EDITAR);
        }
    }

    @Subscribe
    public void onVerificaDatosInicialesTransfOB(VerificaDatosInicialesTransfOBEvent verificaDatosInicialesTransfOBEvent) {
        ((AgendaDestinatariosTransferenciasView) getBaseView()).dismissProgressIndicator();
        final VerificaDatosInicialesTransfOBEvent verificaDatosInicialesTransfOBEvent2 = verificaDatosInicialesTransfOBEvent;
        AnonymousClass2 r1 = new BaseWSResponseHandler(this.b, TypeOption.INTERMDIATE_VIEW, FragmentConstants.TRANSFERENCIAS, getBaseView(), (BaseActivity) this.b) {
            public void onOk() {
                AgendaDestinatariosTransferenciasPresenter.this.onVerificaDatosInicialesTransfOBOk(verificaDatosInicialesTransfOBEvent2);
            }

            public void commonAllErrorsAfterProcess(WebServiceEvent webServiceEvent) {
                AgendaDestinatariosTransferenciasPresenter.this.onVerificaDatosInicialesTransfCommonError();
            }
        };
        r1.handleWSResponse(verificaDatosInicialesTransfOBEvent);
    }

    public void onVerificaDatosInicialesTransfOBOk(VerificaDatosInicialesTransfOBEvent verificaDatosInicialesTransfOBEvent) {
        VerificaDatosInicialesTransfOBBodyResponseBean verificaDatosInicialesTransfOBBodyResponseBean = ((VerificaDatosInicialesTransfOBResponseBean) verificaDatosInicialesTransfOBEvent.getBeanResponse()).getVerificaDatosInicialesTransfOBBodyResponseBean();
        if (verificaDatosInicialesTransfOBBodyResponseBean.resCod == null || verificaDatosInicialesTransfOBBodyResponseBean.resCod.isEmpty()) {
            this.e.setDiferido(verificaDatosInicialesTransfOBBodyResponseBean.getVerificaDatosSalidaOBBean().getDiferido());
            ((AgendaDestinatariosTransferenciasView) getBaseView()).gotoNextFlow(this.g, verificaDatosInicialesTransfOBBodyResponseBean.getVerificaDatosSalidaOBBean(), null, this.e);
            return;
        }
        showChangedAliasError(verificaDatosInicialesTransfOBBodyResponseBean.resDesc, verificaDatosInicialesTransfOBBodyResponseBean.getVerificaDatosSalidaOBBean());
    }

    public void showChangedAliasError(String str, final VerificaDatosSalidaOBBean verificaDatosSalidaOBBean) {
        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(null, this.b.getResources().getString(R.string.IDX_ALERT_LBL_TITLE_ADVISE), str, this.b.getResources().getString(R.string.ID1_ALERT_BTN_ACCEPT), this.b.getResources().getString(R.string.IDX_ALERT_BTN_CANCEL));
        newInstance.setDialogListener(new IDialogListener() {
            public void onItemSelected(String str) {
            }

            public void onSimpleActionButton() {
            }

            public void onPositiveButton() {
                AgendaDestinatariosTransferenciasPresenter.this.a(AgendaDestinatariosTransferenciasPresenter.this.k, AgendaDestinatariosTransferenciasPresenter.this.i, AgendaDestinatariosTransferenciasPresenter.this.h, AgendaDestinatariosTransferenciasPresenter.this.j, AgendaDestinatariosTransferenciasPresenter.this.l, verificaDatosSalidaOBBean.getNumero(), verificaDatosSalidaOBBean.getSucursal());
            }

            public void onNegativeButton() {
                ((AgendaDestinatariosTransferenciasView) AgendaDestinatariosTransferenciasPresenter.this.getBaseView()).resetSelectedFromList();
            }
        });
        newInstance.show(((AppCompatActivity) getBaseView()).getSupportFragmentManager(), "aliasPopUp");
    }
}
