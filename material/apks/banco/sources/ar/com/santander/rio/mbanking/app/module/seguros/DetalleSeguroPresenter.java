package ar.com.santander.rio.mbanking.app.module.seguros;

import android.content.Context;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.base.BasePresenter;
import ar.com.santander.rio.mbanking.app.base.BaseWSResponseHandler;
import ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity;
import ar.com.santander.rio.mbanking.app.ui.constants.SegurosConstants;
import ar.com.santander.rio.mbanking.app.ui.fragments.FragmentConstants;
import ar.com.santander.rio.mbanking.app.ui.fragments.privado.SeguroWebViewFragment;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListener;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.services.events.GetFirmaSeguroEvent;
import ar.com.santander.rio.mbanking.services.events.GetPolizaEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeOption;
import ar.com.santander.rio.mbanking.services.soap.beans.body.SeguroBean;
import ar.com.santander.rio.mbanking.services.soap.versions.VGetFirmaSeguros;
import ar.com.santander.rio.mbanking.services.soap.versions.VGetPoliza;
import ar.com.santander.rio.mbanking.utils.UtilFile;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import java.util.ArrayList;

public class DetalleSeguroPresenter extends BasePresenter<DetalleSeguroView> {
    /* access modifiers changed from: private */
    public Context a;
    /* access modifiers changed from: private */
    public SeguroBean b;
    /* access modifiers changed from: private */
    public String c;

    public DetalleSeguroPresenter(Bus bus, IDataManager iDataManager, Context context) {
        super(bus, iDataManager);
        this.a = context;
    }

    public void onCreatePage(String str, SeguroBean seguroBean, String str2) {
        ((DetalleSeguroView) getBaseView()).setDetalleSeguroView(str, seguroBean);
        this.b = seguroBean;
        this.c = str2;
    }

    public void showDetalleSeguroMenuOptions() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(this.a.getString(R.string.IDXX_SEGUROS_OPTION_VER_POLIZA));
        if (this.c != null) {
            arrayList.add(this.a.getString(R.string.DENUNCIAR_SINIESTRO));
        }
        final IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(null, null, arrayList, this.a.getString(R.string.IDX_ALERT_BTN_CANCEL), null, null, "");
        newInstance.setDialogListener(new IDialogListener() {
            public void onNegativeButton() {
            }

            public void onPositiveButton() {
            }

            public void onItemSelected(String str) {
                if (str.equals(DetalleSeguroPresenter.this.a.getString(R.string.IDXX_SEGUROS_OPTION_VER_POLIZA))) {
                    ((DetalleSeguroView) DetalleSeguroPresenter.this.getBaseView()).getAnalyticsManager().trackEvent(DetalleSeguroPresenter.this.a.getString(R.string.analytics_trackevent_category_seguros), DetalleSeguroPresenter.this.a.getString(R.string.analytics_trackevent_action_ver_poliza), DetalleSeguroPresenter.this.a.getString(R.string.analytics_trackevent_label_poliza));
                    newInstance.dismiss();
                    ((DetalleSeguroView) DetalleSeguroPresenter.this.getBaseView()).showProgressIndicator(VGetPoliza.nameService);
                    DetalleSeguroPresenter.this.mDataManager.getPoliza(DetalleSeguroPresenter.this.b.getCodRamo(), DetalleSeguroPresenter.this.b.getNumPoliza(), DetalleSeguroPresenter.this.b.getNumCertificado());
                } else if (str.equals(DetalleSeguroPresenter.this.a.getString(R.string.DENUNCIAR_SINIESTRO))) {
                    ((DetalleSeguroView) DetalleSeguroPresenter.this.getBaseView()).showProgressIndicator(VGetFirmaSeguros.nameService);
                    DetalleSeguroPresenter.this.mDataManager.getFirmaSeguro();
                }
            }

            public void onSimpleActionButton() {
                newInstance.dismiss();
            }
        });
        newInstance.show(((DetalleSeguroView) getBaseView()).getFragmentManager(), "popUpPoliza");
    }

    @Subscribe
    public void onGetFirmaSeguro(GetFirmaSeguroEvent getFirmaSeguroEvent) {
        ((DetalleSeguroView) getBaseView()).dismissProgressIndicator();
        final GetFirmaSeguroEvent getFirmaSeguroEvent2 = getFirmaSeguroEvent;
        AnonymousClass2 r1 = new BaseWSResponseHandler(this.a, TypeOption.INITIAL_VIEW, FragmentConstants.FIRMA_SEGURO, (BaseActivity) this.a) {
            /* access modifiers changed from: protected */
            public void onOk() {
                String str = "";
                if (DetalleSeguroPresenter.this.c != null) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(DetalleSeguroPresenter.this.c);
                    sb.append("&insuranceCode=");
                    sb.append(DetalleSeguroPresenter.this.b.getCodRamo());
                    sb.append("&policy=");
                    sb.append(DetalleSeguroPresenter.this.b.getNumPoliza());
                    sb.append("&certificate=");
                    sb.append(DetalleSeguroPresenter.this.b.getNumCertificado());
                    sb.append("&jwt=");
                    str = sb.toString();
                }
                ((SantanderRioMainActivity) this.mContext).changeFragmentAnimation(new SeguroWebViewFragment(getFirmaSeguroEvent2.getResponse().getFirmaSeguroBodyResponseBean, str), R.anim.slide_in_right, R.anim.slide_out_left, R.anim.no_animation, R.anim.slide_out_right);
            }
        };
        r1.handleWSResponse(getFirmaSeguroEvent);
    }

    private void a() {
        final IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(null, this.a.getString(R.string.IDX_ALERT_LBL_TITLE_ADVISE), this.a.getString(R.string.ID_XXXX_SEGUROS_MSG_POPUP_NOAPP), this.a.getString(R.string.ID1_ALERT_BTN_ACCEPT), null);
        newInstance.setDialogListener(new IDialogListener() {
            public void onItemSelected(String str) {
            }

            public void onNegativeButton() {
            }

            public void onPositiveButton() {
            }

            public void onSimpleActionButton() {
                newInstance.dismiss();
            }
        });
        newInstance.show(((DetalleSeguroView) getBaseView()).getFragmentManager(), "popUp");
    }

    @Subscribe
    public void onGetPoliza(GetPolizaEvent getPolizaEvent) {
        ((DetalleSeguroView) getBaseView()).dismissProgressIndicator();
        final GetPolizaEvent getPolizaEvent2 = getPolizaEvent;
        AnonymousClass4 r1 = new BaseWSResponseHandler(this.a, TypeOption.INTERMDIATE_VIEW, FragmentConstants.SEGUROS, getBaseView(), (BaseActivity) this.a) {
            /* access modifiers changed from: protected */
            public void onOk() {
                DetalleSeguroPresenter.this.a(getPolizaEvent2);
            }
        };
        r1.handleWSResponse(getPolizaEvent);
    }

    /* access modifiers changed from: private */
    public void a(GetPolizaEvent getPolizaEvent) {
        if (getPolizaEvent != null) {
            try {
                if (UtilFile.canDisplayPdf(this.a)) {
                    UtilFile.showPdf(UtilFile.saveFileFromBase64(getPolizaEvent.getResponse().getGetPolizaBodyResponseBean().getArchivoPoliza(), String.format(SegurosConstants.POLIZA_FILE_NAME, new Object[]{getPolizaEvent.getResponse().getGetPolizaBodyResponseBean().getArchivoNombre()}), this.a), this.a);
                    return;
                }
                a();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
