package ar.com.santander.rio.mbanking.app.module.transfers.abmDestinatarioTransf;

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
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.services.events.VerificaDatosInicialesTransfOBEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeOption;
import ar.com.santander.rio.mbanking.services.soap.beans.VerificaDatosInicialesTransfOBResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CtaMigradaBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.VerificaDatosInicialesTransfOBBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.VerificaDatosOBBean;
import ar.com.santander.rio.mbanking.services.soap.versions.VVerificaDatosInicialesTransf;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

public class NuevoDestinatarioTransferenciaPresenter extends BasePresenter<NuevoDestinatarioTransferenciaView> {
    /* access modifiers changed from: private */
    public String a;
    /* access modifiers changed from: private */
    public String b;
    /* access modifiers changed from: private */
    public String c;
    /* access modifiers changed from: private */
    public String d;
    private CtaMigradaBean e;
    private Boolean f;
    protected Context mContext;
    protected SessionManager mSessionManager;

    public NuevoDestinatarioTransferenciaPresenter(Bus bus, IDataManager iDataManager, SessionManager sessionManager, Context context) {
        super(bus, iDataManager);
        this.mContext = context;
        this.mSessionManager = sessionManager;
    }

    public void onCreatePage() {
        ((NuevoDestinatarioTransferenciaView) getBaseView()).setNuevoDestinatarioView();
    }

    public void onVerificar(String str, String str2, String str3, String str4, String str5, String str6, Boolean bool) {
        String str7 = str2;
        ((NuevoDestinatarioTransferenciaView) getBaseView()).showProgressIndicator(VVerificaDatosInicialesTransf.nameService);
        this.f = bool;
        if (str7.equals("09") || str7.equals("10")) {
            str7 = "02";
        }
        String str8 = str7;
        this.a = str;
        this.b = str8;
        IDataManager iDataManager = this.mDataManager;
        VerificaDatosOBBean verificaDatosOBBean = new VerificaDatosOBBean(this.a, this.b, str3, str4, this.mSessionManager.getLoginUnico().getDatosPersonales().getNroDocumento(), this.mSessionManager.getLoginUnico().getDatosPersonales().getFechaNacimiento(), str5, str6);
        iDataManager.verificaDatosInicialesTransfOB(verificaDatosOBBean);
    }

    /* access modifiers changed from: private */
    public void a(String str, String str2, String str3, String str4, String str5, String str6, CtaMigradaBean ctaMigradaBean) {
        if (!this.f.booleanValue()) {
            ((NuevoDestinatarioTransferenciaView) getBaseView()).showProgressIndicator(VVerificaDatosInicialesTransf.nameService);
            IDataManager iDataManager = this.mDataManager;
            VerificaDatosOBBean verificaDatosOBBean = new VerificaDatosOBBean(this.a, this.b, str3, str4, this.mSessionManager.getLoginUnico().getDatosPersonales().getNroDocumento(), this.mSessionManager.getLoginUnico().getDatosPersonales().getFechaNacimiento(), str5, str6);
            iDataManager.verificaDatosInicialesTransfOB(verificaDatosOBBean);
            return;
        }
        ((NuevoDestinatarioTransferenciaView) getBaseView()).supermetododeprueba(ctaMigradaBean);
    }

    @Subscribe
    public void onVerificaDatosInicialesTransfOB(VerificaDatosInicialesTransfOBEvent verificaDatosInicialesTransfOBEvent) {
        ((NuevoDestinatarioTransferenciaView) getBaseView()).dismissProgressIndicator();
        final VerificaDatosInicialesTransfOBEvent verificaDatosInicialesTransfOBEvent2 = verificaDatosInicialesTransfOBEvent;
        AnonymousClass1 r1 = new BaseWSResponseHandler(this.mContext, TypeOption.INTERMDIATE_VIEW, FragmentConstants.TRANSFERENCIAS, getBaseView(), (BaseActivity) this.mContext) {
            public void onOk() {
                NuevoDestinatarioTransferenciaPresenter.this.a(verificaDatosInicialesTransfOBEvent2);
            }

            public void onRes4Error() {
                ((NuevoDestinatarioTransferenciaView) NuevoDestinatarioTransferenciaPresenter.this.getBaseView()).onErrorVerificacionCBU();
            }
        };
        r1.handleWSResponse(verificaDatosInicialesTransfOBEvent);
    }

    /* access modifiers changed from: private */
    public void a(VerificaDatosInicialesTransfOBEvent verificaDatosInicialesTransfOBEvent) {
        try {
            VerificaDatosInicialesTransfOBBodyResponseBean verificaDatosInicialesTransfOBBodyResponseBean = ((VerificaDatosInicialesTransfOBResponseBean) verificaDatosInicialesTransfOBEvent.getBeanResponse()).verificaDatosInicialesTransfOBBodyResponseBean;
            if (verificaDatosInicialesTransfOBBodyResponseBean.getCtaMigradaBean() != null) {
                this.e = verificaDatosInicialesTransfOBBodyResponseBean.getCtaMigradaBean();
                showDialogConfirmationCtaMigrada((AppCompatActivity) this.mContext, verificaDatosInicialesTransfOBBodyResponseBean);
                return;
            }
            ((NuevoDestinatarioTransferenciaView) getBaseView()).goToEditarDestinatario((VerificaDatosInicialesTransfOBResponseBean) verificaDatosInicialesTransfOBEvent.getBeanResponse(), this.e);
            this.e = null;
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void showDialogConfirmationCtaMigrada(AppCompatActivity appCompatActivity, final VerificaDatosInicialesTransfOBBodyResponseBean verificaDatosInicialesTransfOBBodyResponseBean) {
        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance("DIALOGCONFIRMATIONABMGETCTAMIGRADA", appCompatActivity.getString(R.string.IDX_ALERT_LBL_TITLE_ADVISE), verificaDatosInicialesTransfOBBodyResponseBean.getCtaMigradaBean().getMensajeCtaMigrada(), null, null, appCompatActivity.getString(R.string.BTN_POSITIVE_ACEPTAR_CTA_MIGRADA), appCompatActivity.getString(R.string.BTN_NEGATIVE_CANCELAR_CTA_MIGRADA), null, null);
        newInstance.setDialogListener(new IDialogListener() {
            public void onItemSelected(String str) {
            }

            public void onNegativeButton() {
            }

            public void onSimpleActionButton() {
            }

            public void onPositiveButton() {
                NuevoDestinatarioTransferenciaPresenter.this.a(NuevoDestinatarioTransferenciaPresenter.this.a, NuevoDestinatarioTransferenciaPresenter.this.b, verificaDatosInicialesTransfOBBodyResponseBean.getCtaMigradaBean().getSucursal(), verificaDatosInicialesTransfOBBodyResponseBean.getCtaMigradaBean().getNumero(), NuevoDestinatarioTransferenciaPresenter.this.c, NuevoDestinatarioTransferenciaPresenter.this.d, verificaDatosInicialesTransfOBBodyResponseBean.getCtaMigradaBean());
            }
        });
        newInstance.show(appCompatActivity.getSupportFragmentManager(), "DIALOGCONFIRMATIONABMGETCTAMIGRADA");
    }
}
