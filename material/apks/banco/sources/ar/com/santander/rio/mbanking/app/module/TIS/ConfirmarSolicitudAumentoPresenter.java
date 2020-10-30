package ar.com.santander.rio.mbanking.app.module.TIS;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.base.BasePresenter;
import ar.com.santander.rio.mbanking.app.base.BaseWSResponseHandler;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListener;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.services.events.SolicitudLimiteTransfEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeOption;
import ar.com.santander.rio.mbanking.services.soap.versions.VSolicitudLimiteTransf;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

public class ConfirmarSolicitudAumentoPresenter extends BasePresenter<ConfirmarSolicitudAumentoView> {
    /* access modifiers changed from: private */
    public Context a;
    /* access modifiers changed from: private */
    public AnalyticsManager b;

    public ConfirmarSolicitudAumentoPresenter(Bus bus, IDataManager iDataManager, Context context, AnalyticsManager analyticsManager) {
        super(bus, iDataManager);
        this.a = context;
        this.b = analyticsManager;
    }

    public void onCreatePage() {
        ((ConfirmarSolicitudAumentoView) getBaseView()).setConfirmarsolicitudAumentoView();
    }

    public void showConfirmDialog(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10) {
        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(this.a.getString(R.string.F33_02A_Lbl_Confirmar), this.a.getString(R.string.F33_02A_Lbl_Confirmas_la_Solicitud_de_Aumento_de_Limite_de_Transferencias), null, null, this.a.getString(R.string.F33_02A_Btn_Si), this.a.getString(R.string.F33_02A_Btn_No), null);
        final String str11 = str;
        final String str12 = str2;
        final String str13 = str3;
        final String str14 = str4;
        final String str15 = str5;
        final String str16 = str6;
        final String str17 = str7;
        final String str18 = str8;
        final String str19 = str9;
        final String str20 = str10;
        AnonymousClass1 r0 = new IDialogListener() {
            public void onItemSelected(String str) {
            }

            public void onSimpleActionButton() {
            }

            public void onPositiveButton() {
                ConfirmarSolicitudAumentoPresenter.this.a(str11, str12, str13, str14, str15, str16, str17, str18, str19, str20);
            }

            public void onNegativeButton() {
                ConfirmarSolicitudAumentoPresenter.this.b.trackEvent(ConfirmarSolicitudAumentoPresenter.this.a.getString(R.string.analytics_event_category_tis), ConfirmarSolicitudAumentoPresenter.this.a.getString(R.string.analytics_event_category_action_tis_cancelar), ConfirmarSolicitudAumentoPresenter.this.a.getString(R.string.analytics_event_tis_02));
            }
        };
        newInstance.setDialogListener(r0);
        newInstance.show(((AppCompatActivity) getBaseView()).getSupportFragmentManager(), "isbanDialogConfirm");
    }

    /* access modifiers changed from: private */
    public void a(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10) {
        ((ConfirmarSolicitudAumentoView) getBaseView()).showProgressIndicator(VSolicitudLimiteTransf.nameService);
        this.mDataManager.solicitudLimiteTransf(str, str2, str3, str4, str5, str6, str7, str8, str9, str10);
    }

    @Subscribe
    public void onCallSolicitudLimiteTransf(SolicitudLimiteTransfEvent solicitudLimiteTransfEvent) {
        ((ConfirmarSolicitudAumentoView) getBaseView()).dismissProgressIndicator();
        final SolicitudLimiteTransfEvent solicitudLimiteTransfEvent2 = solicitudLimiteTransfEvent;
        AnonymousClass2 r1 = new BaseWSResponseHandler(this.a, TypeOption.NO_TRANSACTIONAL_FINAL_VIEW, VSolicitudLimiteTransf.nameService, getBaseView(), (BaseActivity) this.a) {
            /* access modifiers changed from: protected */
            public void onOk() {
                ConfirmarSolicitudAumentoPresenter.this.a(solicitudLimiteTransfEvent2);
            }

            /* access modifiers changed from: protected */
            public void commonAllErrorsAfterProcess(WebServiceEvent webServiceEvent) {
                ConfirmarSolicitudAumentoPresenter.this.b.trackScreen(this.mContext.getString(R.string.analytics_screen_name_tis_f33_05));
            }
        };
        r1.handleWSResponse(solicitudLimiteTransfEvent);
    }

    /* access modifiers changed from: private */
    public void a(SolicitudLimiteTransfEvent solicitudLimiteTransfEvent) {
        try {
            ((ConfirmarSolicitudAumentoView) getBaseView()).gotoComprobanteSolicitud(solicitudLimiteTransfEvent.getResponse().solicitudLimiteTransfBodyResponseBean);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
