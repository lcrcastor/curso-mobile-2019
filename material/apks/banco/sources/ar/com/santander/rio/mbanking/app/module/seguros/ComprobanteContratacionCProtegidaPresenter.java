package ar.com.santander.rio.mbanking.app.module.seguros;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.base.BasePresenter;
import ar.com.santander.rio.mbanking.app.base.BaseWSResponseHandler;
import ar.com.santander.rio.mbanking.app.ui.constants.SegurosConstants;
import ar.com.santander.rio.mbanking.app.ui.fragments.FragmentConstants;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListener;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.services.events.GetPolizaEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeOption;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ContratarCompraProtegidaBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.versions.VGetPoliza;
import ar.com.santander.rio.mbanking.utils.UtilFile;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

public class ComprobanteContratacionCProtegidaPresenter extends BasePresenter<ComprobanteContratacionCProtegidaView> {
    private Context a;
    private String b;

    public ComprobanteContratacionCProtegidaPresenter(Bus bus, IDataManager iDataManager, Context context) {
        super(bus, iDataManager);
        this.a = context;
    }

    public void onCreatePage(ContratarCompraProtegidaBodyResponseBean contratarCompraProtegidaBodyResponseBean) {
        ((ComprobanteContratacionCProtegidaView) getBaseView()).setComprobanteContratacionView(contratarCompraProtegidaBodyResponseBean);
    }

    public void getPoliza(String str, String str2, String str3) {
        ((ComprobanteContratacionCProtegidaView) getBaseView()).showProgressIndicator(VGetPoliza.nameService);
        this.mDataManager.getPoliza(str, str2, str3);
        this.b = str3;
    }

    @Subscribe
    public void onGetPoliza(GetPolizaEvent getPolizaEvent) {
        ((ComprobanteContratacionCProtegidaView) getBaseView()).dismissProgressIndicator();
        final GetPolizaEvent getPolizaEvent2 = getPolizaEvent;
        AnonymousClass1 r1 = new BaseWSResponseHandler(this.a, TypeOption.INTERMDIATE_VIEW, FragmentConstants.SEGUROS, getBaseView(), (BaseActivity) this.a) {
            /* access modifiers changed from: protected */
            public void onOk() {
                ComprobanteContratacionCProtegidaPresenter.this.a(getPolizaEvent2);
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
                showNoPdfApplicationPopUp();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void showNoPdfApplicationPopUp() {
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
        newInstance.show(((AppCompatActivity) getBaseView()).getSupportFragmentManager(), "popUp");
    }
}
