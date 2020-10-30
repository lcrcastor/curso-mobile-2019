package ar.com.santander.rio.mbanking.app.module.superClub;

import android.content.Context;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.base.BasePresenter;
import ar.com.santander.rio.mbanking.app.base.BaseWSResponseHandler;
import ar.com.santander.rio.mbanking.app.ui.fragments.FragmentConstants;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.services.events.GetFirmaSCEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeOption;
import ar.com.santander.rio.mbanking.services.soap.versions.VGetFirmaSC;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

public class SuperClubCatalogoPresenter extends BasePresenter<SuperClubCatalogoView> {
    private Context a;
    private SessionManager b;

    public SuperClubCatalogoPresenter(Bus bus, IDataManager iDataManager, Context context, SessionManager sessionManager) {
        super(bus, iDataManager);
        this.a = context;
        this.b = sessionManager;
    }

    public void getFirmaSC() {
        ((SuperClubCatalogoView) getBaseView()).showProgressIndicator(VGetFirmaSC.nameService);
        this.mDataManager.getFirmaSC();
    }

    @Subscribe
    public void onGetFirmaSC(GetFirmaSCEvent getFirmaSCEvent) {
        final GetFirmaSCEvent getFirmaSCEvent2 = getFirmaSCEvent;
        AnonymousClass1 r0 = new BaseWSResponseHandler(this.a, TypeOption.INITIAL_VIEW, FragmentConstants.COMODINES_SUPERCLUB, getBaseView(), (BaseActivity) this.a) {
            /* access modifiers changed from: protected */
            public void onOk() {
                SuperClubCatalogoPresenter.this.a(getFirmaSCEvent2);
            }

            /* access modifiers changed from: protected */
            public void commonAllErrorsBeforeProcess(WebServiceEvent webServiceEvent) {
                this.mBaseView.dismissProgressIndicator();
            }
        };
        r0.handleWSResponse(getFirmaSCEvent);
    }

    /* access modifiers changed from: private */
    public void a(GetFirmaSCEvent getFirmaSCEvent) {
        try {
            ((SuperClubCatalogoView) getBaseView()).setCatalogoView(getFirmaSCEvent.getResponseBean().getGetFirmaSCBodyResponseBean());
        } catch (Exception e) {
            e.printStackTrace();
            e.fillInStackTrace();
        }
    }
}
