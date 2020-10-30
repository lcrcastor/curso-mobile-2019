package ar.com.santander.rio.mbanking.app.module.buzonNotifPush;

import android.util.Log;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.base.BasePresenter;
import ar.com.santander.rio.mbanking.app.base.BaseWSResponseHandler;
import ar.com.santander.rio.mbanking.app.ui.constants.WSErrorHandlerConstants;
import ar.com.santander.rio.mbanking.app.ui.fragments.FragmentConstants;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.services.events.GetNotificacionesPushEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeOption;
import ar.com.santander.rio.mbanking.services.soap.versions.VGetNotificacionesPush;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import java.util.ArrayList;

public class BuzonNotifPushPresenter extends BasePresenter<BuzonNotifPushView> {
    public BuzonNotifPushPresenter(Bus bus, IDataManager iDataManager) {
        super(bus, iDataManager);
    }

    public void callGetNotificacionesPush(int i, boolean z) {
        if (z) {
            ((BuzonNotifPushView) getBaseView()).showProgressIndicator(VGetNotificacionesPush.nameService);
        }
        this.mDataManager.getNotificacionesPush(String.valueOf(i));
    }

    @Subscribe
    public void onGetNotificacionesPush(GetNotificacionesPushEvent getNotificacionesPushEvent) {
        ((BuzonNotifPushView) getBaseView()).dismissProgressIndicator();
        final GetNotificacionesPushEvent getNotificacionesPushEvent2 = getNotificacionesPushEvent;
        AnonymousClass1 r1 = new BaseWSResponseHandler(((BuzonNotifPushView) getBaseView()).getFragmentContext(), TypeOption.INITIAL_VIEW, FragmentConstants.NOTIF_PUSH, getBaseView(), (BaseActivity) ((BuzonNotifPushView) getBaseView()).getFragmentContext()) {
            public void onOk() {
                BuzonNotifPushPresenter.this.onGetNotificacionesPushResultOk(getNotificacionesPushEvent2);
            }

            public void onRes4Error() {
                BuzonNotifPushPresenter.this.onGetNotificacionesPushResult4(getNotificacionesPushEvent2);
            }
        };
        r1.handleWSResponse(getNotificacionesPushEvent);
    }

    public void onGetNotificacionesPushResultOk(GetNotificacionesPushEvent getNotificacionesPushEvent) {
        try {
            ((BuzonNotifPushView) getBaseView()).setBuzonNotifPushView(getNotificacionesPushEvent.getResponseBean().getNotificacionesPushBodyResponseBean().getNotificaciones().getNotificacion(), getNotificacionesPushEvent.getResponseBean().getNotificacionesPushBodyResponseBean().getMasNov(), "");
        } catch (Exception e) {
            Log.e(WSErrorHandlerConstants.LOG_TAG, "onGetNotificacionesPushResultOk: ", e);
            e.fillInStackTrace();
        }
    }

    public void onGetNotificacionesPushResult4(GetNotificacionesPushEvent getNotificacionesPushEvent) {
        try {
            ((BuzonNotifPushView) getBaseView()).setBuzonNotifPushView(new ArrayList(), "N", getNotificacionesPushEvent.getMessageToShow());
        } catch (Exception e) {
            Log.e(WSErrorHandlerConstants.LOG_TAG, "onGetNotificacionesPushResultOk: ", e);
            e.fillInStackTrace();
        }
    }
}
